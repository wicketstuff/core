package org.wicketstuff.foundation.button;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.util.StringUtil;

public class FoundationAjaxSubmitLinkTest {
	
	@Test
	public void testBasic() {
		testButton(new WicketTester(), new FoundationAjaxSubmitLink("btn"), new ArrayList<String>());
	}

	@Test
	public void testAdvancedSize() {
		testButton(new WicketTester(), new FoundationAjaxSubmitLink("btn", new ButtonOptions(ButtonSize.TINY)), 
				Arrays.asList(StringUtil.EnumNameToCssClassName(ButtonSize.TINY.name())));
	}

	@Test
	public void testAdvancedColor() {
		testButton(new WicketTester(), new FoundationAjaxSubmitLink("btn", new ButtonOptions(ButtonColor.SUCCESS)), 
				Arrays.asList(StringUtil.EnumNameToCssClassName(ButtonColor.SUCCESS.name())));
	}

	@Test
	public void testAdvancedRadius() {
		testButton(new WicketTester(), new FoundationAjaxSubmitLink("btn", new ButtonOptions(ButtonRadius.RADIUS)), 
				Arrays.asList(StringUtil.EnumNameToCssClassName(ButtonRadius.RADIUS.name())));
	}

	@Test
	public void testAdvancedState() {
		testButton(new WicketTester(), new FoundationAjaxSubmitLink("btn", new ButtonOptions(ButtonState.DISABLED)), 
				Arrays.asList(StringUtil.EnumNameToCssClassName(ButtonState.DISABLED.name())));
	}

	@Test
	public void testAdvancedExpansion() {
		testButton(new WicketTester(), new FoundationAjaxSubmitLink("btn", new ButtonOptions(ButtonExpansion.EXPAND)), 
				Arrays.asList(StringUtil.EnumNameToCssClassName(ButtonExpansion.EXPAND.name())));
	}
	
	private void testButton(WicketTester tester, FoundationAjaxSubmitLink btn, List<String> additionalCssClassesToVerify) {
		FoundationButtonTestPage page = new FoundationButtonTestPage(btn);
		tester.startPage(page);
		TagTester tagTester = tester.getTagByWicketId(btn.getId());
		assertTrue(tagTester.getAttributeContains("class", "button"));
		for (String clazz: additionalCssClassesToVerify) {
			assertTrue(tagTester.getAttributeContains("class", clazz));
		}
	}
	
}
