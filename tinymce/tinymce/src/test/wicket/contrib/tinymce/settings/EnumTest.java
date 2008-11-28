package wicket.contrib.tinymce.settings;

import junit.framework.TestCase;

import org.junit.Test;

public class EnumTest {
    @Test
    public void testEquals() {
        Enum enum1 = new Enum("enum1") {};
        Enum enum2 = new Enum("enum1") {};
        Enum enum3 = new Enum("enum3") {};
        TestCase.assertEquals(enum1, enum1);
        TestCase.assertEquals(enum1, enum2);
        TestCase.assertEquals(enum2, enum1);
        TestCase.assertFalse(enum1.equals(enum3));
        TestCase.assertFalse(enum3.equals(enum1));
        TestCase.assertFalse(enum1.equals(null));
        TestCase.assertFalse(enum1.equals("enum1"));
    }
    
    @Test
    public void testHashCode() {
        Enum enum1 = new Enum("enum1") {};
        Enum enum2 = new Enum("enum1") {};
        Enum enum3 = new Enum("enum3") {};
        TestCase.assertEquals(enum1.hashCode(), enum1.hashCode());
        TestCase.assertEquals(enum1.hashCode(), enum2.hashCode());
        TestCase.assertFalse(enum1.hashCode() == enum3.hashCode());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNotNull() {
        new Enum(null) {};
    }
}
