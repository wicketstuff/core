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

import java.util.Collection;
import java.util.List;

/**
 * @author cdjost
 */
public class LOLsChoiceProvider extends ChoiceProvider<List<ELOLsState>> {

    private final List<List<ELOLsState>> selectableStates;

    public LOLsChoiceProvider(List<List<ELOLsState>> selectableStates) {
        this.selectableStates = selectableStates;
    }

    @Override
    public String getDisplayValue(List<ELOLsState> listOfStates) {
        if (listOfStates != null) {
            if (listOfStates.equals(ELOLsState.LIST_1)) {
                return "LIST_1+";
            }

            if (listOfStates.equals(ELOLsState.LIST_2)) {
                return "LIST_2+";
            }

            if (listOfStates.equals(ELOLsState.LIST_3)) {
                return "LIST_3+";
            }
        }
        return "Something is wrong";
    }

    @Override
    public String getIdValue(List<ELOLsState> listOfStates) {
        return String.valueOf(selectableStates.indexOf(listOfStates));
    }

    @Override
    public void query(String term, int page, Response<List<ELOLsState>> response) {
        // Could be implemented
        response.addAll(selectableStates);
    }

    @Override
    public Collection<List<ELOLsState>> toChoices(Collection<String> collection) {
        final String id = collection.stream().findFirst().orElseThrow();
        final int index = Integer.parseInt(id);
        return List.of(selectableStates.get(index));
    }
}