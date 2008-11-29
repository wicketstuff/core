package org.wicketstuff.yui.examples.pages;

import java.util.ArrayList;

import org.wicketstuff.yui.YuiAttribute;
import org.wicketstuff.yui.YuiImage;
import org.wicketstuff.yui.YuiProperty;
import org.wicketstuff.yui.YuiTextBox;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.selection.Selection;
import org.wicketstuff.yui.markup.html.selection.SelectionSettings;

public class SelectionPage extends WicketExamplePage
{
	private ArrayList imageListA = new ArrayList();
	private ArrayList imageListB = new ArrayList();
	private ArrayList textListA = new ArrayList();
	private ArrayList textListB = new ArrayList();

	public SelectionPage()
	{
		/**
		 * Define the animation properties
		 */
		YuiProperty yuiPropertyBorderTopColor = new YuiProperty("'#FF0000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderBottomColor = new YuiProperty("'#FF0000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderLeftColor = new YuiProperty("'#000000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderRightColor = new YuiProperty("'#000000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyFontColor = new YuiProperty("'#000000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderWidth = new YuiProperty("4", "4", true);
		YuiProperty yuiPropertyFontSize = new YuiProperty("18", "15", true);
		YuiProperty yuiPropertyBorderRadius = new YuiProperty("30", "30", true);
		YuiProperty yuiPropertyBorderTopLeftRadius = new YuiProperty("0", "0", true);

		YuiAttribute yuiAttribute = new YuiAttribute();

		yuiAttribute.add("borderTopColor", yuiPropertyBorderTopColor);
		yuiAttribute.add("borderBottomColor", yuiPropertyBorderBottomColor);
		yuiAttribute.add("borderLeftColor", yuiPropertyBorderLeftColor);
		yuiAttribute.add("borderRightColor", yuiPropertyBorderRightColor);
		yuiAttribute.add("color", yuiPropertyFontColor);
		yuiAttribute.add("borderWidth", yuiPropertyBorderWidth);
		yuiAttribute.add("fontSize", yuiPropertyFontSize);
		yuiAttribute.add("borderRadius", yuiPropertyBorderRadius);
		yuiAttribute.add("borderTopLeftRadius", yuiPropertyBorderTopLeftRadius);

		imageListA.add(new YuiImage("style/broom.bmp"));
		imageListA.add(new YuiImage("style/flower.bmp"));
		imageListA.add(new YuiImage("style/window.bmp"));
		add(new Selection("aSelection", SelectionSettings.getDefault(yuiAttribute, "bounceIn", 1, "click", 1, imageListA)));

		imageListB.add(new YuiImage("style/broom.bmp"));
		imageListB.add(new YuiImage("style/flower.bmp"));
		imageListB.add(new YuiImage("style/window.bmp"));
		add(new Selection("bSelection", SelectionSettings.getDefault(yuiAttribute, "bounceIn", 1, "click", 2, "Only up to 2 selections allowed only!", imageListB)));

		textListA.add(new YuiTextBox("#8585DB", 80, 80, "Cat"));
		textListA.add(new YuiTextBox("#FFA8A8", 80, 80, "Boy"));
		textListA.add(new YuiTextBox("#B3FFB3", 80, 80, "Apple"));
		add(new Selection("cSelection", SelectionSettings.getDefault(yuiAttribute, "easeOutStrong", 1, "click", 1, textListA)));

		textListB.add(new YuiTextBox("#8585DB", 80, 80, "Three"));
		textListB.add(new YuiTextBox("#FFA8A8", 80, 80, "Two"));
		textListB.add(new YuiTextBox("#B3FFB3", 80, 80, "One"));
		add(new Selection("dSelection", SelectionSettings.getDefault(yuiAttribute, "bounceIn", 1, "click", 3, "Up to 3 selections allowed only!", textListB)));
	}
}
