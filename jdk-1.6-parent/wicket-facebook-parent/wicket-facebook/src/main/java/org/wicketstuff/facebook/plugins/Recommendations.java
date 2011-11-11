package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/recommendations/
 * 
 * @author Till Freier
 * 
 */
public class Recommendations extends AbstractFacebookPlugin
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private LinkTarget linkTarget = LinkTarget._BLANK;

	private boolean showHeader = false;

	/**
	 * 
	 * @param id
	 *            wicket-id
	 */
	public Recommendations(final String id)
	{
		super(id, "fb-recommendations");

		add(new AttributeModifier("data-header", new PropertyModel<Boolean>(this, "showHeader")));
		add(new AttributeModifier("data-linktarget", new EnumModel(new PropertyModel<LinkTarget>(
			this, "linkTarget"))));
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param site
	 *            a comma separated list of domains to show recommendations for. The XFBML version
	 *            defaults to the current domain
	 */
	public Recommendations(final String id, final IModel<?> site)
	{
		this(id);

		add(new AttributeModifier("data-site", site));
	}

	/**
	 * @see #setLinkTarget(LinkTarget)
	 * @return
	 */
	public LinkTarget getLinkTarget()
	{
		return linkTarget;
	}

	/**
	 * @see #setShowHeader(boolean)
	 * @return whether to show the Facebook header
	 */
	public boolean isShowHeader()
	{
		return showHeader;
	}

	/**
	 * This specifies the context in which content links are opened. By default all links within the
	 * plugin will open a new window. If you want the content links to open in the same window, you
	 * can set this parameter to <code>_top</code> or <code>_parent</code>. Links to Facebook URLs
	 * will always open in a new window
	 * 
	 * @param linkTarget
	 *            Options: {@link LinkTarget}
	 */
	public void setLinkTarget(final LinkTarget linkTarget)
	{
		this.linkTarget = linkTarget;
	}

	/**
	 * specifies whether to show the Facebook header
	 * 
	 * @param showHeader
	 */
	public void setShowHeader(final boolean showHeader)
	{
		this.showHeader = showHeader;
	}


}
