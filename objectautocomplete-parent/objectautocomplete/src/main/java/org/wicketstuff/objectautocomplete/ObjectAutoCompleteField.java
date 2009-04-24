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
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.*;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.value.IValueMap;

import java.io.Serializable;
import java.util.*;

/**
 * Wicket component for selecting a single object of type T with an identifier of type I via
 * autocompletion. The textfield used for autocompletion is nothing more than a <em>search field</em>
 * where the autocomplete menu presents the search results. Selecting an entry of the search results
 * (either via keyboard or mouse click) "picks" an entity, whose id is stored in the model
 * of this component (So, the model object type of this model is I).
 *
 * A subclass must provide the list of T-objects, which are presented in the autocompletion menu.
 *
 * @author roland
 * @since May 21, 2008
 */
public class ObjectAutoCompleteField<O /* object */,I /* its id */ extends Serializable> extends FormComponentPanel<I>
        implements ObjectAutoCompleteCancelListener {

    // Used for removing non-conformant span attributes
    final private static Set<String> ALLOWED_SPAN_ATTRIBUTES =
            new HashSet<String>(
                    Arrays.asList("wicket:id","id", "class", "lang", "dir", "title",
                                  "style", "align", "onclick","ondblclick", "onmousedown",
                                  "onmouseup", "onmouseover", "onmousemove",
                                  "onmouseout", "onkeypress", "onkeydown", "onkeyup")
            );

    // Listener to be notified on a selection change. These need not to be components
    private List<ObjectAutoCompleteSelectionChangeListener<I>> selectionChangeListeners =
            new ArrayList<ObjectAutoCompleteSelectionChangeListener<I>>();

    // Remember old id in case a search operation is aborted
    private I selectedObjectId;

    // Used for state handling
    private I backupObjectId;
    private String backupText;

    // Textfield used to search for the object
    private TextField<String> searchTextField;

    // Hiddenfield carrying the selected object id
    private HiddenField<I> objectField;

    // whether the input field should be cleared on selection
    private boolean clearInputOnSelection;

    /**
     * Package scoped constructor to be used by the builder to create an auto completion fuild via
     * the builder pattern. I.e. use {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder#build(String)}
     * for creating an object auto completion component
     *
     * @param pId id of the component to add
     * @param pModel the model
     * @param pBuilder builder object used to create this field.
     */
    ObjectAutoCompleteField(String pId, IModel<I> pModel,ObjectAutoCompleteBuilder<O,I> pBuilder) {
        super(pId, pModel);

        // Remember original object. This is the one we are working on.
        selectedObjectId = getModelObject();

        setOutputMarkupId(true);

        // Register ourself as a cancel listener to restore asn old id
        pBuilder.cancelListener(this);

        // Register all change selection listeners
        for (ObjectAutoCompleteSelectionChangeListener<I> listener : pBuilder.selectionChangeListener) {
            registerForUpdateOnSelectionChange(listener);
        }

        // Search Text model contains the text selected
        Model<String> searchTextModel = new Model<String>();
        addSearchTextField(searchTextModel, pBuilder);
        addReadOnlyPanel(searchTextModel,pBuilder);

        clearInputOnSelection = pBuilder.clearInputOnSelection;
    }


    /**
     * Register a listener that needs to be updated when the selection changes, i.e.
     * the user selected an object from the suggestion list. The listener is called
     * with the id-model and the ajax request target which can be used for updating one
     * self
     *
     * @param pListener the listener to notify
     */
    public void registerForUpdateOnSelectionChange(ObjectAutoCompleteSelectionChangeListener<I> pListener) {
        selectionChangeListeners.add(pListener);
    }

    // ==========================================================================================================

    // the 'search part' if in lookup mode
    private void addSearchTextField(final Model<String> pSearchTextModel, ObjectAutoCompleteBuilder<O,I> pBuilder) {
        searchTextField = new TextField<String>("search",pSearchTextModel) {
            @Override
            public boolean isVisible() {
                return isSearchMode();
            }
        };
        searchTextField.setOutputMarkupId(true);
        // this disables Firefox autocomplete
        searchTextField.add(new SimpleAttributeModifier("autocomplete", "off"));

        objectField = new HiddenField<I>("hiddenId",new PropertyModel<I>(this,"selectedObjectId"));
        objectField.setOutputMarkupId(true);
        if (pBuilder.idType != null) {
            objectField.setType(pBuilder.idType);
        }
        add(objectField);

        searchTextField.add(
                pBuilder.buildBehavior(objectField),
                new ObjectUpdateBehavior()
                //newFormUpdateBehaviour(searchTextField)
        );

        add(searchTextField);
    }

    @Override
    protected IModel<I> initModel() {
        IModel model = super.initModel();
        if (model instanceof IWrapModel) {
            IWrapModel iwModel = (IWrapModel) model;
            if (iwModel.getWrappedModel() instanceof CompoundPropertyModel) {
                CompoundPropertyModel<I> cpModel =  (CompoundPropertyModel<I>) iwModel.getWrappedModel();
                objectField.setModel(new PropertyModel<I>(cpModel,getId()));
            }
        }
        return model;
    }

    // the 'read only part' if the object has been selected
    private void addReadOnlyPanel(final Model<String> pSearchTextModel, ObjectAutoCompleteBuilder<O,I> pBuilder) {
        final WebMarkupContainer wac = new WebMarkupContainer("readOnlyPanel") {
            @Override
            public boolean isVisible() {
                return !isSearchMode();
            }
        };
        wac.setOutputMarkupId(true);

        ObjectReadOnlyRenderer<I> roRenderer = pBuilder.readOnlyObjectRenderer;

        Component objectReadOnlyComponent;
        if (roRenderer != null) {
            objectReadOnlyComponent =
                    roRenderer.getObjectRenderer(
                            "selectedValue", new PropertyModel<I>(this,"selectedObjectId"),pSearchTextModel);
        } else {
            objectReadOnlyComponent =
                    new Label("selectedValue",pSearchTextModel);
        }
        objectReadOnlyComponent.setOutputMarkupId(true);

        AjaxFallbackLink deleteLink = new AjaxFallbackLink("searchLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                changeToSearchMode(target);
            }
        };

        Component linkImage = new Image("searchLinkImage").setVisible(false);
        if (pBuilder.imageResource != null || pBuilder.imageResourceReference != null) {
            linkImage = pBuilder.imageResource != null ?
                    new Image("searchLinkImage",pBuilder.imageResource) :
                    new Image("searchLinkImage",pBuilder.imageResourceReference);
            deleteLink.add(new Label(ObjectAutoCompleteBuilder.SEARCH_LINK_PANEL_ID).setVisible(false));
        } else if (pBuilder.searchLinkContent != null) {
            deleteLink.add(pBuilder.searchLinkContent);
        } else if (!pBuilder.unchangeable) {
            deleteLink.add(new Label(ObjectAutoCompleteBuilder.SEARCH_LINK_PANEL_ID,pBuilder.searchLinkText));
        }
        deleteLink.add(linkImage);

        if (pBuilder.searchOnClick) {
            deleteLink.setVisible(false);
            objectReadOnlyComponent.add(new AjaxEventBehavior("onclick") {
                @Override
                protected void onEvent(AjaxRequestTarget target) {
                    changeToSearchMode(target);
                }
            });
        }
        wac.add(objectReadOnlyComponent);
        wac.add(deleteLink);
        add(wac);
    }

    private void changeToSearchMode(AjaxRequestTarget target) {
        backupObjectId = selectedObjectId;
        backupText = searchTextField.getModelObject();
        selectedObjectId = null;
        ObjectAutoCompleteField.this.setModelObject(null);
        if (target != null) {
            target.addComponent(ObjectAutoCompleteField.this);
            String id = searchTextField.getMarkupId();
            target.appendJavascript(
                    "wicketGet('" +id +"').focus();" +
                            "wicketGet('" + id + "').select();");
        }
    }

    /**
     * Callback called in case the user cancels a search via 'escape'
     *
     * @param pTarget target to which the components to update are added
     */
    public void searchCanceled(AjaxRequestTarget pTarget,boolean pForceRestore) {
        if (backupObjectId != null) {
            if (Strings.isEmpty(searchTextField.getModelObject()) && !pForceRestore) {
                searchTextField.setModelObject(null);
                backupText = null;
                backupObjectId = null;
            } else if (backupText != null) {
                searchTextField.setModelObject(backupText);
            }
            selectedObjectId = backupObjectId;
            pTarget.addComponent(ObjectAutoCompleteField.this);
        } else {
            clearSearchInput();
            pTarget.addComponent(searchTextField);
        }
        notifyListeners(pTarget);
    }

    private void clearSearchInput() {
        searchTextField.setModelObject(null);
        selectedObjectId = null;
        searchTextField.clearInput();
        backupText = null;
    }

    // mode detection based on the existance of a seleced model
    private boolean isSearchMode() {
        return selectedObjectId == null;
    }

    /**
     * Called when form is submitted. We are simply store the object remembered internally.
     */
    @Override
    protected void convertInput() {
        setConvertedInput(selectedObjectId);
    }

    @Override
    // ensure that this component is embedded in a <span> ... </span>
    protected void onComponentTag(ComponentTag pTag) {
        super.onComponentTag(pTag);
        pTag.setName("span");
        // Remove non-conformant <span> attributes
        IValueMap attribMap = pTag.getAttributes();
        Iterator<Map.Entry<String,Object>> attrIterator = attribMap.entrySet().iterator();
        while (attrIterator.hasNext()) {
            Map.Entry<String,Object> entry = attrIterator.next();
            String key = entry.getKey().toLowerCase();
            if (!ALLOWED_SPAN_ATTRIBUTES.contains(key)) {
                attrIterator.remove();
            }
        }
    }

    private void notifyListeners(AjaxRequestTarget pTarget) {
        for (ObjectAutoCompleteSelectionChangeListener<I> listener : selectionChangeListeners) {
            listener.selectionChanged(pTarget,objectField.getModel());
        }

    }

    /**
     * Access to internal text field which can be used for adding
     * some decorations or behaviours (like JS-Handler or specific
     * CSS). Please be conservative in what you are doing, i.e.
     * do not modify its model.
     *
     * Note: This method might vanish until the first release, it's probably
     * better to provide delegation methods.
     *
     * @return textfield textfield holding the search input
     */
    public TextField<String> getSearchTextField() {
        return searchTextField;
    }

    // =========================================================================================

    //  Behaviour, when user leaves the input field.
    class ObjectUpdateBehavior extends AjaxEventBehavior {

        ObjectUpdateBehavior() {
            super("onchange");
        }

        @Override
        protected void onEvent(AjaxRequestTarget target) {
            objectField.processInput();
            searchTextField.processInput();
            target.addComponent(ObjectAutoCompleteField.this);
            notifyListeners(target);
            if (clearInputOnSelection) {
                clearSearchInput();
            }
        }

        @Override
        protected CharSequence getEventHandler()
        {
            return generateCallbackScript(new AppendingStringBuffer("wicketAjaxPost('").append(
                    getCallbackUrl(false)).append(
                    "', wicketSerialize(Wicket.$('" + searchTextField.getMarkupId() + "')) + " +
                            "wicketSerialize(Wicket.$('" + objectField.getMarkupId() + "'))"));
        }
    }

}
