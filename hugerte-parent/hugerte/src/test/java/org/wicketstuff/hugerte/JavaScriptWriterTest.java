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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class JavaScriptWriterTest
{
	@Test
	void escapesQuotesBackslashesAndControlCharacters()
	{
		assertEquals("\"a\\\"b\\\\c\\nd\\te\\u0001\"", JavaScriptWriter.write("a\"b\\c\nd\te\u0001"));
	}

	@Test
	void escapesCharactersThatEndOrChangeAScriptElement()
	{
		assertEquals("\"\\u003c/script\\u003e\\u003c!--\\u0026\\u0027\"",
			JavaScriptWriter.write("</script><!--&'"));
	}

	@Test
	void escapesJavaScriptLineTerminators()
	{
		assertEquals("\"\\u2028\\u2029\"", JavaScriptWriter.write("\u2028\u2029"));
	}

	@Test
	void writesNestedValuesInOrder()
	{
		var map = new LinkedHashMap<String, Object>();
		map.put("b", List.of(1, 2.5, true));
		map.put("a", Map.of("x", "y"));
		map.put("n", new BigDecimal("1.50"));
		assertEquals("{\"b\":[1,2.5,true],\"a\":{\"x\":\"y\"},\"n\":1.50}",
			JavaScriptWriter.write(map));
	}

	@Test
	void writesRawJavaScriptUnchanged()
	{
		assertEquals("{\"setup\":function (e) { return '<b>'; }}", JavaScriptWriter
			.write(Map.of("setup", new RawJavaScript("function (e) { return '<b>'; }"))));
	}

	@Test
	void resolvesUrlsLikeABrowser()
	{
		String root = "/ctx/app/";
		String page = "admin/articles/edit?0";
		assertEquals("/ctx/app/wicket/resource/a.css",
			JavaScriptWriter.resolve(root, page, "../../wicket/resource/a.css"));
		assertEquals("/ctx/css/editor.css?v=1#main",
			JavaScriptWriter.resolve(root, page, "../../../css/editor.css?v=1#main"));
		assertEquals("/ctx/app/admin/articles/edit?action=upload",
			JavaScriptWriter.resolve(root, page, "?action=upload"));
		assertEquals("/ctx/app/admin/articles/edit?0#top",
			JavaScriptWriter.resolve(root, page, "#top"));
		assertEquals("/ctx/app/simple/a.css", JavaScriptWriter.resolve(root, "//simple/x", "a.css"));
		assertEquals("/static/a.css", JavaScriptWriter.resolve(root, page, "/static/a.css"));
		assertEquals("https://cdn.example.com/a.css",
			JavaScriptWriter.resolve(root, page, "https://cdn.example.com/a.css"));
		assertEquals("//cdn.example.com/a.css",
			JavaScriptWriter.resolve(root, page, "//cdn.example.com/a.css"));
	}

	@Test
	void rejectsUnsupportedTypes()
	{
		assertThrows(IllegalArgumentException.class, () -> JavaScriptWriter.write(new Object()));
	}
}
