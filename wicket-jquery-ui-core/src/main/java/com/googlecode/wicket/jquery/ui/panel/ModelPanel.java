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
package com.googlecode.wicket.jquery.ui.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Provides a {@link Panel} with a generized model
 *  
 * @param <T> the model object type
 * @author Sebastien Briquet - sebastien@7thweb.net
 */
public class ModelPanel<T> extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public ModelPanel(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public ModelPanel(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Gets the model
	 * @return the model
	 */
	@SuppressWarnings("unchecked")
	public IModel<T> getModel()
	{
		return (IModel<T>) this.getDefaultModel();
	}
	
	/**
	 * Sets the model
	 * @param model the model
	 */
	public void setModel(IModel<T> model)
	{
		this.setDefaultModel(model);
	}
	
	/**
	 * Gets the model object
	 * @return the model object
	 */
	@SuppressWarnings("unchecked")
	public T getModelObject()
	{
		return (T) this.getDefaultModelObject();
	}

	/**
	 * Sets the model object
	 * @param object the model object
	 */
	public void setModelObject(String object)
	{
		this.setDefaultModelObject(object);
	}

}
