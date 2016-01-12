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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;

/**
 * Represents a composite behavior allowing the user to attach multiple behaviors to a component at
 * once.
 * 
 * @author David Bernard
 * @author Erik Brakkee
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 * @see http://cwiki.apache.org/WICKET/composite-behaviors.html
 */
public class CompositeBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	private final Iterable<Behavior> behaviors_;

	public CompositeBehavior(final Behavior... behaviors)
	{
		this(Arrays.asList(behaviors));
	}

	public CompositeBehavior(final Iterable<Behavior> behaviors)
	{
		behaviors_ = behaviors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterRender(final Component arg0)
	{
		for (final Behavior behavior : behaviors_)
			behavior.afterRender(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeRender(final Component arg0)
	{
		for (final Behavior behavior : behaviors_)
			behavior.beforeRender(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(final Component arg0)
	{
		for (final Behavior behavior : behaviors_)
			behavior.bind(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void detach(final Component arg0)
	{
		for (final Behavior behavior : behaviors_)
			behavior.detach(arg0);
	}

	public void exception(final Component component, final RuntimeException aException)
	{
		for (final Behavior behavior : behaviors_)
			behavior.onException(component, aException);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getStatelessHint(final Component arg0)
	{
		boolean back = true;
		for (final Behavior behavior : behaviors_)
			back = back && behavior.getStatelessHint(arg0);
		return back;
	}

	@Override
	public boolean isEnabled(final Component arg0)
	{
		boolean back = true;
		for (final Behavior behavior : behaviors_)
			back = back && behavior.isEnabled(arg0);
		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTemporary(final Component component)
	{
		boolean back = true;
		for (final Behavior behavior : behaviors_)
			back = back && behavior.isTemporary(component);
		return back;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onComponentTag(final Component aComponent, final ComponentTag aTag)
	{
		for (final Behavior behavior : behaviors_)
			behavior.onComponentTag(aComponent, aTag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		super.renderHead(component, response);
		for (final Behavior behavior : behaviors_)
			behavior.renderHead(component, response);
	}
}
