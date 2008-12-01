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
package org.wicketstuff.objectautocomplete.example;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.wicketstuff.objectautocomplete.AutoCompletionChoicesProvider;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteField;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteRenderer;

import java.io.Serializable;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * @author roland
 * @since May 26, 2008
 */
abstract public class BaseExamplePage<O extends Serializable,I extends Serializable>
        extends WebPage implements AutoCompletionChoicesProvider<O> {

    protected ObjectAutoCompleteField<O,I> acField;

    protected BaseExamplePage() {
        this(new Model<I>());
    }

    public BaseExamplePage(IModel<I> pModel) {
        super(pModel);
        initExample();
    }

    private void initExample() {
        ObjectAutoCompleteBuilder<O,I> builder =
                new ObjectAutoCompleteBuilder<O,I>(this);
        initBuilder(builder);

        acField = builder.build("acField", getModel());

        final Form form = new Form("form");
        add(form);
        form.add(acField);
        form.add(new Label("acLabel",getAutoCompleteFieldLabel()));

        // Add code sample and list of sample data
        add(new Label("acCodeSample",getCodeSample()));
        add(new Label("acHtmlSample",getHtmlSample()));

        add(new DataView<O>("acData",new ListDataProvider<O>(getAllChoices())) {
            @Override
            protected void populateItem(Item<O> item) {
                O object = item.getModelObject();
                item.add(new Label("id",
                        PropertyResolver.getValue(getIdProperty(),object).toString()));
                item.add(new Label("name",
                        PropertyResolver.getValue(getNameProperty(),object).toString()));
            }
        });

        WebMarkupContainer wac = new WebMarkupContainer("submitButtonPanel") {
            public boolean isVisible() {
                return needsFormButton();
            }
        };
        Button submitButton = new Button("submitButton") {
            public void onSubmit() {
                System.out.println("Clicked");
                super.onSubmit();
            }
        };
        wac.add(submitButton);
        form.add(wac);
        add(new FeedbackPanel("feedbackPanel"));
    }

    protected boolean needsFormButton() {
        return false;
    }
    /**
     * Override to initialize the builder. Does nothing
     * in this base class.
     *
     * @param pBuilder builder to initialize.
     */
    protected void initBuilder(ObjectAutoCompleteBuilder<O,I> pBuilder) {
        // intentionally empty
    }

    protected String getAutoCompleteFieldLabel() {
       return "Brand:";
    }

    // id-property used for presenting the list of alternatives
    protected  String getIdProperty() {
        return "id";
    }

    // name property used to present the list of alternatives
    protected String getNameProperty() {
        return "name";
    }

    // used to get the list of all possible choices
    abstract List<O> getAllChoices();

    // a sample of the usage for this code
    abstract String getCodeSample();

    // a HTML sample using this code
    abstract String getHtmlSample();

    public IModel<I> getModel() {
        return (IModel<I>) getDefaultModel();
    }

    public Iterator getChoices(String input) {
        List<O> cars = getAllChoices();
        List<O> ret = new ArrayList<O>();
        for (O car : cars) {
            addIfMatch(ret,car,input);
        }
        return ret.iterator();
    }

    abstract protected void addIfMatch(List<O> pList, O pElement, String pInput);
}
