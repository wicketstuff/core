package wicket.contrib.tinymce.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.wicketstuff.tinymce6.settings.TinyMCESettings;

/**
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille (fbille@avaleo.net)
 * @author Johannes Renoth
 */
class TinyMCESettingsTest
{
	@Test
	void testCustomSettings()
	{
		final var settings = new TinyMCESettings(TinyMCESettings.Language.en_GB);
		settings.addCustomSetting("setting_1: false");
		settings.addCustomSetting("setting : \"help,blah\"");
		assertEquals("""
			,
			\tpromotion : false,
			\tstatusbar : false,
			\ttypeahead_urls : false,
			\tsetting_1: false,
			\tsetting : "help,blah\"""", settings.toJavaScript());
	}

}
