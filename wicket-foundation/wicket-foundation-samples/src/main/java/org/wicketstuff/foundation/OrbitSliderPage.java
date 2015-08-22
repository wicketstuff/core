package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.foundation.orbitslider.FoundationOrbitContentSlider;
import org.wicketstuff.foundation.orbitslider.FoundationOrbitSlider;
import org.wicketstuff.foundation.orbitslider.FoundationOrbitSliderAjaxLink;
import org.wicketstuff.foundation.orbitslider.OrbitSliderContent;
import org.wicketstuff.foundation.orbitslider.OrbitSliderItem;

public class OrbitSliderPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public OrbitSliderPage(PageParameters params) {
		super(params);
		List<OrbitSliderItem> items = new ArrayList<>();
		items.add(new OrbitSliderItem("1", new PackageResourceReference(this.getClass(), "andromeda-orbit.jpg"), "Caption 1"));
		items.add(new OrbitSliderItem("2", new PackageResourceReference(this.getClass(), "launch-orbit.jpg"), "Caption 2"));
		items.add(new OrbitSliderItem("3", new PackageResourceReference(this.getClass(), "satelite-orbit.jpg"), "Caption 3"));
		FoundationOrbitSlider orbitSlider = new FoundationOrbitSlider("orbitSlider", new ListModel<>(items));
		add(orbitSlider);
		List<OrbitSliderContent> contents = new ArrayList<>();
		contents.add(new OrbitSliderContent("1", "Heading 1", "Subheading 1"));
		contents.add(new OrbitSliderContent("2", "Heading 2", "Subheading 2"));
		contents.add(new OrbitSliderContent("3", "Heading 3", "Subheading 3"));
		FoundationOrbitContentSlider contentSlider = new FoundationOrbitContentSlider("contentSlider", new ListModel<>(contents));
		add(contentSlider);

		add(new FoundationOrbitSliderAjaxLink("slide1", Model.of("slide1")));
		add(new FoundationOrbitSliderAjaxLink("slide2", Model.of("slide2")));
		add(new FoundationOrbitSliderAjaxLink("slide3", Model.of("slide3")));
		
		List<OrbitSliderContent> slides = new ArrayList<>();
		slides.add(new OrbitSliderContent("slide1", "This is heading 1", "This is subheading 1"));
		slides.add(new OrbitSliderContent("slide2", "This is heading 2", "This is subheading 2"));
		slides.add(new OrbitSliderContent("slide3", "This is heading 3", "This is subheading 3"));
		FoundationOrbitContentSlider slider = new FoundationOrbitContentSlider("slider", new ListModel<>(slides));
		add(slider);
	}
}
