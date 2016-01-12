/*
 *  Copyright 2012 inaiat.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.lightbox2.references;

import org.apache.wicket.request.resource.CssResourceReference;

public class LightboxCssResourceReference extends CssResourceReference
{

	private static final long serialVersionUID = 242982571994986917L;

	private static final LightboxCssResourceReference INSTANCE = new LightboxCssResourceReference();

	public static LightboxCssResourceReference get()
	{
		return INSTANCE;
	}

	private LightboxCssResourceReference()
	{
		super(LightboxCssResourceReference.class, "css/lightbox.css");
	}
}
