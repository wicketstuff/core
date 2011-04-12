package org.wicketstuff.jquery.codepress;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.minis.behavior.SimpleAttributeAppender;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CodepressBehaviour extends JQueryBehavior
{
	public static final ResourceReference CODEPRESS_JS = new PackageResourceReference(
		CodepressBehaviour.class, "jquery.codepress.js");

	private CodepressOptions options_;

	public CodepressBehaviour()
	{
		this(new CodepressOptions());
	}

	public CodepressBehaviour(CodepressOptions options)
	{
		super();
		options_ = options != null ? options : new CodepressOptions();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.renderJavaScriptReference(CODEPRESS_JS);
	}

	@Override
	protected CharSequence getOnReadyScript()
	{
		StringBuilder onReady = new StringBuilder("$.codepress.config = ");
		onReady.append(options_.toString(false));
		onReady.append("$('" + getComponent().getMarkupId() + "').codepress();");

		return onReady;
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		StringBuilder classes = new StringBuilder("codepress");
		if (options_.getFileType() != null)
			classes.append(" " + options_.getFileType());

		classes.append(" autocomplete-" + (options_.isAutoComplete() ? "on" : "off"));

		if (!options_.isLineNumbers())
			classes.append(" linenumbers-off");

		getComponent().add(new SimpleAttributeAppender("class", classes.toString(), " "));
	}

}
