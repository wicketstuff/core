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
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.console.clojure.ClojureScriptEnginePanel;
import org.wicketstuff.console.engine.Engines;
import org.wicketstuff.console.engine.IScriptEngine;
import org.wicketstuff.console.engine.IScriptExecutionResult;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.groovy.GroovyScriptEnginePanel;
import org.wicketstuff.console.jython.JythonScriptEnginePanel;
import org.wicketstuff.console.scala.ScalaScriptEnginePanel;
import org.wicketstuff.console.templates.IScriptTemplateStore;
import org.wicketstuff.console.templates.LangLabel;
import org.wicketstuff.console.templates.ScriptTemplate;

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

	private static final long serialVersionUID = 1L;

	private static final ResourceReference CSS = new PackageResourceReference(
		ScriptEnginePanel.class, "ScriptEnginePanel.css");

	private static final ResourceReference JS = new PackageResourceReference(
		ScriptEnginePanel.class, "ScriptEnginePanel.js");

	private String input;
	private String output;
	private String returnValue;
	private boolean success;

	private Label titleLabel;
	private LangLabel titleLangLabel;
	private Form<Void> form;
	private TextArea<String> inputTa;
	private OutputTextArea outputTa;
	private TextField<String> returnValueTf;

	private Image indicator;
	private final Lang lang;
	private final IScriptTemplateStore store;

	private RepeatingView controls;

	public ScriptEnginePanel(final String id, final Lang lang)
	{
		this(id, lang, null);
	}


	public ScriptEnginePanel(final String id, final Lang lang, final IScriptTemplateStore store)
	{
		super(id);
		this.lang = lang;
		this.store = store;

		init();
	}

	private void init()
	{
		setDefaultModel(new CompoundPropertyModel<ScriptEnginePanel>(this));
		initComponents();
	}

	protected void initComponents()
	{

		titleLabel = new Label("title", Model.of("Wicket Console"));
		add(titleLabel);

		titleLangLabel = new LangLabel("title-lang", Model.of(lang));
		add(titleLangLabel);

		form = new Form<Void>("form");
		add(form);

		inputTa = new TextArea<String>("input");
		inputTa.setOutputMarkupId(true);
		form.add(inputTa);

		controls = new RepeatingView("controls");
		form.add(controls);

		addControls(controls);

		indicator = new Image("indicator", AbstractDefaultAjaxBehavior.INDICATOR);
		indicator.setOutputMarkupId(true);
		form.add(indicator);

		returnValueTf = new TextField<String>("returnValue");
		returnValueTf.setOutputMarkupId(true);
		add(returnValueTf);

		outputTa = new OutputTextArea("output", this);
		outputTa.setOutputMarkupId(true);
		add(outputTa);
	}


	protected void addControls(final RepeatingView controls)
	{
		final Fragment submitfrag = new Fragment(controls.newChildId(), "submitFragment", this);
		submitfrag.add(new SubmitButton("submit", this));
		controls.add(submitfrag);

		final Fragment clearfrag = new Fragment(controls.newChildId(), "clearFragment", this);
		clearfrag.add(new ClearButton("clear", this));
		controls.add(clearfrag);

		controls.add(new StorePanel(controls.newChildId(), this));
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
			response.render(CssHeaderItem.forReference(css));
		}

		response.render(JavaScriptHeaderItem.forReference(JS));
	}

	public void process(final AjaxRequestTarget target)
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

	public void setTitle(final IModel<String> title)
	{
		titleLabel.setDefaultModel(title);
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

	Image getIndicator()
	{
		return indicator;
	}


	public Form<Void> getForm()
	{
		return form;
	}

	public IScriptTemplateStore getStore()
	{
		return store;
	}

	public String getAjaxIndicatorMarkupId()
	{
		return indicator.getMarkupId();
	}

	public static ScriptEnginePanel create(final String wicketId, final Lang lang)
	{
		return create(wicketId, lang, null);
	}

	public static ScriptEnginePanel create(final String wicketId, final Lang lang,
		final IScriptTemplateStore store)
	{
		ScriptEnginePanel enginePanel;
		switch (lang)
		{
			case GROOVY :
				enginePanel = new GroovyScriptEnginePanel(wicketId, store);
				break;
			case CLOJURE :
				enginePanel = new ClojureScriptEnginePanel(wicketId, store);
				break;
			case SCALA :
				enginePanel = new ScalaScriptEnginePanel(wicketId, store);
				break;
			case JYTHON :
				enginePanel = new JythonScriptEnginePanel(wicketId, store);
				break;
			default :
				throw new UnsupportedOperationException("Unsupported language: " + lang);
		}

		return enginePanel;
	}


	/**
	 * Stores the current script in the store.
	 * 
	 * @param target
	 * 
	 * @param scriptTitle
	 *            Title
	 */
	public void storeScriptTemplate(final AjaxRequestTarget target, final String scriptTitle)
	{
		if (store == null)
		{
			throw new UnsupportedOperationException("There is no store attached");
		}

		store.save(new ScriptTemplate(scriptTitle, getInput(), lang));
	}
}