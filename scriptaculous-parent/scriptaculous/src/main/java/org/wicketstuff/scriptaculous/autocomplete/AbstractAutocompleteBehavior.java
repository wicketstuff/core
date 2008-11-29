package org.wicketstuff.scriptaculous.autocomplete;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.model.Model;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * support class for all autocomplete behaviors.
 * This class is responsible for:
 * <ul>
 *   <li>binding required javascript to lookup/display autocomplete results.</li>
 *   <li>binding css to render the autocomplete results</li>
 * </ul>
 * Subclasses of this behavior are responsible for implementing the way in which
 * results are looked up.  This can be a static list of results, or an ajax callback.
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 * @see http://wiki.script.aculo.us/scriptaculous/show/Autocompletion
 */
public abstract class AbstractAutocompleteBehavior extends ScriptaculousAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private Map<String, Object> options = new HashMap<String, Object>();
	
	protected void onBind() {
		super.onBind();

		getComponent().setOutputMarkupId(true);
		getComponent().add(new AttributeModifier("autocomplete", new Model<String>("off")));
		getComponent().add(HeaderContributor.forCss(getCss(), "screen"));
	}

	/**
	 * render a placeholder div for the autocomplete results.
	 */
	protected void onComponentRendered() {
		super.onComponentRendered();

		Response response = RequestCycle.get().getResponse();
		response.write(
				"<div class=\"auto_complete\" id=\"" + getAutocompleteId() + "\"></div>");

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new " + getAutocompleteType() + "(");
		builder.addLine("  '" + getComponent().getMarkupId() + "', ");
		builder.addLine("  '" + getAutocompleteId() + "', ");
		builder.addLine("  " + getThirdAutocompleteArgument() + ", ");
		builder.addOptions(options);
		builder.addLine(");");

		response.write(builder.buildScriptTagString());
	}

	private final String getAutocompleteId()
	{
		return getComponent().getMarkupId() + "_autocomplete";
	}

	/**
	 * extension point to customize what css is used to style the component.
	 * @return
	 */
	protected ResourceReference getCss()
	{
		return new ResourceReference(AbstractAutocompleteBehavior.class, "style.css");
	}

	/**
	 * subclasses need to declare the scriptaculous autocomplete type.
	 * @return
	 */
	protected abstract String getAutocompleteType();

	/**
	 * Subclasses need to define this optional argument.
	 * each implementation requires a different value.
	 * @return
	 */
	protected abstract String getThirdAutocompleteArgument();

	protected void addOption(String key, Object value) {
		options.put(key, value);
	}
}
