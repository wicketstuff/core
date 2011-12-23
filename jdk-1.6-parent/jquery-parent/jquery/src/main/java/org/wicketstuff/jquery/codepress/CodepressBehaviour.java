package org.wicketstuff.jquery.codepress;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CodepressBehaviour extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;

	public static final ResourceReference CODEPRESS_JS = new PackageResourceReference(
		CodepressBehaviour.class, "jquery.codepress.js");

	private final CodepressOptions options_;

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
		response.render(JavaScriptHeaderItem.forReference(CODEPRESS_JS));
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

		getComponent().add(AttributeModifier.append("class", classes.toString()));
	}

}
