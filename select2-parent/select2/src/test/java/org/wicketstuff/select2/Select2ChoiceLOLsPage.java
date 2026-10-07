/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cdjost
 */
public class Select2ChoiceLOLsPage extends WebPage {

    final Select2Choice<List<ELOLsState>> listOfListsSelect;

    final Form<Void> form;

    public Select2ChoiceLOLsPage() {
        super();

        final List<List<ELOLsState>> states = new LinkedList<>();
        states.add(ELOLsState.LIST_1);
        states.add(ELOLsState.LIST_2);
        states.add(ELOLsState.LIST_3);

        List<ELOLsState> selectedStates = ELOLsState.LIST_1;

        IModel<List<ELOLsState>> selectedStatesModel = Model.ofList(selectedStates);
        listOfListsSelect = new Select2Choice<>("selectState", selectedStatesModel, new LOLsChoiceProvider(states));

        form = new Form<>("form");
        form.add(listOfListsSelect);
        add(this.form);
    }
}
