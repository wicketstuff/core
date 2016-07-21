package org.wicketstuff.simile.timeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.simile.timeline.json.JsonUtils;
import org.wicketstuff.simile.timeline.model.BandInfoParameters;
import org.wicketstuff.simile.timeline.model.BandInfoParameters.DateTime;

public class Timeline extends Panel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TIMELINE_PARAMS_JAVASCRIPT = Timeline.class.getName() + "PARAMS_JS";

	private transient JsonUtils jsonUtils = new JsonUtils();

	private String timelineMarkupId;
	private String timelineDataId;

	public Timeline(String id, IModel<TimelineModel> model)
	{
		super(id, new CompoundPropertyModel<TimelineModel>(model));

		List<BandInfoParameters> bandInfo = new ArrayList<BandInfoParameters>();

		BandInfoParameters upperBand = new BandInfoParameters();
		upperBand.setIntervalPixels(70);
		upperBand.setHighlight(true);
		bandInfo.add(upperBand);

		BandInfoParameters lowerBand = new BandInfoParameters();
		lowerBand.setIntervalUnit(DateTime.YEAR);
		lowerBand.setIntervalPixels(200);
		lowerBand.setWidth("200px");
		bandInfo.add(lowerBand);

		init(id, model, bandInfo);
	}

	public Timeline(String id, IModel<TimelineModel> model, List<BandInfoParameters> bandInfo)
	{
		super(id, new CompoundPropertyModel<TimelineModel>(model));
		init(id, model, bandInfo);
	}

	private void init(String id, IModel<?> model, List<BandInfoParameters> bandInfo)
	{
// add(HeaderContributor
// .forJavaScript(new ResourceReference(getClass(),
// "./timeline_js/timeline-api.js?timeline-use-local-resources=true&bundle=true")));

		WebMarkupContainer tl = new WebMarkupContainer("tl");
		tl.setOutputMarkupId(true);
		add(tl);

		timelineMarkupId = tl.getMarkupId();

		WebMarkupContainer timelineData = new WebMarkupContainer("timelineData");
		timelineData.setOutputMarkupId(true);
		timelineData.add(new ListView<ITimelineEvent>("events")
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ITimelineEvent> item)
			{
				item.add(new AttributeModifier("title", new PropertyModel<String>(item.getModel(),
					"title")));
				item.add(new AttributeModifier("start", new PropertyModel<String>(item.getModel(),
					"startFormatted")));
				item.add(new AttributeModifier("end", new PropertyModel<String>(item.getModel(),
					"endFormatted")));
				item.add(new AttributeModifier("link", new PropertyModel<String>(item.getModel(),
					"link")));
				item.add(new AttributeModifier("isDuration", new PropertyModel<Boolean>(
					item.getModel(), "isDuration")));
				item.add(new AttributeModifier("color", new PropertyModel<String>(item.getModel(),
					"color")));

				Label child = new Label("text", new PropertyModel<String>(item.getModel(), "text"));
				child.setRenderBodyOnly(true);
				child.setEscapeModelStrings(false);
				item.add(child);
			}

		});

		add(timelineData);

		timelineDataId = timelineData.getMarkupId();

		StringBuffer loadScript = new StringBuffer();
		loadScript.append("function " + getLoadScriptName() + "() {\n");
		loadScript.append("var theme = Timeline.ClassicTheme.create();\n");
		loadScript.append("theme.event.bubble.width = 320;\n");
		loadScript.append("theme.event.bubble.height = 220;\n");
		loadScript.append("theme.ether.backgroundColors[1] = theme.ether.backgroundColors[0];\n");
		loadScript.append("var eventSource = new Timeline.DefaultEventSource(0);");
		loadScript.append("timeLineOnLoad('" + timelineMarkupId + "', '" + timelineDataId +
			"', theme, eventSource, " + jsonUtils.convertBandInfos(bandInfo) + ");\n");
		loadScript.append("}");

		add(new Label("loadScript", loadScript.toString()).setEscapeModelStrings(false));

	}

	private String getLoadScriptName()
	{
		return "timeLineLoad" + timelineMarkupId;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		PageParameters pp= new PageParameters();
		pp.add("timeline-use-local-resources", "true");
		pp.add("bundle", "true");
		response.render(JavaScriptHeaderItem.forReference(
				new PackageResourceReference(getClass(), "./timeline_js/timeline-api.js"), pp, "timeline-api"));

		StringBuffer parameters = new StringBuffer("");

		parameters.append("Timeline_ajax_url='")
				.append(timelineUrl())
				.append("';\n");

		response.render(JavaScriptHeaderItem.forScript(parameters.toString(),
			TIMELINE_PARAMS_JAVASCRIPT));
		response.render(OnLoadHeaderItem.forScript(getLoadScriptName() + "()"));
	}

	private CharSequence timelineUrl()
	{
		return urlFor(new PackageResourceReference(getClass(), "timeline_ajax/simile-ajax-api.js"),
			new PageParameters());
	}
}
