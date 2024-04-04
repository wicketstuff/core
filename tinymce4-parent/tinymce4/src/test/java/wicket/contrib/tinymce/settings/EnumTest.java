package wicket.contrib.tinymce.settings;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
		assertEquals(enum1, enum1);
		assertEquals(enum1, enum2);
		assertEquals(enum2, enum1);
		assertFalse(enum1.equals(enum3));
		assertFalse(enum3.equals(enum1));
		assertFalse(enum1.equals(null));
		assertFalse(enum1.equals("enum1"));
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
		assertEquals(enum1.hashCode(), enum1.hashCode());
		assertEquals(enum1.hashCode(), enum2.hashCode());
		assertFalse(enum1.hashCode() == enum3.hashCode());
	}

	@Test
	public void testNotNull()
	{
		assertThrows(IllegalArgumentException.class, () -> {
			new Enum(null)
			{
				private static final long serialVersionUID = 1L;
			};
		});
	}
}
