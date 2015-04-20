package org.wicketstuff.select2.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author lexx
 */
public class ValueSplitterTest
{

	@Test
	public void testSplitWithSingleValue() throws Exception
	{
		String[] strings = ValueSplitter.split("A");
		assertEquals(1, strings.length);
		assertEquals("A", strings[0]);
	}

	@Test
	public void testSplitWithRegularCSV() throws Exception
	{
		String[] strings = ValueSplitter.split("A,B,C");
		assertEquals(3, strings.length);
	}

	@Test
	public void testSplitWithSingleJsonId() throws Exception
	{
		String jsonId = "{\"someKey\":\"someValue\"}";
		String[] strings = ValueSplitter.split(jsonId);
		assertEquals(1, strings.length);
		assertEquals(jsonId, strings[0]);
	}

	@Test
	public void testSplitWithSingleJsonIdThatHasNestedObjects() throws Exception
	{
		String jsonId = "{\"email\":{\"emailAddress\":\"test@test.com\"},\"isContact\":true}";
		String[] strings = ValueSplitter.split(jsonId);
		assertEquals(1, strings.length);
		assertEquals(jsonId, strings[0]);
	}

	@Test
	public void testSplitWithMultipleJsonIds() throws Exception
	{
		String jsonId = "{\"someKey\":\"someValue\"},{\"someKey\":\"otherValue\"}";
		String[] strings = ValueSplitter.split(jsonId);
		assertEquals(2, strings.length);
	}

	@Test
	public void testSplitWithMultipleJsonIdsAndNestedJsonObjects() throws Exception
	{
		String[] strings = ValueSplitter.split("{\"email\":{\"emailAddress\":\"nouser@test.com\"},\"isContact\":true},{\"email\":{\"emailAddress\":\"otheruser@test.com\"},\"isContact\":contact}");
		assertEquals(2, strings.length);
	}
}