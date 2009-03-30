package org.wicketstuff.yui.markup.html.carousel;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.behavior.Attributes;
import org.wicketstuff.yui.markup.html.contributor.YuiLoaderContributor;
import org.wicketstuff.yui.markup.html.contributor.YuiLoaderModule;

public class Carousel<T> extends Panel
{
	private static final long serialVersionUID = 1L;

	private static final String MODULE_NAME = "wicket_yui_carousel";

	private static final ResourceReference MODULE_JS_REF = new ResourceReference(Carousel.class,
			"Carousel.js");

	private static final String[] MODULE_REQUIRES = { "carousel", "animation" };

	/** the carousel */
	private WebMarkupContainer carousel;

	/**
	 * 
	 * @param id
	 * @param list
	 * @param css
	 */
	public Carousel(String id, IModel<List<T>> list)
	{
		super(id);

		WebMarkupContainer skinContainer;
		add(skinContainer = new WebMarkupContainer("skin_container"));

		skinContainer.add(carousel = new WebMarkupContainer("carousel_container"));
		carousel.setOutputMarkupId(true);

		carousel.add(new ListView<T>("list", list)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<T> item)
			{
				item.add(newPanel("item", item.getModelObject()).setRenderBodyOnly(true));
			}
		});

		skinContainer.add(new AttributeModifier("class", true, new Model<String>(getCssClass())));

		// add this new Yui Loader Module
		add(YuiLoaderContributor.addModule(new YuiLoaderModule(MODULE_NAME,
				YuiLoaderModule.ModuleType.js, MODULE_JS_REF, MODULE_REQUIRES)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getInitJS()
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("var ").append(getCarouselVar()).append(
						" = new YAHOO.widget.Carousel(").append("\"").append(getCarouselId())
						.append("\",").append(getOpts()).append(");");

				buffer.append(getCarouselVar()).append(".render();");
				buffer.append(getCarouselVar()).append(".show()");
				return buffer.toString();
			}

			private String getCarouselVar()
			{
				return "var_" + getCarouselId();
			}

			private String getCarouselId()
			{
				return carousel.getMarkupId();
			}
		}));
	}

	/**
	 * default carousel with Sam Skin
	 * 
	 * @param id
	 * @param list
	 */
	public Carousel(String id, final List<T> list)
	{
		this(id, new LoadableDetachableModel<List<T>>()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected List<T> load()
			{
				return list;
			}

		});
	}

	protected String getCssClass()
	{
		return "yui-skin-sam";
	}

	protected Component newPanel(String id, T object)
	{
		return new EmptyPanel(id);
	}

	/**
	 * Override this method to provde own customised options...use these
	 * Options... { animation {} , }
	 * 
	 * isCircular: true, numVisible: 1
	 * 
	 * @see <a href="http://developer.yahoo.com/yui/carousel/#apiref">YUI
	 *      developer Carousel Reference Table</a>
	 * @return
	 */
	protected String getOpts()
	{
		// or simply {}
		Attributes attributes = new Attributes();
		// animation - animation : {speed : 0.5}
		attributes.add(new Attributes("animation", new Attributes("speed", 0.5F).toString()));
		// circular
		attributes.add(new Attributes("isCircular", "true"));
		// num Visible
		attributes.add(new Attributes("numVisible", 1));
		return attributes.toString();
	}

}
