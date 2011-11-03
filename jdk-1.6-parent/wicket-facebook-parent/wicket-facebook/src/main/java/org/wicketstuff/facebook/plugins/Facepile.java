package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/facepile/
 * 
 * @author Till Freier
 * 
 */
public class Facepile extends AbstractFacebookPlugin
{
	public enum Size
	{
		LARGE, SMALL
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int numRows = 1;
	private Size size = Size.SMALL;

	public Facepile(final String id, final IModel<?> url)
	{
		super(id, "fb-facepile");

		add(new AttributeModifier("data-href", url));

		add(new AttributeModifier("data-num-rows", new PropertyModel<Integer>(this, "numRows")));
		add(new AttributeModifier("data-size", new EnumModel(new PropertyModel<ColorScheme>(this,
			"size"))));
	}

	public int getNumRows()
	{
		return numRows;
	}

	public Size getSize()
	{
		return size;
	}

	public void setNumRows(int numRows)
	{
		this.numRows = numRows;
	}

	public void setSize(Size size)
	{
		this.size = size;
	}


}
