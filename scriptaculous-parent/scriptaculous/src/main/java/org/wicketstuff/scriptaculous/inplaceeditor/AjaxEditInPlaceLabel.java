package org.wicketstuff.scriptaculous.inplaceeditor;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.target.basic.StringRequestTarget;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;
import org.wicketstuff.scriptaculous.JavascriptBuilder.AjaxCallbackJavascriptFunction;
import org.wicketstuff.scriptaculous.effect.Effect;

/**
 * Label that uses AJAX for editing "in place" instead of using conventional forms.
 * There are several extension points within this class to allow for subclasses
 * to customize the behavior of this component.
 *
 * @see http://wiki.script.aculo.us/scriptaculous/show/Ajax.InPlaceEditor
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class AjaxEditInPlaceLabel<T> extends AbstractTextComponent<T>
{
	private static final long serialVersionUID = 1L;
	private AbstractAjaxBehavior callbackBehavior = new AjaxEditInPlaceOnSaveBehavior();
	private AbstractAjaxBehavior onCompleteBehavior = new AjaxEditInPlaceOnCompleteBehavior();
	private Map<String, Object> options = new HashMap<String, Object>();
	private boolean enterEditMode = false;
	private AbstractAjaxBehavior loadBehavior;

	public AjaxEditInPlaceLabel(String wicketId, IModel<T> model)
	{
		super(wicketId);
		setModel(model);
		setOutputMarkupId(true);
        setEscapeModelStrings(false);

		add(callbackBehavior);
		add(onCompleteBehavior);
	}

	public String getInputName()
	{
		return "value";
	}

	/**
	 * configure use of okButton for InPlaceEditor.
	 *
	 * @param value
	 */
	public void setOkButton(boolean value)
	{
		addOption("okButton", Boolean.valueOf(value));
	}

	public void setCancelLink(boolean value)
	{
		addOption("cancelLink", Boolean.valueOf(value));
	}

	public void setExternalControl(WebMarkupContainer control)
	{
		addOption("externalControl", control.getMarkupId());
	}

	public void setSubmitOnBlur(boolean value)
	{
		addOption("submitOnBlur", Boolean.valueOf(value));
	}

	public void setRows(int rows)
	{
		addOption("rows", new Integer(rows));
	}

	public void setCols(int cols)
	{
		addOption("cols", new Integer(cols));
	}

	public void setSize(int size)
	{
		addOption("size", new Integer(size));
	}

	/**
	 * extension point for customizing what text is loaded for editing.
	 * Usually used in conjunction with <code>getDisplayValue()</code> to override
	 * what text is displayed in text area versus the label.
	 * <pre>
	 * setLoadBehavior(new AbstractAjaxBehavior() {
	 *   public void onRequest() {
     *     RequestCycle.get().setRequestTarget(new StringRequestTarget(getValue()));
     *   }
     * });
     * </pre>
	 * @see #getDisplayValue()
	 */
	public void setLoadBehavior(AbstractAjaxBehavior loadBehavior)
	{
		this.loadBehavior = loadBehavior;
		add(loadBehavior);
	}

	/**
	 * extension point to allow for manipulation of what value is displayed.
	 * Overriding this method allows for the component to display different text
	 * than what is edited. This may be useful when the display text is
	 * formatted differently than the editable text (ex: textile). The default
	 * behavior is to return the same value as the <code>getValue()</code>
	 * method.
	 *
	 * @see #getValue();
	 * @see #setLoadBehavior(AbstractAjaxBehavior)
	 * @see AjaxEditInPlaceOnSaveBehavior#onRequest()
	 */
	protected String getDisplayValue()
	{
		return getValue();
	}

	/**
	 * Sets the label to be in <i>edit mode</i> the next time the page is rendered.
	 * Needs to be called again when refreshing the component.
	 */
	public void enterEditMode()
	{
		enterEditMode = true;
	}

	/**
	 * extension point to override default onComplete behavior.
	 * allows for customizing behavior once the edited value has been saved.
	 * default implementation is to do nothing.
	 * @see AjaxEditInPlaceOnCompleteBehavior#onRequest()
	 */
	protected void onComplete(final AjaxRequestTarget target)
	{
	}

	/**
	 * protected method to allow for subclasses to access adding additional options.
	 */
	protected final void addOption(String key, Object value)
	{
		options.put(key, value);
	}

	/**
	 * Handle the container's body.
	 *
	 * @param markupStream
	 *            The markup stream
	 * @param openTag
	 *            The open tag for the body
	 * @see wicket.Component#onComponentTagBody(MarkupStream, ComponentTag)
	 */
	protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		replaceComponentTagBody(markupStream, openTag, getDisplayValue());
	}

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		addOption("onComplete", new AjaxCallbackJavascriptFunction(onCompleteBehavior));

		if (null != loadBehavior) {
			addOption("loadTextURL", loadBehavior.getCallbackUrl());
		}

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Ajax.InPlaceEditor('" + getMarkupId() + "', ");
		builder.addLine("  '" + callbackBehavior.getCallbackUrl() + "', ");
		builder.addOptions(options);
		builder.addLine(")");
		if (enterEditMode)
		{
			builder.addLine(".enterEditMode()");
			enterEditMode = false;
		}
		builder.addLine(";");
		getResponse().write(builder.buildScriptTagString());
	}

	private class AjaxEditInPlaceOnCompleteBehavior extends ScriptaculousAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target) {
			target.appendJavascript(new Effect.Highlight(AjaxEditInPlaceLabel.this).toJavascript());

			onComplete(target);
		}
	}

	private class AjaxEditInPlaceOnSaveBehavior extends ScriptaculousAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target) {
			FormComponent formComponent = (FormComponent)getComponent();
			formComponent.validate();
			if (formComponent.isValid())
			{
				formComponent.updateModel();
			}
			RequestCycle.get().setRequestTarget(new StringRequestTarget(getDisplayValue()));
		}
	}
}
