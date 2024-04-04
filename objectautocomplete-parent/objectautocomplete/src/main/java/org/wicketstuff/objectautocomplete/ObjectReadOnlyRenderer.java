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
package org.wicketstuff.objectautocomplete;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * A renderer for rendering the readonly view of an object.
 * 
 * @author roland
 * @since May 29, 2008
 */
public interface ObjectReadOnlyRenderer<I> extends Serializable
{

	/**
	 * Get the component used for rendering the read only view when an object has been selected.
	 * 
	 * @param id
	 *            wicket id of object
	 * @param pModel
	 *            the model holding the selected objects id
	 * @param pSearchTextModel
	 *            the string used during selection
	 * @return a component which is used as as readonly view.
	 */
	Component getObjectRenderer(String id, IModel<I> pModel, IModel<String> pSearchTextModel);
}
