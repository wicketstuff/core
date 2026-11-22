package org.wicketstuff.hugerte.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.wicketstuff.hugerte.settings.HugeRteSettings;

/**
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille (fbille@avaleo.net)
 * @author Johannes Renoth
 */
class HugeRteSettingsTest
{
	@Test
	void testCustomSettings()
	{
		final var settings = new HugeRteSettings(HugeRteSettings.Language.en_GB);
		settings.addCustomSetting("setting_1: false");
		settings.addCustomSetting("setting : \"help,blah\"");
		assertEquals("""
			,
			\tstatusbar : false,
			\ttypeahead_urls : false,
			\tsetting_1: false,
			\tsetting : "help,blah\"""", settings.toJavaScript());
	}

}
