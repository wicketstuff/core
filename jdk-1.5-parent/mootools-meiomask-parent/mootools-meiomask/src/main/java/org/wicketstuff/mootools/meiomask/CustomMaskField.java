package org.wicketstuff.mootools.meiomask;

import org.apache.wicket.model.IModel;

public class CustomMaskField<T> extends MeioMaskField<T>
{

	private static final long serialVersionUID = -5943859274575812088L;

	public CustomMaskField(String id, String mask)
	{
		this(id, mask, false);
	}


	public CustomMaskField(String id, String mask, boolean valueContainsLiteralCharacters)
	{
		this(id, mask, null, null, valueContainsLiteralCharacters, null);
	}

	public CustomMaskField(String id, String mask, IModel<T> model)
	{
		this(id, mask, null, model, false, null);
	}

	public CustomMaskField(String id, String mask, String options)
	{
		this(id, mask, options, null, false, null);
	}

	public CustomMaskField(String id, String mask, String options, IModel<T> model)
	{
		this(id, mask, options, model, false, null);
	}

	public CustomMaskField(String id, String mask, String options, IModel<T> model,
		boolean valueContainsLiteralCharacters)
	{
		this(id, mask, options, model, valueContainsLiteralCharacters, null);
	}

	public CustomMaskField(String id, String mask, String options, IModel<T> model,
		boolean valueContainsLiteralCharacters, Class<T> type)
	{
		super(id, MaskType.Custom, options, model, valueContainsLiteralCharacters, type, mask);
	}


}