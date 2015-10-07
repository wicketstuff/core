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

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Used to provide a representation by the given theme
 * 
 * @author Tobias Soloschenko
 *
 */
public class SelectizeCssResourceReference extends CssResourceReference
{

	private static final long serialVersionUID = 1L;

	public enum Theme
	{
		/**
		 * none theme
		 */
		NONE,
		/**
		 * defaul theme
		 */
		DEFAULT,
		/**
		 * Legacy theme
		 */
		LEGACY,
		/**
		 * Theme for bootstrap 2
		 */
		BOOTSTRAP2,
		/**
		 * Theme for bootstrap 3
		 */
		BOOTSTRAP3
	}

	/**
	 * Singleton instance of this reference
	 */
	private static SelectizeCssResourceReference INSTANCE = null;

	/**
	 * @param Theme
	 *            the theme of the selectize component
	 * @return the single instance of the resource reference
	 */
	public static SelectizeCssResourceReference instance(Theme theme)
	{
		if (INSTANCE == null)
		{
			switch (theme)
			{
				case DEFAULT :
					INSTANCE = new SelectizeCssResourceReference(
						"res/selectize/css/selectize.default.css");
					break;
				case LEGACY :
					INSTANCE = new SelectizeCssResourceReference(
						"res/selectize/css/selectize.legacy.css");
					break;
				case BOOTSTRAP2 :
					INSTANCE = new SelectizeCssResourceReference(
						"res/selectize/css/selectize.bootstrap2.css");
					break;
				case BOOTSTRAP3 :
					INSTANCE = new SelectizeCssResourceReference(
						"res/selectize/css/selectize.bootstrap3.css");
					break;
				case NONE :
				default :
					INSTANCE = new SelectizeCssResourceReference("res/selectize/css/selectize.css");
					break;
			}
		}
		return INSTANCE;
	}

	/**
	 * Private constructor.
	 */
	private SelectizeCssResourceReference(String path)
	{
		super(SelectizeCssResourceReference.class, path);
	}
}
