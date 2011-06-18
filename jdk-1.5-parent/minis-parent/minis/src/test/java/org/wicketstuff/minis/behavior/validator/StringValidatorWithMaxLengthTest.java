package org.wicketstuff.minis.behavior.validator;

import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.validation.validator.StringValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link StringValidatorWithMaxLength}.
 * 
 * @author akiraly
 */
public class StringValidatorWithMaxLengthTest
{
	private final WicketTester tester = new WicketTester();
	private final int maxLength = 50;

	@After
	public void after()
	{
		tester.destroy();
	}

	@Test
	public void testExactLength()
	{
		testValidator(StringValidatorWithMaxLength.exactLength(maxLength));
	}

	@Test
	public void testLengthBetween()
	{
		testValidator(StringValidatorWithMaxLength.lengthBetween(maxLength - 1, maxLength));
	}

	@Test
	public void testMaximumLength()
	{
		testValidator(StringValidatorWithMaxLength.maximumLength(maxLength));
	}

	private void testValidator(StringValidator validator)
	{
		TextField<String> field = new TextField<String>("foo");
		field.add(validator);

		IMarkupFragment markup = Markup.of("<form><input type=\"text\" wicket:id=\"" +
			field.getId() + "\" /></form>");

		tester.startComponentInPage(field, markup);

		TagTester tagTester = tester.getTagByWicketId(field.getId());

		Assert.assertNotNull(tagTester);
		Assert.assertEquals(Integer.toString(maxLength),
			tagTester.getAttribute(StringValidatorWithMaxLength.MAX_LENGTH));
	}
}
