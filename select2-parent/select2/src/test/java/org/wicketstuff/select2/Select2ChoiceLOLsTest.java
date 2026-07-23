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
