package org.wicketstuff.yui.examples.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.wicketstuff.yui.YuiImage;
import org.wicketstuff.yui.YuiSortConstants;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.sort.SortBox;
import org.wicketstuff.yui.markup.html.sort.SortGroup;
import org.wicketstuff.yui.markup.html.sort.SortSettings;

/**
 * This class demostrates how you can use the org.wicketstuff.yui.markup.html.sort package
 * to create images that can be sorted and retrieve the ordering after sort.
 * <p>
 * 
 * @author cptan
 *
 */
public class SortPage extends WicketExamplePage {
	
	/**
	 * Defines a SortPage object
	 *
	 */
	public SortPage() {

		/**
		 * Example A: Intersect Mode
		 */

		// Step 1a: Define the first image and its position
		YuiImage blueA = new YuiImage("style/blue.bmp", "blue");
		blueA.setTop(50);
		blueA.setLeft(80);

		// Step 1b: Define the second image and its position
		YuiImage greenA = new YuiImage("style/green.bmp", "green");
		greenA.setTop(50);
		greenA.setLeft(195);

		// Step 1c: Define the third image and its position
		YuiImage pinkA = new YuiImage("style/pink.bmp", "pink");
		pinkA.setTop(50);
		pinkA.setLeft(305);

		// Step 1d: Define the fourth image and its position
		YuiImage yellowA = new YuiImage("style/yellow.bmp", "yellow");
		yellowA.setTop(50);
		yellowA.setLeft(415);

		// Step 2: The initial ordering of the images depends on the order in which the
		// image is added to the List. In this case, the initial ordering is
		// blue, green, pink, yellow.
		final List<YuiImage> sortListA = new ArrayList<YuiImage>();
		sortListA.add(blueA);
		sortListA.add(greenA);
		sortListA.add(pinkA);
		sortListA.add(yellowA);

		// Step 3: Declare the type of mode and adding a textfield to contain the
		// ordering of the images
		final SortSettings settingsA = SortSettings.getDefault(
				YuiSortConstants.INTERSECT, sortListA);
		TextField tfValueA = new TextField("valueA");
		SortGroup sortGroupA = new SortGroup("sortGroupA", settingsA, tfValueA);
		add(sortGroupA);

		//Step 4: Create a SortBox for each image
		ListView sortListViewA = new ListView("sortListViewA", sortListA) {
			protected void populateItem(ListItem item) {
				YuiImage imageA = (YuiImage) item.getModelObject();
				item.add(new SortBox("sortBoxA", item.getIndex(), imageA,
						settingsA));
			};
		};
		sortGroupA.add(sortListViewA);

		/**
		 * Example B: Point Mode
		 */

		//Step 1a: Define the first image and its position
		YuiImage blueB = new YuiImage("style/blue.bmp", "blue");
		blueB.setTop(200);
		blueB.setLeft(80);

		//Step 1b: Define the second image and its position
		YuiImage greenB = new YuiImage("style/green.bmp", "green");
		greenB.setTop(200);
		greenB.setLeft(195);
		
		//Step 1c: Define the third image and its position
		YuiImage pinkB = new YuiImage("style/pink.bmp", "pink");
		pinkB.setTop(200);
		pinkB.setLeft(305);
		
		//Step 1d: Define the fourth image and its position
		YuiImage yellowB = new YuiImage("style/yellow.bmp", "yellow");
		yellowB.setTop(200);
		yellowB.setLeft(415);

		// Step 2: The initial ordering of the images depends on the order in which the
		// image is added to the List. In this case, the initial ordering is
		// blue, green, pink, yellow.
		final List<YuiImage> sortListB = new ArrayList<YuiImage>();
		sortListB.add(blueB);
		sortListB.add(greenB);
		sortListB.add(pinkB);
		sortListB.add(yellowB);

		// Step 3: Declare the type of mode and adding a textfield to contain the
		// ordering of the images
		final SortSettings settingsB = SortSettings.getDefault(
				YuiSortConstants.POINT, sortListB);
		TextField tfValueB = new TextField("valueB");
		SortGroup sortGroupB = new SortGroup("sortGroupB", settingsB, tfValueB);
		add(sortGroupB);

		// Step 4: Create a SortBox for each image
		ListView sortListViewB = new ListView("sortListViewB", sortListB) {
			protected void populateItem(ListItem item) {
				YuiImage imageB = (YuiImage) item.getModelObject();
				item.add(new SortBox("sortBoxB", item.getIndex(), imageB,
						settingsB));
			};
		};
		sortGroupB.add(sortListViewB);
	}
}
