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
package org.wicketstuff.multitextinput;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormModelUpdateListener;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.ConverterLocator;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import org.wicketstuff.prototype.PrototypeResourceReference;

/**
 * <p>
 * A component which allows users to type multiple pieces of text into what
 * looks like a text input.
 * </p>
 * <p>
 * When the user hits the enter key, the text they typed is added to the widget
 * and the user may continue to add more.
 * </p>
 * <p>
 * When the form is submitted two sets of inputs are put into the model, the
 * first is the list of items the user has added (or were already there when the
 * widget was first rendered). These inputs are converted from String to
 * whatever type the {@link #getType()} returns and are placed in the model.
 * </p>
 * <p>
 * The developer retrieves the inputs via the standard
 * {@link IModel#getObject()} way, but the model is also loaded with another
 * Collection, the collection of items which were removed from the widget. These
 * are accessible by casting the model to a MultiTextInputModel and using it's
 * {@link MultiTextInputModel#getRemovedItems()} method
 * </p>
 * <p>
 * The component itself was adapted from an existing javascript widget and thus
 * the Wicket component has no "markup" rendering to speak of. Rendering is all
 * done on the client's browser using javascript
 * </p>
 * <p>
 * The only dependency the component has on the client side is the prototype
 * javascript library, which is included via the WicketStuff prototype include.
 * </p>
 * 
 * @author Craig Tataryn (craiger AT tataryn DOT net)
 * 
 * @param <T>
 *            underlying type of the objects which comprise the model's
 *            collection of items for the widget
 */
public class MultiTextInput<T> extends FormComponent<Collection<T>> {

    private int inputLength = 15;

    /**
     * A Model which holds a collection of items for a MultiTextInput. Another
     * list of items is kept which holds the "removed" items (see:
     * {@link MultiTextInputModel#getRemovedItems()}) from the input.
     * 
     * @author Craig Tataryn (craiger AT tataryn DOT net)
     * 
     * @param <T>
     *            a collection type which holds values for a MultiTextInput
     */
    public static class MultiTextInputModel<T extends Collection<?>> implements IModel<T> {

        private static final long serialVersionUID = 1L;
        private T items;
        private T removedItems;

        public MultiTextInputModel(T items) {
            this.items = items;
        }

        /**
         * Returns a list of items removed from the {@link MultiTextInput}
         * 
         * @return List of items removed {@link MultiTextInput}.
         */
        public T getRemovedItems() {
            return removedItems;
        }

        /**
         * Sets a list of items removed from the {@link MultiTextInput}. Users
         * may find this a convenience when having to update a database table
         * after form submission.
         * 
         * @param removedItems
         */
        public void setRemovedItems(T removedItems) {
            this.removedItems = removedItems;
        }

        /**
         * Gets the items for this model
         * 
         * @see IModel#getObject()
         */
        public T getObject() {
            return this.items;
        }

        /**
         * Sets the items for this model
         * 
         * @see IModel#setObject(Object)
         */
        public void setObject(T object) {
            this.items = object;
        }

        /**
         * Does nothing at the moment
         * 
         * @see IDetachable#detach()
         */
        public void detach() {
            // TODO not sure if there is anything to do.
        }

    }

    private static final long serialVersionUID = 1L;
    final ClientProperties properties = ((WebClientInfo) getRequestCycle().getClientInfo()).getProperties();

    public MultiTextInput(String id, IModel<Collection<T>> model) {
        super(id, model);
    }

    /**
     * Allows the user to create a MultiTextInput using a simple Collection
     * 
     * @param id
     *            of the component
     * @param items
     *            to display when the component is rendered
     */
    public MultiTextInput(String id, Collection<T> items) {
        super(id, new MultiTextInputModel(items));
    }

    /**
     * Allows the user to construct a MultTextInput using a collection of items
     * and a maximum number of characters allowable
     * 
     * @param id
     *            of the component
     * @param items
     *            to display when the component is rendered
     * @param inputLength
     *            maximum number of characters the user is allowed to type
     *            (note, if one of the items in the items parameter contains
     *            more characters, it is still displayed)
     */
    public MultiTextInput(String id, Collection<T> items, int inputLength) {
        super(id, new MultiTextInputModel(items));
        this.inputLength = inputLength;
    }

    public MultiTextInput(String id) {
        super(id);
    }

    /**
     * Constructs a MultiTextInput using a colletion of items and the type of
     * the underlying items in the model. This type is used to convert String
     * values from the user's input to the correct type used in the model
     * 
     * @param id
     *            of the component
     * @param items
     *            to display when the component is rendered
     * @param type
     *            to use when converting user input (see:
     *            {@link ConverterLocator})
     */
    public MultiTextInput(String id, Collection<T> items, Class<T> type) {
        super(id, new MultiTextInputModel<Collection<T>>(items));
        setType(type);
    }

    /**
     * Gets the maximum number of characters a user can type into the input
     * 
     * @return
     */
    public int getInputLength() {
        return inputLength;
    }

    /**
     * Sets the maximum number of characters a user can type into the input
     * 
     * @param inputLength
     */
    public void setInputLength(int inputLength) {
        this.inputLength = inputLength;
    }

