/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.datastores.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.mock.MockPageContext;
import org.apache.wicket.pageStore.IPageContext;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.util.lang.Bytes;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performance and stability test for IPageStore implementations
 */
public abstract class BaseDataStoreTest {
	/** Log for reporting. */
	private static final Logger log = LoggerFactory.getLogger("IPageStoreTest");

	private static final Random random = new Random();
	private static final int FILE_SIZE_MIN = 1024 * 200;
	private static final int FILE_SIZE_MAX = 1024 * 300;
	private static final int SESSION_COUNT = 50;
	private static final int FILES_COUNT = 1000;
	private static final int WAIT_TIME = 1;
	private static final int THREAD_COUNT = 20;
	private static final int READ_MODULO = 100;

	private static class File {
		private final String sessionId;
		private final int id;

		private byte first;
		private byte last;
		private int length;

		public File(String sessionId, int id) {
			this.sessionId = sessionId;
			this.id = id;
		}

		public String getSessionId() {
			return sessionId;
		}

		public int getId() {
			return id;
		}

		public byte[] generateData() {
			length = FILE_SIZE_MIN + random.nextInt(FILE_SIZE_MAX - FILE_SIZE_MIN);
			byte data[] = new byte[length];
			random.nextBytes(data);
			first = data[0];
			last = data[data.length - 1];
			return data;
		}

		public boolean checkData(byte data[]) {
			if (data == null) {
				log.error("data[] should never be null");
				return false;
			}
			if (data.length != length) {
				log.error("data.length != length");
				return false;
			}
			if (first != data[0]) {
				log.error("first != data[0]");
				return false;
			}
			if (last != data[data.length - 1]) {
				log.error("last != data[data.length - 1]");
				return false;
			}
			return true;
		}
	}

	private final Map<String, AtomicInteger> sessionCounter = new ConcurrentHashMap<>();
	private final ConcurrentLinkedQueue<File> filesToSave = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<File> filesToRead1 = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<File> filesToRead2 = new ConcurrentLinkedQueue<>();

	private final AtomicInteger read1Count = new AtomicInteger(0);
	private final AtomicInteger read2Count = new AtomicInteger(0);
	private final AtomicInteger saveCount = new AtomicInteger(0);

	private final AtomicBoolean saveDone = new AtomicBoolean(false);
	private final AtomicBoolean read1Done = new AtomicBoolean(false);
	private final AtomicBoolean read2Done = new AtomicBoolean(false);

	private final AtomicInteger failures = new AtomicInteger();

	private final AtomicInteger bytesWritten = new AtomicInteger(0);
	private final AtomicInteger bytesRead = new AtomicInteger(0);

	private final AtomicInteger saveTime = new AtomicInteger(0);

	private volatile RuntimeException exceptionThrownByThread;

	private static final ConcurrentHashMap<String, IPageContext> contexts = new ConcurrentHashMap<>();

	private static IPageContext getContext(String sessionId)
	{
		IPageContext context = new MockPageContext(sessionId);

		IPageContext existing = contexts.putIfAbsent(sessionId, context);
		return existing != null ? existing : context;
	}

	private String randomSessionId() {
		List<String> s = new ArrayList<>(sessionCounter.keySet());
		return s.get(random.nextInt(s.size()));
	}

	private int nextSessionId(String sessionId) {
		AtomicInteger i = sessionCounter.get(sessionId);
		return i.incrementAndGet();
	}

	private void generateFiles() {
		for (int i = 0; i < SESSION_COUNT; ++i) {
			sessionCounter.put(UUID.randomUUID().toString(), new AtomicInteger(0));
		}
		for (int i = 0; i < FILES_COUNT; ++i) {
			String session = randomSessionId();
			File file = new File(session, nextSessionId(session));
			long now = System.nanoTime();
			filesToSave.add(file);
			long duration = System.nanoTime() - now;
			saveTime.addAndGet((int)duration);
		}
	}

	private IPageStore dataStore;

	/**
	 * Stores RuntimeException into a field.
	 */
	private abstract class ExceptionCapturingRunnable implements Runnable {
		@Override
		public final void run() {
			try {
				doRun();
			} catch (RuntimeException e) {
				if (exceptionThrownByThread == null) {
					exceptionThrownByThread = e;
				}
			}
		}

