/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.select2;

import java.io.Serializable;

import com.github.openjson.JSONException;
import com.github.openjson.JSONStringer;
import org.wicketstuff.select2.json.Json;

/**
 * Select2 Ajax settings. Refer to the Select2 documentation for what these options mean.
 * 
 * @author igor
 */
public final class AjaxSettings implements Serializable
{
	private static final long serialVersionUID = 1L;
	private CharSequence url;
	private String dataType = "json";
	private int delay = 100;
	private String data;
	private String processResults;
	/** whether or not to use traditional parameter encoding. */
	private boolean cache;

	public void toJson(JSONStringer stringer) throws JSONException
	{
		stringer.object();
		Json.writeFunction(stringer, "data", data);
		Json.writeObject(stringer, "dataType", dataType);
		Json.writeObject(stringer, "delay", delay);
		Json.writeFunction(stringer, "processResults", processResults);
		Json.writeObject(stringer, "url", url);
		Json.writeObject(stringer, "cache", cache);
		stringer.endObject();
	}

	public void setUrl(CharSequence url)
	{
		this.url = url;
	}

	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	public void setDelay(int delay)
	{
		this.delay = delay;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public void setProcessResults(String processResults)
	{
		this.processResults = processResults;
	}

	public CharSequence getUrl()
	{
		return url;
	}

	public String getDataType()
	{
		return dataType;
	}

	public int getDelay()
	{
		return delay;
	}

	public String getData()
	{
		return data;
	}

	public String getProcessResults()
	{
		return processResults;
	}

	public boolean isCache()
	{
		return cache;
	}

	public void setCache(boolean cache)
	{
		this.cache = cache;
	}

}
