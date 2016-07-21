/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.input.events;

/**
 * holds events
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public enum EventType
{
	dbclick, click, focus, blur, change, submit, onclick;

	private String eventType;

	private EventType()
	{
		eventType = name();

	}

	public String getEvent()
	{
		return eventType;
	}

}
