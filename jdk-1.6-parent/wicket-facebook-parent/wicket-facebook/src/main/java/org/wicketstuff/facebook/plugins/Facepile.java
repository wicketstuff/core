package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

	private int maxRows = 1;
	private Size size = Size.SMALL;

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param urlModel
	 *            model for the URL of the page. The plugin will display photos of users who have
	 *            liked this page.
	 */
	public Facepile(final String id, final IModel<?> urlModel)
	{
		super(id, "fb-facepile");

		add(new AttributeModifier("data-href", urlModel));

		add(new AttributeModifier("data-max-rows", new PropertyModel<Integer>(this, "maxRows")));
		add(new AttributeModifier("data-size", new EnumModel(new PropertyModel<ColorScheme>(this,
			"size"))));
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            the URL of the page. The plugin will display photos of users who have liked this
	 *            page.
	 */
	public Facepile(final String id, final String url)
	{
		this(id, Model.of(url));
	}

	/**
	 * @see #setMaxRows(int)
	 * @return
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * @see #setSize(Size)
	 * @return
	 */
	public Size getSize()
	{
		return size;
	}

	/**
	 * the maximum number of rows of faces to display. The XFBML version will dynamically size its
	 * height; if you specify a maximum of four rows of faces, but there are only enough friends to
	 * fill two rows, the XFBML version of the plugin will set its height for two rows of faces.
	 * Default: 1.
	 * 
	 * @param numRows
	 *            the number of rows
	 */
	public void setMaxRows(final int numRows)
	{
		maxRows = numRows;
	}

	/**
	 * @param size
	 *            size of the photos and social context. Default size: small.
	 */
	public void setSize(final Size size)
	{
		this.size = size;
	}


}
