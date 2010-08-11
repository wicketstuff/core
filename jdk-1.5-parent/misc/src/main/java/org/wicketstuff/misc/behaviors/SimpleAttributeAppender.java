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
package org.wicketstuff.misc.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

/**
 *
 * @author dwayne
 */
@SuppressWarnings("serial")
public class SimpleAttributeAppender extends SimpleAttributeModifier {

    private CharSequence separator_;

    public SimpleAttributeAppender(String attribute, CharSequence value, CharSequence separator) {
        super(attribute, value);
        separator_ = separator;
    }

    /**
     * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
     *      org.apache.wicket.markup.ComponentTag)
     */
    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        if (isEnabled(component) && !Strings.isEmpty(getValue())) {
            CharSequence newValue = tag.getAttributes().getString(getAttribute());
            if (Strings.isEmpty(newValue)) {
                newValue = getValue();
            } else {
                newValue = new StringBuilder(getValue()).append(separator_).append(newValue);
            }
            tag.getAttributes().put(getAttribute(), newValue);
        }
    }
}