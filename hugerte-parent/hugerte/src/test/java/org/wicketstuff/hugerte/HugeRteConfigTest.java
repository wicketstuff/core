/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.junit.jupiter.api.Test;

class HugeRteConfigTest
{
	@Test
	void defaultsAreEmpty()
	{
		assertEquals("{}", HugeRteConfig.defaults().toJavaScript());
	}

	@Test
	void writesPluginsToolbarAndOptions()
	{
		var config = HugeRteConfig.defaults()
			.withPlugins(HugeRtePlugin.TABLE, HugeRtePlugin.LISTS)
			.withToolbar("undo redo | bold")
			.withMenubar(false)
			.with("height", 300);

		assertEquals(
			"{\"plugins\":[\"lists\",\"table\"],\"toolbar\":\"undo redo | bold\",\"menubar\":false,\"height\":300}",
			config.toJavaScript());
	}

	@Test
	void writesSeveralToolbarRowsAsArray()
	{
		assertEquals("{\"toolbar\":[\"undo redo\",\"bold italic\"]}",
			HugeRteConfig.defaults().withToolbar("undo redo", "bold italic").toJavaScript());
	}

	@Test
	void hidesTheToolbar()
	{
		var hidden = HugeRteConfig.defaults().withToolbar("bold").withoutToolbar();

		assertEquals("{\"toolbar\":false}", hidden.toJavaScript());
		assertEquals("{\"toolbar\":\"italic\"}", hidden.withToolbar("italic").toJavaScript());
		assertEquals("{}", hidden.withToolbar().toJavaScript());
		assertThrows(IllegalArgumentException.class,
			() -> new HugeRteConfig(Set.of(), List.of("bold"), true, Map.of()));
	}

	@Test
	void withersReturnCopies()
	{
		var base = HugeRteConfig.defaults().with("height", 300);
		var changed = base.with("height", 400).withPlugins(HugeRtePlugin.LINK);

		assertNotSame(base, changed);
		assertEquals("{\"height\":300}", base.toJavaScript());
		assertEquals("{\"plugins\":[\"link\"],\"height\":400}", changed.toJavaScript());
		assertEquals("{\"plugins\":[\"link\"]}",
			changed.without("height").without("absent").toJavaScript());
	}

	@Test
	void copiesMutableValues()
	{
		var list = new ArrayList<String>(List.of("a"));
		var config = HugeRteConfig.defaults().with("list", list);
		list.add("b");

		assertEquals("{\"list\":[\"a\"]}", config.toJavaScript());
		assertThrows(UnsupportedOperationException.class, () -> config.options().put("x", 1));
	}

	@Test
	void rejectsReservedOptions()
	{
		for (var option : List.of("selector", "target", "plugins", "toolbar"))
		{
			assertThrows(IllegalArgumentException.class,
				() -> HugeRteConfig.defaults().with(option, "x"));
		}
	}

	@Test
	void rejectsUnsupportedValues()
	{
		var defaults = HugeRteConfig.defaults();
		assertThrows(IllegalArgumentException.class, () -> defaults.with("a", new Object()));
		assertThrows(IllegalArgumentException.class, () -> defaults.with("a", null));
		assertThrows(IllegalArgumentException.class, () -> defaults.with("a", Double.NaN));
		assertThrows(IllegalArgumentException.class,
			() -> defaults.with("a", Map.of(1, "non-string key")));
		assertThrows(IllegalArgumentException.class,
			() -> defaults.with("a", List.of(new Object())));
	}

	@Test
	void rejectsBlankRawJavaScript()
	{
		assertThrows(IllegalArgumentException.class, () -> new RawJavaScript(" "));
	}

	@Test
	void isSerializable()
	{
		var config = HugeRteConfig.defaults()
			.withPlugins(HugeRtePlugin.LINK)
			.withToolbar("bold")
			.with("nested", Map.of("list", List.of(1, "two")))
			.with("setup", new RawJavaScript("function (e) {}"));

		var copy = WicketObjects.cloneObject(config);

		assertEquals(config, copy);
		assertEquals(Set.of(HugeRtePlugin.LINK), copy.plugins());
	}
}
