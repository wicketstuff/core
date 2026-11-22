package wicket.contrib.tinymce.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
			\tlicense_key : "gpl",
			\tpromotion : false,
			\tstatusbar : false,
			\ttypeahead_urls : false,
			\tsetting_1: false,
			\tsetting : "help,blah\"""", settings.toJavaScript());
	}

	@Test
	void testLicenseKey()
	{
		final var settings = new TinyMCESettings(TinyMCESettings.Language.en_GB);
		assertEquals(TinyMCESettings.GPL_LICENSE_KEY, settings.getLicenseKey());

		settings.setLicenseKey("T8LK:commercial");
		assertTrue(settings.toJavaScript().contains("license_key : \"T8LK:commercial\""));

		settings.setLicenseKey(null);
		assertFalse(settings.toJavaScript().contains("license_key"));
	}
}
