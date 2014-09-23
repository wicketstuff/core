/* Copyright (c) 2013 Martin Knopf
 * 
 * Licensed under the MIT license;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://opensource.org/licenses/MIT
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jwicket.ui.tooltip;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Martin Knopf
 * 
 */
public class TestJQueryUiWidget
{

	private JQueryUiWidget widget;

	@Before
	public void setUp() throws Exception
	{
		this.widget = new JQueryUiWidget("tooltip");
	}

	@Test
	public void shouldBuildJsWithOneOption()
	{
		this.widget.setOption("content", "'hello world'");

		assertEquals("$('.selector').tooltip({content:'hello world'});",
			this.widget.buildJS("'.selector'"));
	}

	@Test
	public void shouldBuildJsWithTwoOptions()
	{
		this.widget.setOption("items", "'.items'");
		this.widget.setOption("content", "'hello world'");

		assertEquals("$('.selector').tooltip({content:'hello world',items:'.items'});",
			this.widget.buildJS("'.selector'"));
	}

	@Test
	public void shouldBuildJsWithOneEventHandler()
	{
		this.widget.setEventHandler("open", "function(){NOP;}");

		assertEquals("$('.selector').tooltip({open:function(){NOP;}});",
			this.widget.buildJS("'.selector'"));
	}

	@Test
	public void shouldBuildJsWithTwoEventHandlers()
	{
		this.widget.setEventHandler("close", "function(){close}");
		this.widget.setEventHandler("open", "function(){open}");

		assertEquals("$('.selector').tooltip({open:function(){open},close:function(){close}});",
			this.widget.buildJS("'.selector'"));
	}

	@Test
	public void shouldBuildJsWithOptionsAndEventHandlers()
	{
		this.widget.setOption("items", "'.items'");
		this.widget.setOption("content", "'hello world'");
		this.widget.setEventHandler("close", "function(){close}");
		this.widget.setEventHandler("open", "function(){open}");

		assertEquals(
			"$('.selector').tooltip({content:'hello world',items:'.items',open:function(){open},close:function(){close}});",
			this.widget.buildJS("'.selector'"));
	}

	@Test
	public void shouldReturnSetOption()
	{
		this.widget.setOption("content", "test conent");

		assertEquals("test conent", this.widget.getOption("content"));
	}

	@Test
	public void shouldReturnSetEventHandler()
	{
		this.widget.setEventHandler("close", "function(){close}");

		assertEquals("function(){close}", this.widget.getEventHandler("close"));
	}
}
