package org.wicketstuff.datastores.memcached;

import org.apache.wicket.util.lang.Bytes;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class SessionResourcesManagerTest extends Assert
{
	private final String sessionId = "abcd";
	private final String sessionId2 = "efgh";

	private final int pageId1 = 1;
	private final byte[] pageData1 = new byte[] {1, 2, 3, 4};

	private final int pageId2 = 2;
	private final byte[] pageData2 = new byte[] {5, 6, 7};

	@Test
	public void removeData() throws Exception
	{
		SessionResourcesManager manager = new SessionResourcesManager(Bytes.bytes(100));

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
	public void storeDataEnoughSpace() throws Exception
	{
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + pageData2.length + 1);
		SessionResourcesManager manager = new SessionResourcesManager(maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		manager.storeData(sessionId2, pageId2, pageData2);
		assertEquals(2, manager.pagesPerSession.size());

		SessionResourcesManager.SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(2, manager.pagesPerSession.size());

		assertEquals(2, sessionData.pages.size());
	}

	@Test
	public void storeDataInsufficientSpaceForTwoPages() throws Exception
	{
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length);
		SessionResourcesManager manager = new SessionResourcesManager(maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionResourcesManager.SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId1, sessionData.pages.element().pageId);

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId2, sessionData.pages.element().pageId);
	}

	@Test
	public void storeDataInsufficientSpaceForASinglePage() throws Exception
	{
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length - 1);
		SessionResourcesManager manager = new SessionResourcesManager(maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionResourcesManager.SessionData sessionData = manager.pagesPerSession.get(sessionId);

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId1, sessionData.pages.element().pageId);

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());

		assertEquals(1, sessionData.pages.size());
		assertEquals(pageId2, sessionData.pages.element().pageId);
	}

	@Test
	public void removePage() throws Exception
	{
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + pageData2.length + 1);
		SessionResourcesManager manager = new SessionResourcesManager(maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		SessionResourcesManager.SessionData sessionData = manager.pagesPerSession.get(sessionId);
		assertEquals(1, sessionData.pages.size());

		manager.storeData(sessionId, pageId2, pageData2);
		assertEquals(1, manager.pagesPerSession.size());
		assertEquals(2, sessionData.pages.size());

		manager.removePage(sessionId, pageId1);
		assertEquals(1, manager.pagesPerSession.size());
		assertEquals(1, sessionData.pages.size());

		manager.removePage(sessionId, pageId2);
		// removing the last page should remove the session data too
		assertEquals(0, manager.pagesPerSession.size());
	}

	@Test
	public void destroy() throws Exception
	{
		Bytes maxSizePerSession = Bytes.bytes(pageData1.length + 1);
		SessionResourcesManager manager = new SessionResourcesManager(maxSizePerSession);

		assertEquals(0, manager.pagesPerSession.size());

		manager.storeData(sessionId, pageId1, pageData1);
		assertEquals(1, manager.pagesPerSession.size());

		manager.destroy();

		assertEquals(0, manager.pagesPerSession.size());
	}
}
