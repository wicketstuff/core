package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.reportbuilder;


public class Column
{
	final IAttributes attributes;

	final String name;

	public Column(String name, IAttributes attributes)
	{
		this.name = name;
		this.attributes = attributes;
	}

	public Column(String name)
	{
		this(name, new AttributeBuilder().build());
	}

	public IAttributes attributes()
	{
		return attributes;
	}

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

	public static final TypedAttribute<Character> FillBefore = new CharType();
	public static final TypedAttribute<Character> FillAfter = new CharType();
	public static final TypedAttribute<String> Separator = new StringType();
	public static final TypedAttribute<String> Indent = new StringType();
}