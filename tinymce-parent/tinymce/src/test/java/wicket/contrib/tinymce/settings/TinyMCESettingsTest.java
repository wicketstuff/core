package wicket.contrib.tinymce.settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import wicket.contrib.tinymce.settings.TinyMCESettings.Language;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;
import wicket.contrib.tinymce.settings.TinyMCESettings.Toolbar;

/**
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille (fbille@avaleo.net)
 */
public class TinyMCESettingsTest extends TestCase {
	private TinyMCESettings settings;
	private StringBuffer buffer;

	protected void setUp() throws Exception {
		settings = new TinyMCESettings(Theme.simple, Language.en);
		buffer = new StringBuffer();
	}

	protected void tearDown() throws Exception {
		settings = null;
		buffer = null;
	}

	public void testToolbarButtons() {
		// Fail if not advanced theme:
		try {
			settings.setToolbarButtons(Toolbar.first, Arrays
					.asList(new Button[] { TinyMCESettings.bold,
							TinyMCESettings.separator, TinyMCESettings.copy }));
			fail("should have thrown illegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		settings = new TinyMCESettings(Theme.advanced, Language.en);
		settings.setToolbarButtons(Toolbar.first, Arrays.asList(new Button[] {
				TinyMCESettings.bold, TinyMCESettings.separator,
				TinyMCESettings.copy }));
		settings.setToolbarButtons(Toolbar.third, Collections.EMPTY_LIST);
		assertEquals(
				"\n\tmode : \"exact\",\n\ttheme : \"advanced\",\n\tlanguage : \"en\",\n\ttheme_advanced_buttons1 : \"bold,separator,copy\",\n\ttheme_advanced_buttons3 : \"\"",
				settings.toJavaScript(Mode.exact, Collections.EMPTY_LIST));
	}

	public void testAddStatusbarLocation() throws Exception {
		settings.setStatusbarLocation(TinyMCESettings.Location.top);
		settings.addStatusbarLocation(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_statusbar_location : \"top\"", buffer
				.toString());
	}

	public void testAddToolbarLocation() throws Exception {
		settings.setToolbarLocation(TinyMCESettings.Location.top);
		settings.addToolbarLocation(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_toolbar_location : \"top\"", buffer
				.toString());
	}

	public void testAddToolbarAlign() throws Exception {
		settings.setToolbarAlign(TinyMCESettings.Align.left);
		settings.addToolbarAlign(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_toolbar_align : \"left\"", buffer
				.toString());
	}

	public void testAddVerticalResizing() throws Exception {
		settings.setResizing(true);
		settings.addResizing(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_resizing : true", buffer.toString());

		buffer = new StringBuffer();
		settings.setResizing(false);
		settings.addResizing(buffer);

		assertEquals("", buffer.toString());
	}

	public void testAddHorizontalResizing() throws Exception {
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

	public void testAddPlugins() throws Exception {
		SavePlugin savePlugin = new SavePlugin();
		DateTimePlugin dateTimePlugin = new DateTimePlugin();

		settings.register(savePlugin);
		settings.register(dateTimePlugin);

		settings.addPlugins(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\tplugins : \"save, insertdatetime\"", buffer
				.toString());
	}

	public void testAdd() throws Exception {
		SavePlugin savePlugin = new SavePlugin();
		settings.register(savePlugin);
		assertNotNull(settings.getPlugins());
		assertEquals(1, settings.getPlugins().size());
		settings.add(savePlugin.getSaveButton(), TinyMCESettings.Toolbar.first,
				TinyMCESettings.Position.before);
		assertEquals(1, settings.getPlugins().size());
	}

	public void testDisableButton1() {
		settings.disableButton(TinyMCESettings.bold);

		settings.addDisabledButtons(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_disable : \"bold\"", buffer
				.toString());
	}

	public void testDisableButton2() {
		settings.disableButton(TinyMCESettings.bold);
		settings.disableButton(TinyMCESettings.italic);

		settings.addDisabledButtons(buffer);

		assertTrue(buffer.capacity() > 0);
		assertEquals(",\n\ttheme_advanced_disable : \"bold,italic\"", buffer
				.toString());
	}

	public void testDateTimePlugin() {
		DateTimePlugin plugin = new DateTimePlugin();
		plugin.setDateFormat("%Y-%m-%d");
		settings.register(plugin);

		String javascript = settings.toJavaScript(Mode.none,
				Collections.EMPTY_LIST);
		Pattern pattern = Pattern.compile(
				".*,\n\tplugin_insertdate_dateFormat : \"%Y-%m-%d\"",
				Pattern.DOTALL);
		assertTrue(pattern.matcher(javascript).matches());
	}

	public void testDefinePluginExtensions() {
		Plugin plugin = new Plugin("mockplugin") {
			private static final long serialVersionUID = 1L;

			protected void definePluginExtensions(StringBuffer buffer) {
				buffer.append("alert('Hello Mock World');");
			}

		};

		settings.register(plugin);

		String javascript = settings.getAdditionalPluginJavaScript();
		assertEquals("alert('Hello Mock World');", javascript);
	}
}
