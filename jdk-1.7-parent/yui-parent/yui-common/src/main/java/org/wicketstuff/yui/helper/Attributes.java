package org.wicketstuff.yui.helper;


/**
 * Attributes is a Javascript Object TODO : need to clean up
 * 
 * @author josh
 * 
 */
public class Attributes extends JSObject<Attributes>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct an Empty Attribute
	 */
	public Attributes()
	{
		super();
	}

	/**
	 * constructor. Adds a single value
	 * 
	 * @param element
	 * @param value
	 */
	public Attributes(String element, String value)
	{
		this.add(element, value);
	}

	/**
	 * 
	 * @param element
	 * @param value
	 */
	public Attributes(String element, int value)
	{
		this.add(element, Integer.toString(value));
	}

	/**
	 * 
	 * @param element
	 * @param value
	 */
	public Attributes(String element, float value)
	{
		this.add(element, Float.toString(value));
	}

	/**
	 * constructor for 2 elements
	 * 
	 * @param element1
	 * @param value1
	 * @param element2
	 * @param value2
	 */
	public Attributes(String element1, String value1, String element2, String value2)
	{
		this.add(element1, value1).add(element2, value2);
	}

	/**
	 * 
	 * @param element1
	 * @param int1
	 * @param element2
	 * @param int2
	 */
	public Attributes(String element1, int int1, String element2, int int2)
	{
		this(element1, int1);
		this.add(element2, int2);
	}

	/**
	 * 
	 * @param element1
	 * @param float1
	 * @param element2
	 * @param float2
	 */
	public Attributes(String element1, float float1, String element2, float float2)
	{
		this(element1, float1);
		this.add(element2, float2);
	}

	/**
	 * 
	 * @param element
	 * @param json
	 */
	public Attributes(String element, JavascriptObject json)
	{
		this.add(element, json.toString());
	}

	/**
	 * 
	 * @param element
	 * @param from
	 * @param to
	 */
	public Attributes(String element, int from, int to)
	{
		this.add(element, new Attributes("from", from, "to", to));
	}

	/**
	 * 
	 * @param element
	 * @param from
	 * @param to
	 */
	public Attributes(String element, float from, float to)
	{
		this.add(element, new Attributes("from", from, "to", to));
	}

	public Attributes(String element, boolean b)
	{
		this.add(element, b);
	}

	/**
	 * allow adding of int values
	 * 
	 * @param element
	 * @param int_value
	 */
	public Attributes add(String element, int int_value)
	{
		return super.add(element, Integer.toString(int_value));
	}

	/**
	 * allow adding of int values
	 * 
	 * @param element
	 * @param float_value
	 */
	public Attributes add(String element, float float_value)
	{
		return super.add(element, Float.toString(float_value));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		return "{}".equals(toString());
	}

	public static Attributes DELAY = new Attributes("delay", "true");
}
