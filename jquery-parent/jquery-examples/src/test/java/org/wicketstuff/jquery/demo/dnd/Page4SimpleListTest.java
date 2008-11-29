package org.wicketstuff.jquery.demo.dnd;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.util.tester.WicketTester;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wicketstuff.jquery.dnd.DnDSortableHandler;

@Test
@SuppressWarnings("serial")
public class Page4SimpleListTest {

    private WicketTester tester_;

    @BeforeClass
    public void setUp() {
        tester_ = new WicketTester();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDnD() throws Exception {
        Page4SimpleList page = new Page4SimpleList();
        // start and render the test page
        tester_.startPage(page);
        // assert rendered page class
        tester_.assertRenderedPage(page.getClass());
        tester_.assertNoInfoMessage();

        WebMarkupContainer webList = (WebMarkupContainer) page.get("myItemList");
        WebMarkupContainer list = (WebMarkupContainer) webList.get("myItem");
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        for (Iterator<ListItem> it = (Iterator<ListItem>) list.iterator(); it.hasNext();) {
            items.add(it.next());
        }

        ArrayList<MyItem> originalDataList = new ArrayList<MyItem>();
        for(MyItem myItem : Page4SimpleList.dataList_) {
            originalDataList.add(myItem);
        }


        assertEquals(Page4SimpleList.dataList_.get(0), originalDataList.get(0));
        assertEquals(Page4SimpleList.dataList_.get(1), originalDataList.get(1));
        assertEquals(Page4SimpleList.dataList_.get(2), originalDataList.get(2));
        assertEquals(Page4SimpleList.dataList_.get(3), originalDataList.get(3));

        DnDSortableHandler dnd = (DnDSortableHandler) page.get("dnd");
        dnd.onDnD(null, webList, 1, webList, 3);

        // model is updated
        assertEquals(Page4SimpleList.dataList_.get(0), originalDataList.get(0));
        assertEquals(Page4SimpleList.dataList_.get(1), originalDataList.get(2));
        assertEquals(Page4SimpleList.dataList_.get(2), originalDataList.get(3));
        assertEquals(Page4SimpleList.dataList_.get(3), originalDataList.get(1));

        // but wicket component isn't updated (wicket always component use the current)
        assertEquals(Page4SimpleList.dataList_.get(0), (MyItem)items.get(0).getModelObject());
        assertEquals(Page4SimpleList.dataList_.get(1), (MyItem)items.get(1).getModelObject());
        assertEquals(Page4SimpleList.dataList_.get(2), (MyItem)items.get(2).getModelObject());
        assertEquals(Page4SimpleList.dataList_.get(3), (MyItem)items.get(3).getModelObject());

        dnd.onDnD(null, webList, 1, webList, 3);

        // model is updated
        assertEquals(Page4SimpleList.dataList_.get(0), originalDataList.get(0));
        assertEquals(Page4SimpleList.dataList_.get(1), originalDataList.get(3));
        assertEquals(Page4SimpleList.dataList_.get(2), originalDataList.get(1));
        assertEquals(Page4SimpleList.dataList_.get(3), originalDataList.get(2));

    }

    protected void assertEquals(MyItem actual, MyItem expected) throws Exception {
        if (actual != expected) {
            Assert.assertEquals(actual.label, expected.label);
            Assert.assertEquals(actual.description, expected.description);
        }
    }
}
