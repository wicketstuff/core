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
package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.openlayers.js.JSUtils;

public class Vector extends Layer implements Serializable
{

	private static final long serialVersionUID = 1L;
	private HashMap<String, String> options = new HashMap<String, String>();

	public Vector(String name)
	{
		this(name, null);
	}

	public Vector(String name, HashMap<String, String> options)
	{
		super();
		setName(name);
		this.options = options;
	}


	@Override
	protected void bindHeaderContributors(IHeaderResponse response)
	{
		// mocleiri: intentionally does nothing

	}

	@Override
	public String getJSconstructor()
	{

		String options = super.getJSOptionsMap(this.options);

		return super.getJSconstructor("OpenLayers.Layer.Vector",
			Arrays.asList(JSUtils.getQuotedString(getName()), options));

	}
}
