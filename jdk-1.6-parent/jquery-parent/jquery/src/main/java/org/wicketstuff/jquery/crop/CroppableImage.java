package org.wicketstuff.jquery.crop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.template.PackageTextTemplate;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CroppableImage extends Panel
{
	private static final long serialVersionUID = 1L;
	private WebMarkupContainer container;
	private WebMarkupContainer image;
	private final Map<String, Object> vars;

	public CroppableImage(String id, CropBehaviour behaviour, final String imageUrl,
		final int width, final int height)
	{
		super(id);

		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);

		image = new WebMarkupContainer("image");
		image.setOutputMarkupId(true);
		add(image);

		vars = new HashMap<String, Object>();
		vars.put("container", container.getMarkupId());
		vars.put("image", image.getMarkupId());
		vars.put("imageUrl", imageUrl);
		vars.put("width", width);
		vars.put("height", height);
		vars.put("rwidth", width / 2);
		vars.put("rheight", height / 2);
		vars.put("cropBg", urlFor(CropBehaviour.CROP_BG, null));

		image.add(behaviour);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		PackageTextTemplate template = new PackageTextTemplate(CroppableImage.class, "crop.css");
		String css = template.interpolate(vars).asString();

		response.render(CssHeaderItem.forCSS(css, "croppable-image." + getMarkupId()));


	}


}
