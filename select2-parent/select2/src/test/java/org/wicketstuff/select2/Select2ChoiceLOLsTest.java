package org.wicketstuff.select2;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author cdjost
 * Test for edge case documented in https://github.com/wicketstuff/core/issues/685
 */
public class Select2ChoiceLOLsTest {

    private WicketTester wicketTester;

    @BeforeEach
    public void setUp()
    {
        wicketTester = new WicketTester();
    }

    @Test
    public void testSelect2ChoiceLOLsInitialValue()
    {
        Select2ChoiceLOLsPage page = new Select2ChoiceLOLsPage();
        wicketTester.startPage(page);
        wicketTester.assertRenderedPage(Select2ChoiceLOLsPage.class);

        assertTrue(page.listOfListsSelect.getModelObject().equals(ELOLsState.LIST_1));
    }

    @Test
    public void testSelect2ChoiceLOLsChangeValue()
    {
        Select2ChoiceLOLsPage page = new Select2ChoiceLOLsPage();
        wicketTester.startPage(page);
        wicketTester.assertRenderedPage(Select2ChoiceLOLsPage.class);
        assertTrue(page.listOfListsSelect.getModelObject().equals(ELOLsState.LIST_1));

        wicketTester.getRequest().getPostParameters().setParameterValue("selectState", "2");
        wicketTester.submitForm(page.form);
        wicketTester.assertRenderedPage(Select2ChoiceLOLsPage.class);
        assertTrue(page.listOfListsSelect.getModelObject().equals(ELOLsState.LIST_3));
    }
}
