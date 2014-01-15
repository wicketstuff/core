package org.wicketstuff.datastores.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of IDataStore that delegates the actual work to another
 * IDataStore.
 *
 * The extra logic that this class provides is to manage the quota per
 * client (http session). This way one client cannot add too much data
 * in the real/delegate data store and eventually drop another client's data.
 */
public class SessionQuotaManagingDataStore implements IDataStore {

	private static final Logger LOG = LoggerFactory.getLogger(SessionQuotaManagingDataStore.class);

	/**
	 * Tracks the data per session.
	 * Used to track the quota per client/session and
	 * to delete all entries for a client when invalidated.
	 */
	/* package scoped for testing */
	final ConcurrentMap<String, SessionData> pagesPerSession =
			new ConcurrentHashMap<String, SessionData>();

	/**
	 * The maximum bytes a client can store (the quota).
	 * @see org.apache.wicket.settings.IStoreSettings#getMaxSizePerSession()
	 */
	private final Bytes maxSizePerSession;

	/**
	 * The real IDataStore.
	 */
	private final IDataStore delegate;

	/**
	 * Constructor.
	 *
	 * @param delegate  The real IDataStore
	 * @param maxSizePerSession The quota
	 */
	public SessionQuotaManagingDataStore(IDataStore delegate, Bytes maxSizePerSession) {
		this.delegate = Args.notNull(delegate, "delegate");
		this.maxSizePerSession = Args.notNull(maxSizePerSession, "maxSizePerSession");
	}

	@Override
	public byte[] getData(String sessionId, int pageId) {
		return delegate.getData(sessionId, pageId);
	}

	@Override
	public void removeData(String sessionId) {
		SessionData sessionData = pagesPerSession.get(sessionId);

		if (sessionData != null) {
			synchronized (sessionData) {
				delegate.removeData(sessionId);
				pagesPerSession.remove(sessionId);
			}
		}
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] data) {
		SessionData sessionData = pagesPerSession.get(sessionId);

		if (sessionData == null) {
			sessionData = new SessionData(sessionId);
			SessionData old = pagesPerSession.putIfAbsent(sessionId, sessionData);
			if (old != null) {
				sessionData = old;
			}
		}

		int pageSize = data.length;

		Integer removedPageId;
		while ((removedPageId = sessionData.removePageIfQuotaExceeded(pageSize, maxSizePerSession)) != null) {
			LOG.debug("Removing page '{}' from session '{}' because the quota is reached.", removedPageId, sessionId);
			delegate.removeData(sessionId, removedPageId);
		}

		synchronized (sessionData) {
			delegate.storeData(sessionId, pageId, data);

			PageData page = new PageData(pageId, pageSize);
			sessionData.addPage(page);
		}
	}

	@Override
	public void removeData(String sessionId, int pageId) {

		SessionData sessionData = pagesPerSession.get(sessionId);

		if (sessionData != null) {
			synchronized (sessionData) {
				delegate.removeData(sessionId, pageId);

				sessionData.removePage(pageId);

				if (sessionData.pages.isEmpty()) {
					pagesPerSession.remove(sessionId);
				}
			}
		}
	}

	@Override
	public void destroy() {
		pagesPerSession.clear();
		delegate.destroy();
	}

	@Override
	public boolean isReplicated() {
		return delegate.isReplicated();
	}

	@Override
	public boolean canBeAsynchronous() {
		return delegate.canBeAsynchronous();
	}
}
