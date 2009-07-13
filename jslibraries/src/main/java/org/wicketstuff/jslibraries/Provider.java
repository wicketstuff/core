/**
 * Copyright (C) 2009 Uwe Schaefer <uwe@codesmell.de>
 *
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
package org.wicketstuff.jslibraries;

import org.apache.wicket.behavior.HeaderContributor;

/**
 * A Provider is an optional means of overriding the HeaderContributor created
 * by JSLib. <br/>
 * If you use <code>
 * JSLib.getHeaderContributor(versionDescriptor, provider[])</code> ,
 * these providers get the opportunity to provide a HeaderContributor first. If
 * it returns null, the requested lib will be served from the application
 * instead (if defined).
 * 
 * @author Uwe Schäfer, (uwe@codesmell.de)
 */
public interface Provider {

	/**
	 * @param versionDescriptor
	 * @param productionHint
	 *            if true, tries to use minimized version of script if available
	 * @return HeaderContributor to be added to component, or null if
	 *         unavailable
	 */
	public HeaderContributor getHeaderContributor(
			VersionDescriptor versionDescriptor, boolean productionHint);

}
