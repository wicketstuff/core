package org.wicketstuff.twitter.behavior;

import org.apache.wicket.model.IModel;
import org.apache.wicket.core.util.string.JavaScriptUtils;

/**
 * 
 * @author Till Freier
 * 
 */
public class TweetBoxBehavior extends AbstractAnywhereBehavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int height;
	private final IModel<?> text;

	private final int width;

	/**
	 * 
	 * @param apiKey
	 * @param text
	 * @param width
	 * @param height
	 */
	public TweetBoxBehavior(String apiKey, IModel<?> text, int width, int height)
	{
		super(apiKey);
		this.width = width;
		this.height = height;
		this.text = text;
	}

	@Override
	protected String getAnywhereMethod()
	{
		StringBuilder js = new StringBuilder();
		js.append("tweetBox({ ");
		js.append("height: ").append(height).append(", ");
		js.append("width: ").append(width).append(", ");
		js.append("defaultContent: '").append(getEscapedModelObject()).append("' })");

		return js.toString();
	}

	private CharSequence getEscapedModelObject()
	{
		final Object object = text.getObject();
		if (object == null)
			return null;

		return JavaScriptUtils.escapeQuotes(object.toString());
	}

}
