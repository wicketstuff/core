package org.wicketstuff.datastores.common;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Bytes;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for SessionQuotaManagingDataStore
 */
public class SessionQuotaManagingDataStoreTest extends Assert {

	private final IDataStore delegate = new NoopDataStore();

	private final String sessionId = "abcd";
	private final String sessionId2 = "efgh";

	private final int pageId1 = 1;
	private final byte[] pageData1 = new byte[] {1, 2, 3, 4};

	private final int pageId2 = 2;
	private final byte[] pageData2 = new byte[] {5, 6, 7};

	@Test
	public void removeData() throws Exception {
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, Bytes.bytes(100));

		assertEquals(0, manager.pagesPerSession.size());
		manager.removeData(sessionId);
		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());

		manager.storeData(sessionId2, pageId2, pageData2);
		assertEquals(2, manager.pagesPerSession.size());

		manager.removeData(sessionId);
		assertEquals(1, manager.pagesPerSession.size());

		manager.removeData(sessionId2);
		assertEquals(0, manager.pagesPerSession.size());
	}

	@Test
	public void storeDataEnoughSpace() throws Exception {
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + pageData2.length + 1);
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		manager.storeData(sessionId2, pageId2, pageData2);
		assertEquals(2, manager.pagesPerSession.size());

		SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(2, manager.pagesPerSession.size());

		assertEquals(2, sessionData.pages.size());
	}

	@Test
	public void storeDataInsufficientSpaceForTwoPages() throws Exception {
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length);
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId1, sessionData.pages.element().pageId);

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId2, sessionData.pages.element().pageId);
	}

	@Test
	public void storeDataInsufficientSpaceForASinglePage() throws Exception {
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length - 1);
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId1, sessionData.pages.element().pageId);

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId2, sessionData.pages.element().pageId);
	}

	/**
	 * Tests that storing a page with id that already exists will override
	 * the older entry
	 *
	 * @throws Exception
	 */
	@Test
	public void storeDataSamePageTwice() throws Exception {
		byte[] pageData1_2 = new byte[] { 1 };
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + pageData1_2.length + 1);
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId1, sessionData.pages.element().pageId);
		assertEquals(pageData1.length, sessionData.size);

		manager.storeData(sessionId, pageId1, pageData1_2);
		assertEquals(1, manager.pagesPerSession.size());

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId1, sessionData.pages.element().pageId);
		assertEquals(pageData1_2.length, sessionData.size);
	}

	@Test
	public void removePage() throws Exception {
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + pageData2.length + 1);
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionData sessionData = manager.pagesPerSession.get(sessionId);
		assertEquals(1, sessionData.pages.size());
		assertEquals(pageData1.length, sessionData.size);

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());
		assertEquals(2, sessionData.pages.size());
		assertEquals(pageData1.length + pageData2.length, sessionData.size);

		manager.removeData(sessionId, pageId1);
		assertEquals(1, manager.pagesPerSession.size());
		assertEquals(1, sessionData.pages.size());
		assertEquals(pageData2.length, sessionData.size);

		manager.removeData(sessionId, pageId2);
		// removing the last page should remove the session data too
		assertEquals(0, manager.pagesPerSession.size());
	}

	@Test
	public void destroy() throws Exception {
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + 1);
		SessionQuotaManagingDataStore manager = new SessionQuotaManagingDataStore(delegate, maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		manager.destroy();

		assertEquals(0, manager.pagesPerSession.size());
	}

	private static class NoopDataStore implements IDataStore {
		@Override
		public byte[] getData(String sessionId, int id) {
			return new byte[0];
		}

		@Override
		public void removeData(String sessionId, int id) {
		}

		@Override
		public void removeData(String sessionId) {
		}

		@Override
		public void storeData(String sessionId, int id, byte[] data) {
		}

		@Override
		public void destroy() {
		}

		@Override
		public boolean isReplicated() {
			return false;
		}

		@Override
		public boolean canBeAsynchronous() {
			return false;
		}
	}
}
