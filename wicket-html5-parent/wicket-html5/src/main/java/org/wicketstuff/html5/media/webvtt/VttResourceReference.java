/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.html5.media.webvtt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.Streams;

/**
 * Generates the VTT content by the given VTTGen. The content might be internationalized with
 * Wicket's i18n functionality.
 * 
 * @author Tobias Soloschenko
 */
public abstract class VttResourceReference extends ResourceReference
{

	private static final long serialVersionUID = 1L;

	public VttResourceReference(String name)
	{
		super(name);
	}

	@Override
	public IResource getResource()
	{
		return new AbstractResource()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected ResourceResponse newResourceResponse(Attributes attributes)
			{
				ResourceResponse resourceResponse = new ResourceResponse();
				resourceResponse.setContentType("text/vtt");
				resourceResponse.setTextEncoding("UTF-8");
				resourceResponse.setWriteCallback(new WriteCallback()
				{
					@Override
					public void writeData(Attributes attributes) throws IOException
					{
						OutputStream outputStream = attributes.getResponse().getOutputStream();
						Streams.copy(new ByteArrayInputStream(getWebVtt().create().getBytes()),
							outputStream);
					}
				});
				return resourceResponse;
			}
		};
	}

	/**
	 * Used to get the VTTGen to created the content with
	 * 
	 * @return the VTTGen to create the VTT content with
	 */
	public abstract WebVtt getWebVtt();

}
