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
package org.wicketstuff.dojo11.markup.html.toaster;

import org.wicketstuff.dojo11.AbstractDojoWidgetBehavior;

/**
 * A dojo Toaster. allow to show a message in a toaster in a screen corner
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
@SuppressWarnings("serial")
public class DojoToasterHandler extends AbstractDojoWidgetBehavior
{

	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	public void setRequire(RequireDojoLibs libs){
		super.setRequire(libs);
		libs.add("dojox.widget.Toaster");
	}
}
