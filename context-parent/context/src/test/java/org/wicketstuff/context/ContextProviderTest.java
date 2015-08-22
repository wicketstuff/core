package org.wicketstuff.context;

import java.io.Serializable;
import java.util.Set;

import junit.framework.Assert;

import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class ContextProviderTest {
    
    @Test
    public void test() {
        WicketTester tester = new WicketTester();
        tester.getApplication().getComponentInitializationListeners().add(new ContextProvider());
        
        MyPanel mypanel = tester.startComponentInPage(MyPanel.class);
        TestComponent component = (TestComponent) mypanel.get("comp");
        Assert.assertEquals(mypanel, component.myPanel);
        Assert.assertNotNull(component.instanceMyPanel);
        Assert.assertEquals(mypanel, component.instanceMyPanel.get());
        Assert.assertNotNull(component.model);
        Assert.assertEquals(mypanel.getDefaultModel(), component.model.get());
        Assert.assertNotNull(component.datas);
        Assert.assertEquals(1, component.datas.size());
        Assert.assertEquals(mypanel.getDefaultModelObject(), component.datas.iterator().next());
    }
    
    public static class TestComponent extends WebMarkupContainer {
        private static final long serialVersionUID = 1L;
        
        @Context MyPanel myPanel;
        @Context Instance<MyPanel> instanceMyPanel;
        @Context Instance<IModel<IData>> model;
        @Context Set<IData> datas;

        public TestComponent(String pId) {
            super(pId);
        }
    }
    
    public static class MyPanel extends Panel {
        private static final long serialVersionUID = 1L;

        public MyPanel(String pId) {
            super(pId, new MyModel(new Data()));
            add(new TestComponent("comp"));
        }
        
        @Override
        public Markup getAssociatedMarkup() {
            return Markup.of("<wicket:panel><span wicket:id=\"comp\"></span></wicket:panel>");
        }
    }
    
    public interface IData {}
    
    public static class Data implements IData, Serializable {}
    
    public static class MyModel extends Model<Data> {
        private Data data;
        public MyModel(Data pData) {
            data = pData;
        }
        @Override
        public Data getObject() {
            return data;
        }
        @Override
        public void setObject(Data pObject) {
            data = pObject;
        }
    }
}
