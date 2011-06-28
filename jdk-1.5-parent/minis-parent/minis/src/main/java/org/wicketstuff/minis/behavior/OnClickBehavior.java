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
package org.wicketstuff.minis.behavior;

import java.util.Arrays;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.Behavior;

public class OnClickBehavior extends CompositeBehavior
{
	private static final long serialVersionUID = 1L;

	public OnClickBehavior(final CharSequence url)
	{
		super(Arrays.asList(new Behavior[] { AttributeModifier.replace("class", "clickable"),
				AttributeModifier.replace("onClick", "document.location.href='" + url + "';") }));
	}
	// onMouseOver="this.bgColor = '#C0C0C0'"
	// onMouseOut
}
