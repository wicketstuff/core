package wicket.contrib.tinymce.settings;


import org.junit.Assert;
import org.junit.Test;

import wicket.contrib.tinymce4.settings.Enum;

public class EnumTest
{
	@Test
	public void testEquals()
	{
		Enum enum1 = new Enum("enum1")
		{

			private static final long serialVersionUID = 1L;
		};
		Enum enum2 = new Enum("enum1")
		{

			private static final long serialVersionUID = 1L;
		};
		Enum enum3 = new Enum("enum3")
		{

			private static final long serialVersionUID = 1L;
		};
		Assert.assertEquals(enum1, enum1);
		Assert.assertEquals(enum1, enum2);
		Assert.assertEquals(enum2, enum1);
		Assert.assertFalse(enum1.equals(enum3));
		Assert.assertFalse(enum3.equals(enum1));
		Assert.assertFalse(enum1.equals(null));
		Assert.assertFalse(enum1.equals("enum1"));
	}

	@Test
	public void testHashCode()
	{
		Enum enum1 = new Enum("enum1")
		{

			private static final long serialVersionUID = 1L;
		};
		Enum enum2 = new Enum("enum1")
		{

			private static final long serialVersionUID = 1L;
		};
		Enum enum3 = new Enum("enum3")
		{

			private static final long serialVersionUID = 1L;
		};
		Assert.assertEquals(enum1.hashCode(), enum1.hashCode());
		Assert.assertEquals(enum1.hashCode(), enum2.hashCode());
		Assert.assertFalse(enum1.hashCode() == enum3.hashCode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotNull()
	{
		new Enum(null)
		{

			private static final long serialVersionUID = 1L;
		};
	}
}
