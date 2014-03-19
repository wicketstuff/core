/*
 * $Id: AutocompleteTextField.java 528 2006-01-08 04:14:46 -0800 (Sun, 08 Jan
 * 2006) jdonnerstag $ $Revision: 1546 $ $Date: 2006-01-08 04:14:46 -0800 (Sun,
 * 08 Jan 2006) $
 *
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.scriptaculous.autocomplete;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Autocomplete behavior that provides a static list of options to autocomplete.
 *
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 * @see http://wiki.script.aculo.us/scriptaculous/show/Autocompleter.Local
 */
public class AutocompleteBehavior extends AbstractAutocompleteBehavior
{
	private static final long serialVersionUID = 1L;
	private final String[] results;

	public AutocompleteBehavior(String[] results)
	{
		this.results = results;
	}

	@Override
	protected String getAutocompleteType()
	{
		return "Autocompleter.Local";
	}

	@Override
	protected String getThirdAutocompleteArgument()
	{
		return buildResults();
	}

	private String buildResults()
	{
		String result = "[";
		for (int x = 0; x < results.length; x++) {
			String value = results[x];
			result += "'" + value + "'";
			if (x < results.length - 1)
			{
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		//do nothing
	}
}
