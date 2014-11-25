package org.wicketstuff.select2;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class Select2MultiChoiceTest extends Assert {
    @Test
    public void testSplitWithSingleValue() throws Exception
    {
        List<String> strings = Select2MultiChoice.splitInput("A");
        assertEquals(1, strings.size());
        assertEquals("A", strings.get(0));
    }

    @Test
    public void testSplitWithRegularCSV() throws Exception
    {
        List<String> strings = Select2MultiChoice.splitInput("A,B,C");
        assertEquals(3, strings.size());
    }

    @Test
    public void testSplitWithSingleJsonId() throws Exception
    {
        String jsonId = "{\"someKey\":\"someValue\"}";
        List<String> strings = Select2MultiChoice.splitInput(jsonId);
        assertEquals(1, strings.size());
        assertEquals(jsonId, strings.get(0));
    }

    @Test
    public void testSplitWithSingleJsonIdThatHasNestedObjects() throws Exception
    {
        String jsonId = "{\"email\":{\"emailAddress\":\"test@test.com\"},\"isContact\":true}";
        List<String> strings = Select2MultiChoice.splitInput(jsonId);
        assertEquals(1, strings.size());
        assertEquals(jsonId, strings.get(0));
    }

    @Test
    public void testSplitWithMultipleJsonIds() throws Exception
    {
        String jsonId = "{\"someKey\":\"someValue\"},{\"someKey\":\"otherValue\"}";
        List<String> strings = Select2MultiChoice.splitInput(jsonId);
        assertEquals(2, strings.size());
    }

    @Test
    public void testSplitWithMultipleJsonIdsAndNestedJsonObjects() throws Exception
    {
        List<String> strings = Select2MultiChoice.splitInput(
            "{\"email\":{\"emailAddress\":\"nouser@test.com\"},\"isContact\":true},{\"email\":{\"emailAddress\":\"otheruser@test.com\"},\"isContact\":contact}" );
        assertEquals(2, strings.size());
    }
}