		/**
		 * Called by {@link #run()}. Thrown RuntimeExceptions are stores into a field for later
		 * check.
		 */
		protected abstract void doRun();
	}

	// Store/Save data in DataStore
	private class SaveRunnable extends ExceptionCapturingRunnable {
		@Override
		protected void doRun() {
			File file;

			while ((file = filesToSave.poll()) != null || saveCount.get() < FILES_COUNT) {
				if (file != null) {
					byte data[] = file.generateData();

					IPageContext context = getContext(file.getSessionId());
					dataStore.addPage(context, new SerializedPage(file.getId(), data));

					if (saveCount.get() % READ_MODULO == 0) {
						filesToRead1.add(file);
					}
					saveCount.incrementAndGet();
					bytesWritten.addAndGet(data.length);
				}

				try {
					Thread.sleep(WAIT_TIME);
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
			}

			saveDone.set(true);
		}
	}

	// Read data from DataStore
	private class Read1Runnable extends ExceptionCapturingRunnable {
		@Override
		protected void doRun() {
			File file;
			while ((file = filesToRead1.poll()) != null || !saveDone.get()) {
				if (file != null) {
					IPageContext context = getContext(file.getSessionId());

					SerializedPage page = (SerializedPage) dataStore.getPage(context, file.getId());
					if (!file.checkData(page.getData())) {
						failures.incrementAndGet();
						log.error("Detected error number: {}", failures.get());
					}
					filesToRead2.add(file);
					read1Count.incrementAndGet();
					bytesRead.addAndGet(page.getData().length);
				}

				try {
					Thread.sleep(WAIT_TIME);
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
			}

			read1Done.set(true);
		}
	}

	private class Read2Runnable extends ExceptionCapturingRunnable {
		@Override
		protected void doRun() {
			File file;
			while ((file = filesToRead2.poll()) != null || !read1Done.get()) {
				if (file != null) {
					IPageContext context = getContext(file.getSessionId());

					SerializedPage page = (SerializedPage) dataStore.getPage(context, file.getId());
					if (!file.checkData(page.getData())) {
						failures.incrementAndGet();
						log.error("Detected error number: {}", failures.get());
					}
					read2Count.incrementAndGet();
					bytesRead.addAndGet(page.getData().length);
				}

				try {
					Thread.sleep(WAIT_TIME);
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
			}

			read2Done.set(true);
		}
	}

	private void doTestDataStore() {
		log.info("Starting...");
		long start = System.currentTimeMillis();

		for (int i = 0; i < THREAD_COUNT; ++i) {
			new Thread(new Read1Runnable()).start();
		}

		for (int i = 0; i < THREAD_COUNT; ++i) {
			new Thread(new Read2Runnable()).start();
		}

		for (int i = 0; i < THREAD_COUNT; ++i) {
			new Thread(new SaveRunnable()).start();
		}

		while (!(read1Done.get() && read2Done.get() && saveDone.get())) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (exceptionThrownByThread != null) {
			throw new RuntimeException("One of the worker threads failed.", exceptionThrownByThread);
		}

		long duration = System.currentTimeMillis() - start;

		log.info("Took: {} ms", duration);
		log.info("Save: {} files,  bytes", saveCount.intValue(), bytesWritten.get());
		log.info("Read: {} files, {} bytes", (read1Count.get() + read2Count.get()), bytesRead.get());

		log.info("Average save time (ns): {}", (double)saveTime.get() / (double)saveCount.get());

		assertEquals(0, failures.get());

		for (String s : sessionCounter.keySet()) {
			IPageContext context = getContext(s);

			dataStore.removeAllPages(context);
		}
	}

	/**
	 * Performance/load test for IPageStore implementations.
	 * Starts one thread that writes data and two that read.
	 *
	 * Slightly modified version of Wicket's DiskDataStoreTest
	 */
	@Test
	public void load() throws Exception {
		generateFiles();

		dataStore = createDataStore();
		dataStore = new SessionQuotaManagingDataStore(dataStore, Bytes.megabytes(100));

		doTestDataStore();

		dataStore.destroy();
	}

	protected abstract IPageStore createDataStore() throws Exception;
}