    @Override
    public void renderHead(HtmlHeaderContainer container) {
        IHeaderResponse response = container.getHeaderResponse();

        // shouldn't be using MarkupAttributes as it's an internal method, but
        // have to, no other way to
        // find out if the user put an id attribute on the tag,
        // getMarkupId(false) should tell us, but it doens't
        // at this stage in rendering it seems
        String tmpId = this.getMarkupAttributes().getString("id");
        // if they haven't set the id on the component tag, we'll set one for
        // them
        if (Strings.isEmpty(tmpId)) {
            this.setOutputMarkupId(true);
            tmpId = this.getMarkupId(true);
        }
        final String id = tmpId;

        // add prototype
        response.renderJavascriptReference(PrototypeResourceReference.INSTANCE);
        // add this components javascript
        response.renderJavascriptReference(new ResourceReference(this.getClass(), "res/scripts/tag.js"));
        // add component css
        if (properties.isBrowserInternetExplorer()) {
            response.renderCSSReference(new ResourceReference(this.getClass(), "res/stylesheets/tag-ie.css"));
        } else if (properties.isBrowserSafari()) {
            response.renderCSSReference(new ResourceReference(this.getClass(), "res/stylesheets/tag-webkit.css"));
        } else {
            response.renderCSSReference(new ResourceReference(this.getClass(), "res/stylesheets/tag-moz.css"));
        }

        // render the javascript to setup the component
        IModel variablesModel = new AbstractReadOnlyModel() {
            public Map getObject() {
                Map<String, CharSequence> variables = new HashMap<String, CharSequence>(2);
                variables.put("id", id);
                StringBuffer arr = new StringBuffer();
                // join our collection into a comma delimeted string
                Collection<T> model = (Collection<T>) MultiTextInput.this.getInnermostModel().getObject();
                if (model != null) {
                    Iterator<?> iter = model.iterator();
                    while (iter.hasNext()) {
                        arr.append('\'');
                        // looks like a weird substitution, but regexp in java
                        // ftl.
                        arr.append(iter.next().toString().replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\""));
                        arr.append('\'');
                        if (iter.hasNext()) {
                            arr.append(',');
                        }
                    }
                }
                variables.put("model", arr.toString());
                variables.put("length", String.valueOf(MultiTextInput.this.inputLength));
                return variables;
            }
        };
        // merge the javascript from the properties file with the properties we
        // set above
        String js = new StringBuffer().append(getString("javascript.tagEntry", variablesModel)).toString();
        response.renderOnDomReadyJavascript(js.toString());

        super.renderHead(container);
    }

    @Override
    protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);
        // our component tag is not actually the input that is submitted
        if (tag.getAttributes().containsKey("name")) {
            tag.remove("name");
        }
    }

    public void updateModel() {
        super.updateModel();
        // do one more step by setting our model with the removed items
        // in case the user of the component needs this convenience
        String[] removed = getRequest().getParameters("removed_" + getInputName());
        MultiTextInputModel<Collection<T>> model = (MultiTextInputModel<Collection<T>>) this.getModel();
        model.setRemovedItems(convertInput(removed));

    }

    /**
     * Robbed from {@link FormComponent}
     * 
     * @param e
     * @param error
     */
    private void reportValidationError(ConversionException e, ValidationError error) {
        final Locale locale = e.getLocale();
        if (locale != null) {
            error.setVariable("locale", locale);
        }
        error.setVariable("exception", e);
        Format format = e.getFormat();
        if (format instanceof SimpleDateFormat) {
            error.setVariable("format", ((SimpleDateFormat) format).toLocalizedPattern());
        }

        Map<String, Object> variables = e.getVariables();
        if (variables != null) {
            error.getVariables().putAll(variables);
        }

        error((IValidationError) error);
    }

    @Override
    protected Collection<T> convertValue(String[] values) throws ConversionException {
        Collection<T> convertedValues = new ArrayList<T>();
        if (values != null && values.length > 0 && values[0] != null) {
            for (String val : values) {
                convertedValues.add((T) val);
            }
        }
        return convertedValues;
    }

    @SuppressWarnings("unchecked")
    private Collection<T> convertInput(String[] inputs) {
        if (getType() == null) {
            try {
                return convertValue(inputs);
            } catch (ConversionException e) {
                ValidationError error = new ValidationError();
                if (e.getResourceKey() != null) {
                    error.addMessageKey(e.getResourceKey());
                }
                if (e.getTargetType() != null) {
                    error.addMessageKey("ConversionError." + Classes.simpleName(e.getTargetType()));
                }
                error.addMessageKey("ConversionError");
                reportValidationError(e, error);
            }
        } else {
            final IConverter converter = getConverter(getType());
            String curInput = "";
            try {
                Collection<T> convertedInput = new ArrayList<T>();
                if (inputs != null) {
                    for (String input : inputs) {
                        curInput = input;
                        convertedInput.add(((T) converter.convertToObject(curInput, getLocale())));
                    }
                }
                return convertedInput;
            } catch (ConversionException e) {
                ValidationError error = new ValidationError();
                if (e.getResourceKey() != null) {
                    error.addMessageKey(e.getResourceKey());
                }
                String simpleName = Classes.simpleName(getType());
                error.addMessageKey("IConverter." + simpleName);
                error.addMessageKey("IConverter");
                error.setVariable("type", simpleName);
                error.setVariable("input", curInput);
                reportValidationError(e, error);
            }
        }
        return null;
    }

    /**
	 * 
	 */
    @Override
    @SuppressWarnings("unchecked")
    protected void convertInput() {
        String[] inputs = getInputAsArray();

        setConvertedInput(convertInput(inputs));

    }

}
