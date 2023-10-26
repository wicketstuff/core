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
package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.egrid.component.EditableDataTable;

import java.io.Serial;

/**
 * Adjusted version of Wicket Extensions' NoRecordsToolbar
 * <p>
 * A toolbar that displays a "no records found" message when the data table contains no rows.
 * <p>
 * The message can be overridden by providing a resource with key
 * <code>datatable.no-records-found</code>
 *
 * @author Igor Vaynberg (ivaynberg)
 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar
 */
public class NoRecordsToolbar extends AbstractEditableToolbar {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final IModel<String> DEFAULT_MESSAGE_MODEL = new ResourceModel("datatable.no-records-found");

    /**
     * Constructor
     *
     * @param table data table this toolbar will be attached to
     */
    public NoRecordsToolbar(final EditableDataTable<?, ?> table) {
        this(table, DEFAULT_MESSAGE_MODEL);
    }

    /**
     * @param table        data table this toolbar will be attached to
     * @param messageModel model that will be used to display the "no records found" message
     */
    public NoRecordsToolbar(final EditableDataTable<?, ?> table, final IModel<String> messageModel) {
        super(table);

        WebMarkupContainer td = new WebMarkupContainer("td");
        add(td);

        td.add(AttributeModifier.replace("colspan", new IModel<String>() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return String.valueOf(table.getColumns().size()).intern();
            }
        }));
        td.add(new Label("msg", messageModel));
    }

    /**
     * Only shows this toolbar when there are no rows
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(getTable().getRowCount() == 0);
    }
}
