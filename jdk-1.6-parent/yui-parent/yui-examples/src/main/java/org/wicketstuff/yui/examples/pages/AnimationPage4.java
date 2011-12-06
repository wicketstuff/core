package org.wicketstuff.yui.examples.pages;
import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailGallery;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailSettings;

/**
 * Example on using Animation...
 * 
 * @author josh
 *
 */
public class AnimationPage4 extends WicketExamplePage 
{
	int COLS = 3;
	int ROWS = 3;
	int TN_WIDTH = 120;
	int TN_HEIGHT = 80;
	
	public AnimationPage4()
	{
		// a gallery of AnimatedThumbnails
		List<AnimatedThumbnailSettings> listOfThumbnails = new ArrayList<AnimatedThumbnailSettings>();
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/singapore.png", 	"images/sgp.jpg", 		"Singapore"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/malaysia.png", 	"images/kl.jpg", 		"Malaysia"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/indonesia.png", 	"images/jakarta.jpg", 	"Indonesia"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/philippines.png","images/makati.jpg", 	"Philippines"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/thailand.png", 	"images/bangkok.jpg", 	"Thailand"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/hongkong.png", 	"images/hk.jpg", 		"Hong Kong"));
		
		AnimatedThumbnailGallery gallery;
		add(gallery = new AnimatedThumbnailGallery("gallery", listOfThumbnails, COLS, ROWS));
		gallery.setThumbnailSize(TN_WIDTH, TN_HEIGHT);
		gallery.setPictureSize(TN_WIDTH * COLS, TN_HEIGHT * ROWS);
	}
}
