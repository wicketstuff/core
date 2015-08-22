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

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.util.string.Strings;

/**
 * Hides a component if the component's model value is empty or null.
 */
public class HideWhenEmptyOrNullBehavior extends Behavior {

    private static final Behavior INSTANCE = new HideWhenEmptyOrNullBehavior();

    /**
	 * Returns the instance of the behavior.
	 *
	 * @return instance of the behavior
	 */
    public static Behavior get() {
        return INSTANCE;
    }

    /**
     * Private constructor.
     */
    private HideWhenEmptyOrNullBehavior() {
        super();
    }

    @Override
    public void onConfigure(final Component component) {
        super.onConfigure(component);
		Object dependentValue = component.getDefaultModelObject();
		boolean visible;
		if (dependentValue instanceof Collection)
		{
			final Collection<?> c = (Collection<?>)dependentValue;
			visible = !c.isEmpty();
		}
		else if (dependentValue instanceof CharSequence)
		{
			visible = !Strings.isEmpty((CharSequence)dependentValue);
		}
		else
		{
			visible = dependentValue != null;
		}
		component.setVisible(visible);
    }

}
