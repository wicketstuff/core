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

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

@SuppressWarnings("serial")
public class AttributeAppenderPlus extends AttributeAppender {

    private String prefix_;
    private String suffix_;

    public AttributeAppenderPlus(String attribute, boolean addAttributeIfNotPresent, IModel appendModel, String separator, String prefix, String suffix) {
        super(attribute, addAttributeIfNotPresent, appendModel, separator);
        prefix_ = prefix;
        suffix_ = suffix;
    }

    public AttributeAppenderPlus(String attribute, IModel appendModel, String separator, String prefix, String suffix) {
        this(attribute, true, appendModel, separator, prefix, suffix);
    }

    @Override
    protected String newValue(String currentValue, String appendValue) {
        if (!Strings.isEmpty(appendValue)) {
            appendValue = prefix_ + appendValue + suffix_;
        }
        return super.newValue(currentValue, appendValue);
    }
}
