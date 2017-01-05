/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.selectize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.json.JSONObject;

/**
 * The SelectizeResponse is the actual model object for ajax requests of the selectize component.
 * 
 * @author Tobias Soloschenko
 *
 */
public class SelectizeResponse extends JSONObject
{

	public SelectizeResponse(List<SelectizeOption> options)
	{
		put("options", options);
	}

	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.writeObject(toString());
	}

	@SuppressWarnings("rawtypes")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		String read = (String)in.readObject();
		JSONObject jsonObject = new JSONObject(read);
		Iterator iterator = jsonObject.keySet().iterator();
		while (iterator.hasNext())
		{
			Object key = iterator.next();
			this.put((String)key, jsonObject.get((String)key));
		}
	}
}
