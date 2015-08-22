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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds JavsScript for jQuery UI widgets based on configured options and event handlers.
 * 
 * @author Martin Knopf
 * 
 */
public class JQueryUiWidget implements Serializable
{

	private final String widgetName;
	private final Map<String, String> options;
	private final Map<String, String> events;

	public JQueryUiWidget(String widgetName)
	{
		this.widgetName = widgetName;
		this.options = new HashMap<String, String>();
		this.events = new HashMap<String, String>();
	}

	public JQueryUiWidget setOption(String optionKey, String optionValue)
	{
		this.options.put(optionKey, optionValue);
		return this;
	}

	public JQueryUiWidget setEventHandler(String eventKey, String eventValue)
	{
		this.events.put(eventKey, eventValue);
		return this;
	}

	public String buildJS(String selector)
	{
		StringBuilder configBuilder = new StringBuilder().append("{");
		for (String key : this.options.keySet())
		{
			configBuilder.append(String.format(",%s:%s", key, this.options.get(key)));
		}
		for (String key : this.events.keySet())
		{
			configBuilder.append(String.format(",%s:%s", key, this.events.get(key)));
		}
		String configJsObject = configBuilder.append("}").toString().replaceFirst(",", "");

		return String.format("$(%s).%s(%s);", selector, this.widgetName, configJsObject);
	}

	public Object getOption(String option)
	{
		return this.options.get(option);
	}

	public Object getEventHandler(String event)
	{
		return this.events.get(event);
	}

}
