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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

/**
 * Deprecated, because superclass is deprecated too.
 * 
 * @author dwayne
 */
@Deprecated
public class SimpleAttributeAppender extends SimpleAttributeModifier
{
	private static final long serialVersionUID = 1L;

	private final CharSequence separator_;

	public SimpleAttributeAppender(final String attribute, final CharSequence value,
		final CharSequence separator)
	{
		super(attribute, value);
		separator_ = separator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onComponentTag(final Component component, final ComponentTag tag)
	{
		if (isEnabled(component) && !Strings.isEmpty(getValue()))
		{
			final String newValue = tag.getAttributes().getString(getAttribute());
			tag.getAttributes().put(getAttribute(), //
				Strings.isEmpty(newValue) ? //
					getValue() : //
					new StringBuilder(getValue()).append(separator_).append(newValue) //
				);
		}
	}
}