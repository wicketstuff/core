package org.wicketstuff.twitter.intents;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;

/**
 * 
 * @author Till Freier
 * 
 */
public abstract class AbstractIntentLink extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;
	private IModel<String> intentUrl;

	public AbstractIntentLink(final String id, final String url)
	{
		super(id);

		intentUrl = Model.of(url);

		add(new AttributeModifier("href", new IModel<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return getUrl();
			}
		}));
	}


	protected abstract PageParameters getParameters();

	public String getUrl()
	{
		final PageParametersEncoder encoder = new PageParametersEncoder();
		final Url url = encoder.encodePageParameters(getParameters());

		final StringBuilder sb = new StringBuilder();
		sb.append(intentUrl.getObject());
		sb.append(url.toString());

		return sb.toString();
	}
}
