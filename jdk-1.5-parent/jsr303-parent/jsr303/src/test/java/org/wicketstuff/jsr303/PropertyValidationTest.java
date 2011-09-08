package org.wicketstuff.jsr303;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests PropertyValidation.
 * 
 * @author akiraly
 */
public class PropertyValidationTest
{
	private final WicketTester tester;

	public PropertyValidationTest()
	{
		tester = new WicketTester();
	}

	@After
	public void tearDown()
	{
		tester.destroy();
	}

	@Test
	public void testValidation()
	{
		String formId = "form";
		tester.startPage(FormTestPage.class);
		tester.assertEnabled(formId);
		tester.assertVisible(formId);

		FormTester formTester = tester.newFormTester(formId);
		Assert.assertFalse("Form should not have errors on initial rendering.",
			formTester.getForm().hasError());

		formTester = tester.newFormTester(formId);
		formTester.submit();
		Assert.assertTrue("Form should have validation errors because no value was submitted.",
			formTester.getForm().hasError());

		formTester = tester.newFormTester(formId);
		formTester.setValue("email", "my@email.org");
		formTester.submit();
		Assert.assertFalse("Form should not have errors because value was submitted.",
			formTester.getForm().hasError());
	}
}
