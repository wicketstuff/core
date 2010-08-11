package org.wicketstuff.jquery.crop;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 *
 */
public class CroppableImage extends Panel {
	private WebMarkupContainer container;
	private WebMarkupContainer image;
	
	public CroppableImage(String id, CropBehaviour behaviour, final String imageUrl, final int width, final int height) {
		super(id);

		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);
		
		image = new WebMarkupContainer("image");
		image.setOutputMarkupId(true);
		add(image);

		LoadableDetachableModel vars = new LoadableDetachableModel() {
			@Override protected Object load() {
				Map vars = new HashMap();
				vars.put("container", container.getMarkupId());
				vars.put("image", image.getMarkupId());
				vars.put("imageUrl", imageUrl);
				vars.put("width", width);
				vars.put("height", height);
				vars.put("rwidth", width/2);
				vars.put("rheight", height/2);
				vars.put("cropBg", urlFor(CropBehaviour.CROP_BG));
				return vars;
			}
		};
	
		add(TextTemplateHeaderContributor.forCss(CroppableImage.class, "crop.css", vars));

		image.add(behaviour);
	}

}
