package org.wicketstuff.foundation.buttongroup;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonOptions;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.util.StringUtil;

public class ButtonGroupPanelTest {

	@Test
	public void renderBasicTest() { 
		WicketTester tester = new WicketTester();
		tester.startComponentInPage(createBasicButtonGroup("buttons"));
		TagTester tag = tester.getTagByWicketId("buttons");
		tag.getAttributeContains("class", "button-group");
	}

	@Test
	public void renderAdvancedTest() { 
		WicketTester tester = new WicketTester();
		tester.startComponentInPage(createAdvancedButtonGroup("buttons"));
		TagTester group = tester.getTagByWicketId("group");
		assertTrue(group.getAttributeContains("class", "button-group"));
		assertTrue(group.getAttributeContains("class", StringUtil.EnumNameToCssClassName(ButtonRadius.ROUND.name())));
		List<TagTester> btnList = tester.getTagsByWicketId("btn");
		assertTrue(btnList.get(0).getAttributeContains("class", 
				"button"));
		assertTrue(btnList.get(0).getAttributeContains("class", 
				StringUtil.EnumNameToCssClassName(ButtonColor.ALERT.name())));
		assertTrue(btnList.get(1).getAttributeContains("class", 
				"button"));
		assertTrue(btnList.get(1).getAttributeContains("class", 
				StringUtil.EnumNameToCssClassName(ButtonColor.SECONDARY.name())));
		assertTrue(btnList.get(2).getAttributeContains("class", 
				"button"));
		assertTrue(btnList.get(2).getAttributeContains("class", 
				StringUtil.EnumNameToCssClassName(ButtonColor.SUCCESS.name())));
	}

	@Test
	public void renderButtonBarTest() { 
		WicketTester tester = new WicketTester();
		tester.startPage(ButtonBarTestPage.class);
		tester.dumpPage();
		TagTester tag = tester.getTagByWicketId("border");
		tag.getAttributeContains("class", "button-bar");
	}
	
	@Test
	public void renderStacked() {
		WicketTester tester = new WicketTester();
		tester.startComponentInPage(createStackedButtonGroup("buttons"));
	}
	
	private TestButtonGroupPanel createBasicButtonGroup(String id) {
		ArrayList<ButtonOptions> btnOptions = new ArrayList<>();
		btnOptions.add(new ButtonOptions());
		ButtonGroupOptions groupOptions = new ButtonGroupOptions();
		TestButtonGroupPanel group = new TestButtonGroupPanel(id, groupOptions, btnOptions);
		return group;
	}

	private TestButtonGroupPanel createAdvancedButtonGroup(String id) {
		ArrayList<ButtonOptions> btnOptions = new ArrayList<>();
		btnOptions.add(new ButtonOptions(ButtonColor.ALERT));
		btnOptions.add(new ButtonOptions(ButtonColor.SECONDARY));
		btnOptions.add(new ButtonOptions(ButtonColor.SUCCESS));
		ButtonGroupOptions groupOptions = new ButtonGroupOptions(ButtonRadius.ROUND);
		TestButtonGroupPanel group = new TestButtonGroupPanel(id, groupOptions, btnOptions);
		return group;
	}
	
	private TestButtonGroupPanel createStackedButtonGroup(String id) {
		ArrayList<ButtonOptions> btnOptions = new ArrayList<>();
		btnOptions.add(new ButtonOptions(ButtonColor.ALERT));
		btnOptions.add(new ButtonOptions(ButtonColor.SECONDARY));
		btnOptions.add(new ButtonOptions(ButtonColor.SUCCESS));
		ButtonGroupOptions groupOptions = new ButtonGroupOptions(ButtonGroupStacking.STACK);
		TestButtonGroupPanel group = new TestButtonGroupPanel(id, groupOptions, btnOptions);
		return group;
	}
}
