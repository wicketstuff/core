package wicket.contrib.tinymce.settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import wicket.contrib.tinymce.settings.TinyMCESettings.Language;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;
import wicket.contrib.tinymce.settings.TinyMCESettings.Toolbar;

/**
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille (fbille@avaleo.net)
 */
public class TinyMCESettingsTest extends TestCase
{
	private TinyMCESettings settings;
	private StringBuffer buffer;

	@Override
	protected void setUp() throws Exception
	{
		settings = new TinyMCESettings(Theme.simple, Language.en);
		buffer = new StringBuffer();
	}

	@Override
	protected void tearDown() throws Exception
	{
		settings = null;
		buffer = null;
	}

	public void testCustomSettings()
	{
		settings.addCustomSetting("setting_1: false");
		settings.addCustomSetting("setting : \"help,blah\"");
		assertEquals(",\n\tsetting_1: false,\n\tsetting : \"help,blah\"", settings.toJavaScript());
	}

	public void testToolbarButtons()
	{
		// Fail if not advanced theme:
		try
		{
			settings.setToolbarButtons(Toolbar.first,
				Arrays.asList(new Button[] { Button.bold, Button.separator, Button.copy }));
			fail("should have thrown illegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
		}

		settings = new TinyMCESettings(Theme.advanced, Language.en);
		settings.setToolbarButtons(Toolbar.first,
			Arrays.asList(new Button[] { Button.bold, Button.separator, Button.copy }));
		settings.setToolbarButtons(Toolbar.third, Collections.EMPTY_LIST);
		assertEquals(
			",\n\ttheme_advanced_buttons1 : \"bold,separator,copy\",\n\ttheme_advanced_buttons3 : \"\"",
			settings.toJavaScript());
	}

	public void testAddStatusbarLocation() throws Exception
	{
		settings.setStatusbarLocation(TinyMCESettings.Location.top);
		settings.addStatusbarLocation(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_statusbar_location : \"top\"", buffer.toString());
	}

	public void testAddToolbarLocation() throws Exception
	{
		settings.setToolbarLocation(TinyMCESettings.Location.top);
		settings.addToolbarLocation(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_toolbar_location : \"top\"", buffer.toString());
	}

	public void testAddToolbarAlign() throws Exception
	{
		settings.setToolbarAlign(TinyMCESettings.Align.left);
		settings.addToolbarAlign(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_toolbar_align : \"left\"", buffer.toString());
	}

	public void testAddVerticalResizing() throws Exception
	{
		settings.setResizing(true);
		settings.addResizing(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_resizing : true", buffer.toString());

		buffer = new StringBuffer();
		settings.setResizing(false);
		settings.addResizing(buffer);

		assertEquals("", buffer.toString());
	}

	public void testAddHorizontalResizing() throws Exception
	{
		settings.setHorizontalResizing(true);
		settings.addResizing(buffer);
		assertTrue(buffer.capacity() > 0);
		assertEquals("", buffer.toString());

		buffer = new StringBuffer();
		settings.setHorizontalResizing(false);
		settings.addResizing(buffer);
		assertEquals("", buffer.toString());

		buffer = new StringBuffer();
		settings.setResizing(true);
		settings.setHorizontalResizing(false);
		settings.addResizing(buffer);
		assertEquals(
			",\n\ttheme_advanced_resizing : true,\n\ttheme_advanced_resize_horizontal : false",
			buffer.toString());
	}

	public void testAddPlugins() throws Exception
	{
		SavePlugin savePlugin = new SavePlugin();
		DateTimePlugin dateTimePlugin = new DateTimePlugin();

		settings.register(savePlugin);
		settings.register(dateTimePlugin);

		settings.addPlugins(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\tplugins : \"save, insertdatetime\"", buffer.toString());
	}

	public void testAdd() throws Exception
	{
		SavePlugin savePlugin = new SavePlugin();
		settings.register(savePlugin);
		assertNotNull(settings.getPlugins());
		assertEquals(1, settings.getPlugins().size());
		settings.add(savePlugin.getSaveButton(), TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.before);
		assertEquals(1, settings.getPlugins().size());
	}

	public void testDisableButton1()
	{
		settings.disableButton(Button.bold);

		settings.addDisabledButtons(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_disable : \"bold\"", buffer.toString());
	}

	public void testDisableButton2()
	{
		settings.disableButton(Button.bold);
		settings.disableButton(Button.italic);

		settings.addDisabledButtons(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_disable : \"bold,italic\"", buffer.toString());
	}

	public void testDateTimePlugin()
	{
		DateTimePlugin plugin = new DateTimePlugin();
		plugin.setDateFormat("%Y-%m-%d");
		settings.register(plugin);

		String javascript = settings.toJavaScript();
		Pattern pattern = Pattern.compile(".*,\n\tplugin_insertdate_dateFormat : \"%Y-%m-%d\"",
			Pattern.DOTALL);
		assertTrue(pattern.matcher(javascript).matches());
	}

	public void testDefinePluginExtensions()
	{
		Plugin plugin = new Plugin("mockplugin")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void definePluginExtensions(StringBuffer buffer)
			{
				buffer.append("alert('Hello Mock World');");
			}

		};

		settings.register(plugin);

		String javascript = settings.getAdditionalPluginJavaScript();
		assertEquals("alert('Hello Mock World');", javascript);
	}
}
