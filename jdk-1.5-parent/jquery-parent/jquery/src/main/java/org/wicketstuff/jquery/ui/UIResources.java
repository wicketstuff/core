package org.wicketstuff.jquery.ui;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public abstract class UIResources
{

	public static final ResourceReference FLORA_CSS = new PackageResourceReference(
		UIResources.class, "flora.css");

	public static final ResourceReference FLORA_SLIDER_CSS = new PackageResourceReference(
		UIResources.class, "slider/flora.slider.css");
}
