/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.resource.StringResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A resource that serves JSON for stateless . You have to mount at application init like. E.g.
 *
 * <p>
 * <pre>
 * 	mountResource(CATEGORIES_JSON, new JsonResourceReference<CategoryTranslation>("categories") {
 *
 * 			private static final long serialVersionUID = 1L;
 *
 * 			protected ChoiceProvider<CategoryTranslation> getChoiceProvider() {
 * 				return CategoriesTextChoiceProvider.getInstance();
 * 			}
 * 		});
 *
 * </pre>
 * </p>
 *
 * and then on component you add
 *
 * <p>
 *  <pre>
 *  	Select2MultiChoice<CategoryTranslation> c = new Select2MultiChoice<CategoryTranslation>("categories", new PropertyModel&lt;Collection&lt;CategoryTranslation&gt;&gt;(searchBean, "translations"), CategoriesTextChoiceProvider.getInstance());
 *  	c.getSettings().setStateless(true);
 *  	c.getSettings().setMountPath(Application.CATEGORIES_JSON);
 *  </pre>
 * <p>
 *
 * @author Ernesto Reinaldo Barreiro (reiern70@mail.com).
 */
public abstract class JsonResourceReference<T> extends ResourceReference
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(JsonResourceReference.class);

	public JsonResourceReference(String name)
	{
		super(JsonResourceReference.class, name);
	}

	@Override
	public IResource getResource()
	{
		ByteArrayOutputStream webResponse = new ByteArrayOutputStream();
		AbstractSelect2Choice.generateJSON(getChoiceProvider(), webResponse);
		StringResourceStream resourceStream;
		try
		{
			resourceStream = new StringResourceStream(webResponse.toString("UTF-8"), "application/json");
		}
		catch (UnsupportedEncodingException e)
		{
			logger.error("getResource", e);
			throw new WicketRuntimeException(e);
		}
		return new ResourceStreamResource(resourceStream);
	}

	/**
	 * The choice provider.
	 *
	 * @return The ChoiceProvider
	 */
	protected abstract ChoiceProvider<T> getChoiceProvider();

}
