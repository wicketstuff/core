/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar
 * 2006) eelco12 $ $Revision: 5004 $ $Date: 2006-03-17 20:47:08 -0800 (Fri, 17
 * Mar 2006) $
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
package wicket.contrib.tinymce.settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.util.resource.StringBufferResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;
import com.github.openjson.JSONTokener;
import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

/**
 * Wicket web resource that acts as backend spell checker for tinymce component.
 * 
 * @author ivaynberg
 * @author Iulian Costan (iulian.costan@gmail.com)
 * @author Boris Goldowsky
 */
class JazzySpellChecker extends AbstractResource
{
	protected static final String dictFile = "wicket/contrib/tinymce/jazzy/english.0";

	private SpellDictionary dict;

	private StringBufferResourceStream resourceStream;

	protected static final String contentType = "application/json";

	private static final Logger LOG = LoggerFactory.getLogger(JazzySpellChecker.class);

	private static final long serialVersionUID = 1L;

	/**
	 * Construct spell checker resource.
	 */
	public JazzySpellChecker()
	{
		// todo load dict file from jazzy.jar archive.
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dictFile);
		InputStreamReader reader = new InputStreamReader(inputStream);
		try
		{
			dict = new SpellDictionaryHashMap(reader);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		resourceStream = new StringBufferResourceStream(contentType);
	}

	// /**
	// * Read the Request and do the processing needed to construct a response.
	// * Why here? configureResponse is one of the few Resource methods that is
	// * called only once per Request. Running the spellcheck anew each time
	// * getResourceStream is called, for example, would be wasteful.
	// *
	// * @see org.apache.wicket.Resource#configureResponse(Response response)
	// */
	@Override
	protected ResourceResponse newResourceResponse(Attributes attributes)
	{
		buildResourceStream();
		if (attributes.getResponse() instanceof WebResponse)
			((WebResponse)attributes.getResponse()).setHeader("Cache-Control",
				"no-cache, must-revalidate");
		ResourceResponse resourceResponse = new ResourceResponse();
		resourceResponse.setWriteCallback(new WriteCallback()
		{
			@Override
			public void writeData(Attributes attributes)
			{
				attributes.getResponse().write(resourceStream.asString());
			}
		});
		return resourceResponse;
	}

	//
	// /**
	// * @see org.apache.wicket.Resource#getResourceStream()
	// */
	// public IResourceStream getResourceStream()
	// {
	// return resourceStream;
	// }

	/**
	 * Read the Request and construct an appropriate ResourceStream to respond with.
	 */
	public void buildResourceStream()
	{
		JSONObject json;
		String cmd = null, id = null;
		JSONArray paramArray = null;

		HttpServletRequest req = ((ServletWebRequest)RequestCycle.get().getRequest()).getContainerRequest();
		BufferedReader reader = null;
		try
		{
			ServletInputStream sis = req.getInputStream();
			reader = new BufferedReader(new InputStreamReader(sis, "UTF-8"));
			// Used for debugging:
			// reader.mark(10);
			// if (reader.read() == -1) {
			// LOG.error("No request seen");
			// }
			// reader.reset();

			json = new JSONObject(new JSONTokener(reader));
			// LOG.debug("JSON Object: {}", json);

			id = json.getString("id");
			cmd = json.getString("method");
			paramArray = json.getJSONArray("params");

		}
		catch (IOException e)
		{
			jsonError("I/O exception while parsing");
		}
		catch (JSONException e)
		{
			jsonError("Could not parse command");
		}
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (paramArray == null)
		{
			handleEmptyCheckList(cmd, id);
		}
		else if ("checkWords".equals(cmd))
		{
			doSpell(cmd, id, paramArray);
		}
		else if ("getSuggestions".equals(cmd))
		{
			doSuggest(cmd, id, paramArray);
		}
		else
		{
			jsonError("Unknown command");
		}

		// LOG.debug("Processed command {}; output will be {}", cmd,
		// resourceStream.asString());
	}


	private void handleEmptyCheckList(final String cmd, final String id)
	{
		respond(null, cmd, id);
	}

	@SuppressWarnings("unchecked")
	private void doSuggest(final String cmd, final String id, final JSONArray paramArray)
	{
		final SpellChecker checker = new SpellChecker(dict);
		String word = paramArray.optString(1);
		List<String> suggestions = checker.getSuggestions(word, 2);

		respond(suggestions.iterator(), cmd, id);
	}

	private void doSpell(final String cmd, final String id, final JSONArray paramArray)
	{
		final SpellChecker checker = new SpellChecker(dict);

		final Set<String> errors = new HashSet<String>();

		checker.addSpellCheckListener(new SpellCheckListener()
		{
			public void spellingError(SpellCheckEvent event)
			{
				errors.add(event.getInvalidWord());
			}
		});

		JSONArray words = paramArray.optJSONArray(1);
		checker.checkSpelling(new StringWordTokenizer(words.toString()));
		respond(errors.iterator(), cmd, id);
	}

	private void respond(Iterator<String> words, String cmd, String id)
	{
		JSONArray array = new JSONArray();
		if (words != null)
		{
			while (words.hasNext())
				array.put(words.next());
		}

		JSONObject response = new JSONObject();
		try
		{
			response.put("id", id);
			response.put("error", (String)null);
			response.put("result", array);
			setResponse(response.toString());
		}
		catch (JSONException e)
		{
			jsonError("Failed to construct response");
		}
	}

	private void setResponse(String response)
	{
		resourceStream.clear();
		resourceStream.append(response);
		resourceStream.setLastModified(Instant.now());
	}

	// Simple method of returning a user-visible error message to TinyMCE
	private void jsonError(String message)
	{
		setResponse("{\"error\":\"" + message + "\"}");
		LOG.debug("Error message return from RPC call: {}", message);
	}

}
