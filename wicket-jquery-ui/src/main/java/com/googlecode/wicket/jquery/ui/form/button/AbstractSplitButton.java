package com.googlecode.wicket.jquery.ui.form.button;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.Menu;

/**
 * Base class for jQuery split-button
 *
 * @author Patrick Davids - Patrick1701
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AbstractSplitButton extends GenericPanel<List<IMenuItem>>
{
	private static final long serialVersionUID = 1L;
	private static final JavaScriptResourceReference SPLITBUTTON_JS = new JavaScriptResourceReference(AbstractSplitButton.class, "SplitButton.js");

	private AbstractLink button;
	private boolean defaultFormProcessing = true;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 */
	public AbstractSplitButton(String id, List<IMenuItem> items)
	{
		this(id, Model.ofList(items));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list model of {@link IMenuItem}
	 */
	public AbstractSplitButton(String id, IModel<List<IMenuItem>> items)
	{
		super(id, items);
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(JavaScriptHeaderItem.forReference(SPLITBUTTON_JS));
	}

	// Properties //

	/**
	 * Returns whether form should be processed the default way.<br/>
	 * If false, all validation and form updating is bypassed and the onSubmit method of that button is called directly, and the onSubmit method of the parent form is not called.
	 *
	 * @return {@code true} or {@code false}
	 */
	public boolean getDefaultFormProcessing()
	{
		return this.defaultFormProcessing;
	}

	/**
	 * Sets whether form should be processed the default way.
	 *
	 * @param defaultFormProcessing {@code true} or {@code false}
	 * @return this, for chaining
	 */
	public AbstractSplitButton setDefaultFormProcessing(boolean defaultFormProcessing)
	{
		this.defaultFormProcessing = defaultFormProcessing;

		return this;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// button-set //
		final WebMarkupContainer buttonset = new WebMarkupContainer("buttonset");
		this.add(buttonset);

		// main-button //
		this.button = this.newLink("button");
		this.button.add(newButtonSetBehavior(buttonset)); // the 'buttonset' behavior is attached to the button to be re-applied on menu click
		buttonset.add(this.button);

		// menu-button //
		buttonset.add(newMenuContainer("select"));
	}

	@Override
	protected void onBeforeRender()
	{
		this.addOrReplace(this.newMenu("menu", this.getModelObject()));

		super.onBeforeRender();
	}

	// Factories //

	/**
	 * Gets a new Link for the main button
	 *
	 * @param id the markup id
	 * @return an {@link AbstractLink}
	 */
	protected abstract AbstractLink newLink(String id);

	/**
	 * Gets a new buttonset behavior
	 *
	 * @param component the component on which the behavior should apply
	 * @return a new {@link JQueryUIBehavior}
	 */
	private static JQueryUIBehavior newButtonSetBehavior(Component component)
	{
		return new JQueryUIBehavior(JQueryWidget.getSelector(component), "buttonset");
	}

	/**
	 * Gets a new {@link WebMarkupContainer} responsible for showing the {@link Menu}
	 *
	 * @param id the markup id
	 * @return a new {@link WebMarkupContainer}
	 */
	private static WebMarkupContainer newMenuContainer(String id)
	{
		WebMarkupContainer container = new WebMarkupContainer(id);

		container.add(new ButtonBehavior(JQueryWidget.getSelector(container)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void bind(Component component)
			{
				super.bind(component);

				this.on("click", "function() { return showSplitButtonMenu(this); }");
			}

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				this.setOption("text", false);
				this.setOption("icons", new Options("primary", Options.asString(JQueryIcon.TRIANGLE_1_S)));
			}
		});

		return container;
	}

	/**
	 * Gets a new {@link Menu} that will be displayed when the menu-container will be clicked
	 *
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 * @return a new {@link Menu}
	 */
	private Menu newMenu(String id, List<IMenuItem> list)
	{
		return new Menu(id, list) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item)
			{
				AbstractSplitButton.this.button.setDefaultModelObject(item);

				target.add(AbstractSplitButton.this.button);
			}
		};
	}
}
