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
package org.wicketstuff;

import java.util.ArrayList;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.multitextinput.MultiTextInput;

/**
 * 
 * Examples page for {@link MultiTextInput}
 * 
 * @author Craig Tataryn (craiger at tataryn dot net)
 * 
 * @param <T>
 */
public class MultiTextInputExamples extends WebPage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public MultiTextInputExamples(final PageParameters parameters) {

        final Label messageAlpha = new Label("messageAlpha", "");
        final MultiTextInput<String> mtiAlpha = new MultiTextInput<String>("multiAlpha", new ArrayList<String>() {
            {
                add("apple");
                add("banana");
                add("<some markup>");
                add("a quote '");
                add("a double quote \"");
            }
        });
        final Label messageInt = new Label("messageInt", "");
        final MultiTextInput<Integer> mtiInt = new MultiTextInput<Integer>("multiInt", new ArrayList<Integer>() {
            {
                add(1);
                add(10);
                add(100);
                add(1000);
            }
        }, Integer.class);
        Form form = new Form("form") {

            @Override
            protected void onSubmit() {
                MultiTextInput.MultiTextInputModel model = (MultiTextInput.MultiTextInputModel) mtiAlpha.getModel();
                messageAlpha.setEscapeModelStrings(false);
                messageAlpha.setDefaultModelObject("<strong>items:</strong> "
                        + Strings.escapeMarkup(model.getObject().toString()) + "<br><strong>removed:</strong> "
                        + Strings.escapeMarkup(model.getRemovedItems().toString()));

                model = (MultiTextInput.MultiTextInputModel) mtiInt.getModel();
                messageInt.setEscapeModelStrings(false);
                messageInt.setDefaultModelObject("<strong>items:</strong> "
                        + Strings.escapeMarkup(model.getObject().toString()) + "<br><strong>removed:</strong> "
                        + Strings.escapeMarkup(model.getRemovedItems().toString()));

                super.onSubmit();
            }

        };

        form.add(messageAlpha);
        form.add(mtiAlpha);
        form.add(messageInt);
        form.add(mtiInt);

        add(new FeedbackPanel("feedback"));
        add(form);

    }
}
