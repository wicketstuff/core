package org.wicketstuff.jquery.lightbox;

import org.wicketstuff.jquery.Options;

public class LightboxOptions extends Options
{

	private static final long serialVersionUID = 1L;

	public LightboxOptions()
	{
		fixedNavigation(true);
		imageLoading("/resources/org.wicketstuff.jquery.lightbox.LightboxBehaviour/lightbox-ico-loading.gif");
		imageBtnClose("/resources/org.wicketstuff.jquery.lightbox.LightboxBehaviour/lightbox-btn-close.gif");
		imageBtnPrev("/resources/org.wicketstuff.jquery.lightbox.LightboxBehaviour/lightbox-btn-prev.gif");
		imageBtnNext("/resources/org.wicketstuff.jquery.lightbox.LightboxBehaviour/lightbox-btn-next.gif");
		imageBlank("/resources/org.wicketstuff.jquery.lightbox.LightboxBehaviour/lightbox-blank.gif");
	}

	public LightboxOptions fixedNavigation(boolean fixedNavigation)
	{
		set("fixedNaviation", fixedNavigation);
		return this;
	}

	public LightboxOptions imageLoading(String imageLoading)
	{
		set("imageLoading", imageLoading);
		return this;
	}

	public LightboxOptions imageBlank(String imageBlank)
	{
		set("imageBlank", imageBlank);
		return this;
	}

	public LightboxOptions imageBtnClose(String imageBtnClose)
	{
		set("imageBtnClose", imageBtnClose);
		return this;
	}

	public LightboxOptions imageBtnPrev(String imageBtnPrev)
	{
		set("imageBtnPrev", imageBtnPrev);
		return this;
	}

	public LightboxOptions imageBtnNext(String imageBtnNext)
	{
		set("imageBtnNext", imageBtnNext);
		return this;
	}

}
