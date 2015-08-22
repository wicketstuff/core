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

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

/**
 * @author David Bernard
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class AttributeAppenderPlus extends AttributeAppender
{
	private static final long serialVersionUID = 1L;

	private final String prefix_;
	private final String suffix_;

	public AttributeAppenderPlus(final String attribute, final IModel<?> appendModel,
		final String separator, final String prefix, final String suffix)
	{
		super(attribute, appendModel, separator);
		prefix_ = prefix;
		suffix_ = suffix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String newValue(final String currentValue, final String appendValue)
	{
		return super.newValue(currentValue, Strings.isEmpty(appendValue) ? appendValue : prefix_ +
			appendValue + suffix_);
	}
}
