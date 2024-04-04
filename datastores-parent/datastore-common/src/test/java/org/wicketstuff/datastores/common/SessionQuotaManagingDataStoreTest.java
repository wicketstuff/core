package org.wicketstuff.datastores.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.wicket.mock.MockPageContext;
import org.apache.wicket.mock.MockPageStore;
import org.apache.wicket.pageStore.IPageContext;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.util.lang.Bytes;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SessionQuotaManagingDataStore}
 */
public class SessionQuotaManagingDataStoreTest {

	private SerializedPage page1 = new SerializedPage(1, new byte[4]);

	private SerializedPage page2 = new SerializedPage(2, new byte[3]);

	// smaller version of page1
	private SerializedPage page1_ = new SerializedPage(1, new byte[2]);
	
	private SerializedPage page3 = new SerializedPage(3, new byte[4]);


	@Test
	public void storeDataEnoughSpace() {
		MockPageStore delegate = new MockPageStore();

		IPageContext context = new MockPageContext();

		IPageStore quotaStore = new SessionQuotaManagingDataStore(delegate, Bytes.MAX);
		quotaStore.addPage(context, page1);
		quotaStore.addPage(context, page2);

		assertEquals(2, delegate.getPages().size());
		assertEquals(page1, delegate.getPages().get(0));
		assertEquals(page2, delegate.getPages().get(1));
	}

	@Test
	public void storeDataInsufficientSpace() {
		MockPageStore delegate = new MockPageStore();

		IPageContext context = new MockPageContext();

		IPageStore quotaStore = new SessionQuotaManagingDataStore(delegate, Bytes.bytes(page1.getData().length + 1));
		quotaStore.addPage(context, page1);
		quotaStore.addPage(context, page2);

		assertEquals(1, delegate.getPages().size());
		assertEquals(page2, delegate.getPages().get(0));
	}

	/**
	 * Tests that storing a page with id that already exists will override
	 * the older entry
	 *
	 * @throws Exception
	 */
	@Test
	public void storeDataSamePageTwice() {
		MockPageStore delegate = new MockPageStore();

		IPageContext context = new MockPageContext();

		IPageStore quotaStore = new SessionQuotaManagingDataStore(delegate, Bytes.bytes(page1_.getData().length + page2.getData().length));
		quotaStore.addPage(context, page1);
		quotaStore.addPage(context, page1_);
		quotaStore.addPage(context, page2);

		assertEquals(2, delegate.getPages().size());
		assertEquals(page1_, delegate.getPages().get(0));
		assertEquals(page2, delegate.getPages().get(1));
	}

	@Test
	public void removePage() {
		MockPageStore delegate = new MockPageStore();

		IPageContext context = new MockPageContext();

		IPageStore quotaStore = new SessionQuotaManagingDataStore(delegate, Bytes.bytes(page1.getData().length + page3.getData().length));
		quotaStore.addPage(context, page1);
		quotaStore.addPage(context, page2);
		quotaStore.removePage(context, page2);
		quotaStore.addPage(context, page3);

		assertEquals(2, delegate.getPages().size());
		assertEquals(page1, delegate.getPages().get(0));
		assertEquals(page3, delegate.getPages().get(1));
	}
}