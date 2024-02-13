package wicket.contrib.tinymce.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wicket.contrib.tinymce6.settings.TinyMCESettings;
import wicket.contrib.tinymce6.settings.TinyMCESettings.Language;

/**
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille (fbille@avaleo.net)
 */
public class TinyMCESettingsTest
{
	private TinyMCESettings settings;

	@BeforeEach
	protected void setUp() throws Exception
	{
		settings = new TinyMCESettings(Language.en_GB);
	}

	@AfterEach
	protected void tearDown() throws Exception
	{
		settings = null;
	}

	@Test
	public void testCustomSettings()
	{
		settings.addCustomSetting("setting_1: false");
		settings.addCustomSetting("setting : \"help,blah\"");
		assertEquals(",\n\tsetting_1: false,\n\tsetting : \"help,blah\"", settings.toJavaScript());
	}

}
