package org.wicketstuff.yui.markup.html.dialog;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

public abstract class Dialog extends Panel implements IHeaderContributor
{
	private static final long serialVersionUID = 1L;

	/** Ref to CSS file */
	private static final ResourceReference CSS = new ResourceReference(Dialog.class,
			"css/Dialog.css");


	private DialogSettings settings;

	/**
	 * The JavaScript variable name of the slider component.
	 */
	private String javaScriptId;

	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(Dialog.class);

	public Dialog(String id, IModel model, DialogSettings settings)
	{
		super(id, model);
		setOutputMarkupId(true);

		add(new AttributeAppender("style", true, new Model("display:none"), ";"));

		this.settings = settings;

		add(YuiHeaderContributor.forModule("event"));
		add(YuiHeaderContributor.forModule("animation"));
		add(YuiHeaderContributor.forModule("dragdrop"));
		add(YuiHeaderContributor.forModule("container"));
		add(CSSPackageResource.getHeaderContribution(getCSS()));

		IModel variablesModel = new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			/** cached variables; we only need to fill this once. */
			private Map<String, Object> variables;

			/**
			 * @see wicket.model.AbstractReadOnlyModel#getObject(wicket.Component)
			 */
			@Override
			public Object getObject()
			{
				if (variables == null)
				{
					this.variables = new HashMap<String, Object>(8);
					variables.put("javaScriptId", javaScriptId);
					variables.put("domId", getMarkupId());
					String settings = "{}"; // default
					if (Dialog.this.settings != null)
					{
						settings = Dialog.this.settings.generateSettings();
					}
					variables.put("settings", settings);
				}
				return variables;
			}
		};

		add(TextTemplateHeaderContributor.forJavaScript(Dialog.class, "init.js", variablesModel));

		// head, content, footer
		Model labelModel = new Model("");
		if (getTitle() != null)
		{
			labelModel = getTitle();
		}
		add(new Label("title", labelModel));
		add(createContent("content"));
		add(createFooter("footer"));
	}

	/**
	 * return a model used to display the title Should be overwrite in order to
	 * set a title
	 * 
	 * @return Model to display the title
	 */
	public Model getTitle()
	{
		return null;
	}

	/**
	 * return a constructed panel containing the modal datas
	 * 
	 * @param panelId
	 *            id of the panel to return
	 * @return a constructed panel
	 */
	public abstract Panel createContent(String panelId);

	/**
	 * return a constructed panel containing the footer
	 * 
	 * @param panelId
	 *            id of the panel to Return
	 * @return a constructed panel
	 */
	public Panel createFooter(String panelId)
	{
		return new EmptyPanel(panelId);
	}

	public void renderHead(IHeaderResponse response)
	{
		response.renderOnLoadJavascript("init" + javaScriptId + "();");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onAttach()
	 */
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		String id = getMarkupId();
		javaScriptId = id + "Dialog";
	}

	public ResourceReference getCSS()
	{
		return CSS;
	}

	public void show(AjaxRequestTarget target)
	{
		target.appendJavascript(javaScriptId + ".show()");
	}

	public void hide(AjaxRequestTarget target)
	{
		target.appendJavascript(javaScriptId + ".hide()");
	}

}
