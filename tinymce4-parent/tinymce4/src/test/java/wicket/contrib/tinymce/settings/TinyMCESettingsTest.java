package wicket.contrib.tinymce.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wicket.contrib.tinymce4.settings.TinyMCESettings;
import wicket.contrib.tinymce4.settings.TinyMCESettings.Language;
import wicket.contrib.tinymce4.settings.TinyMCESettings.Theme;

/**
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille (fbille@avaleo.net)
 */
public class TinyMCESettingsTest
{
	private TinyMCESettings settings;
	private StringBuffer buffer;

	@BeforeEach
	protected void setUp() throws Exception
	{
		settings = new TinyMCESettings(Theme.modern, Language.en_GB);
		buffer = new StringBuffer();
	}

	@AfterEach
	protected void tearDown() throws Exception
	{
		settings = null;
		buffer = null;
	}

	@Test
	public void testCustomSettings()
	{
		settings.addCustomSetting("setting_1: false");
		settings.addCustomSetting("setting : \"help,blah\"");
		assertEquals(",\n\tsetting_1: false,\n\tsetting : \"help,blah\"", settings.toJavaScript());
	}

}
