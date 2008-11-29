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
package org.wicketstuff.misc;

import java.util.Map;

import org.apache.wicket.request.target.coding.MixedParamUrlCodingStrategy;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.value.ValueMap;

/**
 * Fix a bug in MixedParamUrlCodingStrategy.decodeParameters, should be fix in wicket 1.3 final => deprecated.
 */
@SuppressWarnings("unchecked")
@Deprecated
public class MyMixedParamUrlCodingStrategy extends MixedParamUrlCodingStrategy {

    public MyMixedParamUrlCodingStrategy(String mountPath, Class bookmarkablePageClass, String[] parameterNames) {
        super(mountPath, bookmarkablePageClass, parameterNames);
    }

    @Override
    public void appendParameters(AppendingStringBuffer url, Map parameters) {
        super.appendParameters(url, parameters);
    }

    @Override
    public ValueMap decodeParameters(String urlFragment, Map urlParameters) {
        ValueMap back = super.decodeParameters(urlFragment, urlParameters);
        for (String key : back.keySet()) {
            back.put(key, urlDecode(back.getString((String)key)));
        }
        return back;
    }
}