package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder;

/**
 * report column
 * @author mosmann
 *
 */
public class Column
{
	final IAttributes attributes;

	final String name;

	/**
	 * column with id and some attributes
	 * @param name
	 * @param attributes
	 */
	public Column(String name, IAttributes attributes)
	{
		this.name = name;
		this.attributes = attributes;
	}

	/**
	 * column with id and no custom attributes set
	 * @param name
	 */
	public Column(String name)
	{
		this(name, new AttributeBuilder().build());
	}

	public IAttributes attributes()
	{
		return attributes;
	}

	/**
	 * column alignment
	 * @author mosmann
	 *
	 */
	public static enum Align implements TypedAttribute<Align> {
		Left, Right;
	}

	static class IntegerType implements TypedAttribute<Integer>
	{

	}

	static class CharType implements TypedAttribute<Character>
	{

	}

	static class StringType implements TypedAttribute<String>
	{

	}

	static class AlignType implements TypedAttribute<Align>
	{

	}

	/**
	 * column filler before column content
	 */
	public static final TypedAttribute<Character> FillBefore = new CharType();
	/**
	 * column filler after column content
	 */
	public static final TypedAttribute<Character> FillAfter = new CharType();
	/**
	 * column separator
	 */
	public static final TypedAttribute<String> Separator = new StringType();
	/**
	 * column indent
	 */
	public static final TypedAttribute<String> Indent = new StringType();
}