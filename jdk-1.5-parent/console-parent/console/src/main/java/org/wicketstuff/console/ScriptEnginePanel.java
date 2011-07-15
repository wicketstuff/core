/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.console;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.console.engine.Engines;
import org.wicketstuff.console.engine.IScriptEngine;
import org.wicketstuff.console.engine.IScriptExecutionResult;
import org.wicketstuff.console.engine.Lang;

/**
 * Abstract panel for executing Scripts.
 * <p>
 * Usage:
 * 
 * <pre>
 * add(new GroovyScriptEnginePanel(&quot;scriptPanel&quot;));
 * </pre>
 * 
 * <pre>
 * &lt;div wicket:id=&quot;scriptPanel&quot&gt;&lt;/div&gt;
 * </pre>
 * 
 * @see ClojureScriptEnginePanel
 * @see GroovyScriptEnginePanel
 * 
 * @author cretzel
 */
public class ScriptEnginePanel extends Panel
{

	private final class ClearButton extends Button
	{
		private static final long serialVersionUID = 1L;

		private ClearButton(final String id)
		{
			super(id);
		}

		@Override
		protected void onInitialize()
		{
			super.onInitialize();

			addClearOnClick(inputTa);
			addClearOnClick(outputTa);
			addClearOnClick(returnValueTf);
		}

		private void addClearOnClick(final Component component)
		{
			final String markupId = component.getMarkupId();
			final String clearJs = String.format("clearValue('%s')", markupId);
			add(new AttributeAppender("onclick", Model.of(clearJs), ";"));
		}

	}

	final class SubmitButton extends AjaxButton implements IAjaxIndicatorAware
	{
		private static final long serialVersionUID = 1L;

		private SubmitButton(final String id, final Form<?> form)
		{
			super(id, form);
		}

		@Override
		protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
		{
			process(target);
		}

		@Override
		protected void onError(final AjaxRequestTarget target, final Form<?> form)
		{
		}

		public String getAjaxIndicatorMarkupId()
		{
			return indicator.getMarkupId();
		}
	}

	private static final long serialVersionUID = 1L;

	private static final ResourceReference CSS = new PackageResourceReference(
		ScriptEnginePanel.class, "ScriptEnginePanel.css");

	private static final ResourceReference JS = new PackageResourceReference(
		ScriptEnginePanel.class, "ScriptEnginePanel.js");

	private String input;
	private String output;
	private String returnValue;
	private boolean success;

	private Label title;
	private Form<Void> form;
	private TextArea<String> inputTa;
	private OutputTextArea outputTa;
	private TextField<String> returnValueTf;

	private Image indicator;
	private final Lang lang;

	public ScriptEnginePanel(final String id, final Lang lang)
	{
		this(id, lang, null);
	}

	public ScriptEnginePanel(final String id, final Lang lang, final IModel<String> title)
	{
		super(id);
		this.lang = lang;
		final IModel<String> titleModel = title != null ? title : Model.of("Wicket Console");

		init(titleModel);
	}

	private void init(final IModel<String> titleModel)
	{
		setDefaultModel(new CompoundPropertyModel<ScriptEnginePanel>(this));
		initComponents(titleModel);
	}

	protected void initComponents(final IModel<String> titleModel)
	{
		title = new Label("title", titleModel);
		add(title);

		form = new Form<Void>("form");
		add(form);

		inputTa = new TextArea<String>("input");
		inputTa.setOutputMarkupId(true);
		form.add(inputTa);

		add(new SubmitButton("submit", form));
		add(new ClearButton("clear"));

		indicator = new Image("indicator", AbstractDefaultAjaxBehavior.INDICATOR);
		indicator.setOutputMarkupId(true);
		add(indicator);

		returnValueTf = new TextField<String>("returnValue");
		returnValueTf.setOutputMarkupId(true);
		add(returnValueTf);

		outputTa = new OutputTextArea("output", this);
		outputTa.setOutputMarkupId(true);
		add(outputTa);
	}

	protected ResourceReference getCSS()
	{
		return CSS;
	}

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		final ResourceReference css = getCSS();
		if (css != null)
		{
			response.renderCSSReference(css);
		}

		response.renderJavaScriptReference(JS);
	}

	protected void process(final AjaxRequestTarget target)
	{

		final IScriptEngine engine = newEngine();
		final Map<String, Object> bindings = newBindings();

		final IScriptExecutionResult result = engine.execute(input, bindings);

		if (result.isSuccess())
		{
			returnValue = String.valueOf(result.getReturnValue());
			output = result.getOutput();
			success = true;
		}
		else
		{
			returnValue = null;
			output = String.format("%s\n\n%s", result.getOutput(), result.getException());
			success = false;
		}

		target.add(returnValueTf, outputTa);
	}

	protected IScriptEngine newEngine()
	{
		return Engines.getSingletonInstance(lang);
	}

	protected Map<String, Object> newBindings()
	{
		final Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("application", Application.get());
		bindings.put("page", getPage());
		bindings.put("component", this);
		return bindings;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public String getInput()
	{
		return input;
	}

	public void setInput(final String input)
	{
		this.input = input;
	}

	public String getOutput()
	{
		return output;
	}

	public void setOutput(final String output)
	{
		this.output = output;
	}

	public String getReturnValue()
	{
		return returnValue;
	}

	public void setReturnValue(final String returnValue)
	{
		this.returnValue = returnValue;
	}

	public TextArea<String> getInputTf()
	{
		return inputTa;
	}

	public TextArea<String> getOutputTf()
	{
		return outputTa;
	}

	public TextField<String> getReturnValueTf()
	{
		return returnValueTf;
	}

	public static ScriptEnginePanel create(final String wicketId, final Lang lang,
		final IModel<String> title)
	{
		switch (lang)
		{
			case GROOVY :
				return new GroovyScriptEnginePanel(wicketId, title);
			case CLOJURE :
				return new ClojureScriptEnginePanel(wicketId, title);
			case SCALA :
				return new ScalaScriptEnginePanel(wicketId, title);
			case JYTHON :
				return new JythonScriptEnginePanel(wicketId, title);
			default :
				throw new UnsupportedOperationException("Unsupported language: " + lang);
		}
	}
}