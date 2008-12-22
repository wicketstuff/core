package org.wicketstuff;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.artwork.ArtworkCanvasBehavior;

public abstract class AbstractGraphicPage extends WebPage {
	
	public AbstractGraphicPage() {
		
		WebMarkupContainer fancyMarkupContainer=new WebMarkupContainer("containerToBeFancy");
		fancyMarkupContainer.add(getBehavior());
		add(fancyMarkupContainer);
		
		
		
	}
	protected abstract ArtworkCanvasBehavior getBehavior();

}
