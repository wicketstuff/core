package org.wicketstuff.jquery.demo.dnd;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.jquery.dnd.DnDSortableHandler;

public class Page4SimpleListTest
{

	private WicketTester tester_;

	@Before
	public void setUp()
	{
		tester_ = new WicketTester();
	}

    @After
    public void tearDown()
    {
        tester_.destroy();
        tester_ = null;
    }

	@Test
	public void testOnDnD() throws Exception
	{
		Page4SimpleList page = new Page4SimpleList();
		// start and render the test page
		tester_.startPage(page);
		// assert rendered page class
		tester_.assertRenderedPage(page.getClass());
		tester_.assertNoInfoMessage();

		WebMarkupContainer webList = (WebMarkupContainer)page.get("myItemList");
		WebMarkupContainer list = (WebMarkupContainer)webList.get("myItem");
		ArrayList<Component> items = new ArrayList<Component>();
		for (Iterator<Component> it = list.iterator(); it.hasNext();)
		{
			items.add(it.next());
		}

		ArrayList<MyItem> originalDataList = new ArrayList<MyItem>();
		for (MyItem myItem : Page4SimpleList.dataList_)
		{
			originalDataList.add(myItem);
		}


		assertEquals(Page4SimpleList.dataList_.get(0), originalDataList.get(0));
		assertEquals(Page4SimpleList.dataList_.get(1), originalDataList.get(1));
		assertEquals(Page4SimpleList.dataList_.get(2), originalDataList.get(2));
		assertEquals(Page4SimpleList.dataList_.get(3), originalDataList.get(3));

		DnDSortableHandler dnd = (DnDSortableHandler)page.get("dnd");
		dnd.onDnD(null, webList, 1, webList, 3);

		// model is updated
		assertEquals(Page4SimpleList.dataList_.get(0), originalDataList.get(0));
		assertEquals(Page4SimpleList.dataList_.get(1), originalDataList.get(2));
		assertEquals(Page4SimpleList.dataList_.get(2), originalDataList.get(3));
		assertEquals(Page4SimpleList.dataList_.get(3), originalDataList.get(1));

		// but wicket component isn't updated (wicket always component use the current)
		assertEquals(Page4SimpleList.dataList_.get(0), (MyItem)items.get(0).getDefaultModelObject());
		assertEquals(Page4SimpleList.dataList_.get(1), (MyItem)items.get(1).getDefaultModelObject());
		assertEquals(Page4SimpleList.dataList_.get(2), (MyItem)items.get(2).getDefaultModelObject());
		assertEquals(Page4SimpleList.dataList_.get(3), (MyItem)items.get(3).getDefaultModelObject());

		dnd.onDnD(null, webList, 1, webList, 3);

		// model is updated
		assertEquals(Page4SimpleList.dataList_.get(0), originalDataList.get(0));
		assertEquals(Page4SimpleList.dataList_.get(1), originalDataList.get(3));
		assertEquals(Page4SimpleList.dataList_.get(2), originalDataList.get(1));
		assertEquals(Page4SimpleList.dataList_.get(3), originalDataList.get(2));

	}

	protected void assertEquals(MyItem actual, MyItem expected) throws Exception
	{
		if (actual != expected)
		{
			Assert.assertEquals(actual.label, expected.label);
			Assert.assertEquals(actual.description, expected.description);
		}
	}
}
