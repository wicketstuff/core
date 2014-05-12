package org.wicketstuff.context.examples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.context.Context;
import org.wicketstuff.context.Instance;
import org.wicketstuff.context.Qualifier;
import org.wicketstuff.context.Traversal;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage() {
        super(Model.of(new PageData(Arrays.asList(1,2,3,4,5))));
        WebMarkupContainer container = new WebMarkupContainer("container");
        add(container.setOutputMarkupId(true));
        container.add(new ListView<Integer>("view", new PropertyModel<List<Integer>>(getDefaultModel(), "elements")) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<Integer> pItem) {
                pItem.add(new CellNumber("cell", pItem.getModel()));
            }
        });
        
        container.add(new AjaxLink<Void>("increment") {
            private static final long serialVersionUID = 1L;
            // locate the model that holds PageData instance
            @Context private IModel<PageData> pageData;
            // parent container
            @Context private Instance<WebMarkupContainer> container;
            @Override
            public void onClick(AjaxRequestTarget pTarget) {
                pageData.getObject().addElement(new Random().nextInt(100));
                pTarget.add(container.get());
            }
        });
        
        add(new AjaxLink<Void>("odd") {
            private static final long serialVersionUID = 1L;
            // locate all cellnumber panels with qualifier 'odd'
            @Context(traversal=Traversal.TOP_DOWN, qualifier="odd") private Instance<Set<CellNumber>> numbers;

            @Override
            public void onClick(AjaxRequestTarget pTarget) {
                pTarget.add(numbers.get().toArray(new Component[0]));
            }
        });
        
        add(new AjaxLink<Void>("even") {
            private static final long serialVersionUID = 1L;
            @Context(traversal=Traversal.TOP_DOWN, qualifier="even") private Instance<Set<CellNumber>> numbers;

            @Override
            public void onClick(AjaxRequestTarget pTarget) {
                pTarget.add(numbers.get().toArray(new Component[0]));
            }
        });
    }
    
    private static class PageData implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private List<Integer> elements;
        
        public PageData(List<Integer> pElements) {
            elements = new ArrayList<Integer>(pElements);
        }
        
        public List<Integer> getElements() {
            return elements;
        }
        public void addElement(Integer pElement) {
            elements.add(pElement);
        }
    }
    
    public static class CellNumber extends WebMarkupContainer {
        private static final long serialVersionUID = 1L;
        
        public CellNumber(String pId, IModel<Integer> pModel) {
            super(pId, pModel);
            setOutputMarkupId(true);
            switch(pModel.getObject() % 2) {
                case 0: add(new Qualifier("even")); break;
                case 1: add(new Qualifier("odd")); break;
                default:
            }
            
            add(new Label("info", pModel));
        }
        
        @Override
        public void renderHead(IHeaderResponse pResponse) {
            super.renderHead(pResponse);
            pResponse.render(new OnDomReadyHeaderItem(
                    String.format(" $('#%s').fadeOut('slow', function() { "
                                  + "   $(this).fadeIn('slow', function() {})"
                                  + "})", getMarkupId())));    
        }
    }
}
