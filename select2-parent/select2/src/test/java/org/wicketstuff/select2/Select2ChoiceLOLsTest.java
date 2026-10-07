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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author cdjost
 * Test for edge case documented in https://github.com/wicketstuff/core/issues/685
 */
class Select2ChoiceLOLsTestextends extends AbstarctSelect2Test {
    @Test
    void testSelect2ChoiceLOLsInitialValue()
    {
        Select2ChoiceLOLsPage page = new Select2ChoiceLOLsPage();
        tester.startPage(page);
        tester.assertRenderedPage(Select2ChoiceLOLsPage.class);

        assertTrue(page.listOfListsSelect.getModelObject().equals(ELOLsState.LIST_1));
    }

    @Test
    public void testSelect2ChoiceLOLsChangeValue()
    {
        Select2ChoiceLOLsPage page = new Select2ChoiceLOLsPage();
        tester.startPage(page);
        tester.assertRenderedPage(Select2ChoiceLOLsPage.class);
        assertTrue(page.listOfListsSelect.getModelObject().equals(ELOLsState.LIST_1));

        tester.getRequest().getPostParameters().setParameterValue("selectState", "2");
        tester.submitForm(page.form);
        tester.assertRenderedPage(Select2ChoiceLOLsPage.class);
        assertTrue(page.listOfListsSelect.getModelObject().equals(ELOLsState.LIST_3));
    }
}
