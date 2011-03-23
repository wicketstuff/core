package org.wicketstuff.console;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.console.engine.IScriptEngine;
import org.wicketstuff.console.engine.IScriptExecutionResult;


public abstract class AbstractScriptEnginePanel extends Panel {

	private final class ClearButton extends Button {
		private static final long serialVersionUID = 1L;

		private ClearButton(String id) {
			super(id);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new AttributeAppender("onclick", Model.of("clearTextarea('"
					+ inputTf.getMarkupId() + "')"), ";"));

		}

	}

	final class SubmitButton extends AjaxButton implements IAjaxIndicatorAware {
		private static final long serialVersionUID = 1L;

		private SubmitButton(String id, Form<?> form) {
			super(id, form);
		}

		@Override
		protected void onSubmit(final AjaxRequestTarget target,
				final Form<?> form) {
			process(target);
		}

		@Override
		protected void onError(final AjaxRequestTarget target,
				final Form<?> form) {

		}

		public String getAjaxIndicatorMarkupId() {
			return indicator.getMarkupId();
		}
	}

	private static final long serialVersionUID = 1L;

	private static final ResourceReference CSS = new CompressedResourceReference(
			AbstractScriptEnginePanel.class, "AbstractScriptEnginePanel.css");

	private static final ResourceReference JS = new CompressedResourceReference(
			AbstractScriptEnginePanel.class, "AbstractScriptEnginePanel.js");

	private String input;
	private String output;
	private String returnValue;

	private Form<Void> form;
	private TextArea<String> inputTf;
	private TextArea<String> outputTf;
	private TextField<String> returnValueTf;

	private Image indicator;

	public AbstractScriptEnginePanel(final String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<AbstractScriptEnginePanel>(
				this));
		initInput();
		initComponents();
	}

	protected void initComponents() {

		form = new Form<Void>("form");
		add(form);

		inputTf = new TextArea<String>("input");
		inputTf.setOutputMarkupId(true);
		form.add(inputTf);

		add(new SubmitButton("submit", form));
		add(new ClearButton("clear"));

		indicator = new Image("indicator",
				AbstractDefaultAjaxBehavior.INDICATOR);
		indicator.setOutputMarkupId(true);
		add(indicator);

		returnValueTf = new TextField<String>("returnValue");
		returnValueTf.setOutputMarkupId(true);
		add(returnValueTf);

		outputTf = new TextArea<String>("output");
		outputTf.setOutputMarkupId(true);
		add(outputTf);
	}

	protected ResourceReference getCSS() {
		return CSS;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		ResourceReference css = getCSS();
		if (css != null) {
			response.renderCSSReference(css);
		}

		response.renderJavaScriptReference(JS);
	}

	protected void process(final AjaxRequestTarget target) {

		final IScriptEngine engine = newEngine();
		final Map<String, Object> bindings = newBindings();

		final IScriptExecutionResult result = engine.execute(input, bindings);

		if (result.isSuccess()) {
			returnValue = String.valueOf(result.getReturnValue());
			output = result.getOutput();
		} else {
			returnValue = null;
			output = String.format("%s\n\n%s", result.getOutput(),
					result.getException());
		}

		target.add(returnValueTf, outputTf);
	}

	protected abstract void initInput();

	protected abstract IScriptEngine newEngine();

	protected Map<String, Object> newBindings() {
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("application", Application.get());
		bindings.put("page", getPage());
		bindings.put("component", this);
		return bindings;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

}