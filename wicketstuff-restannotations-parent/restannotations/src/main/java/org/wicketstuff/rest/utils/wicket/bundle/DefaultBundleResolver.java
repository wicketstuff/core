/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.utils.wicket.bundle;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.resource.loader.IStringResourceLoader;
import org.apache.wicket.validation.IErrorMessageSource;
import org.wicketstuff.rest.resource.AbstractRestResource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Wicket bundle resolver that relies on the default
 * {@link IStringResourceLoader}S. Its constructor requires an subclass of
 * {@link AbstractRestResource} which is used to resolve custom bundles.
 *
 * @author andrea del bene
 *
 */
public class DefaultBundleResolver implements IErrorMessageSource
{
    private final List<Class<?>> targetClasses;

    public DefaultBundleResolver(Class<?>... targetClasses)
    {
        this.targetClasses = Collections.unmodifiableList(Arrays.asList(targetClasses));
    }

    public DefaultBundleResolver(List<Class<?>> targetClasses)
    {
        this.targetClasses = Collections.unmodifiableList(targetClasses);
    }

    @Override
    public String getMessage(String key, Map<String, Object> vars)
    {
        String resourceValue = null;
        List<IStringResourceLoader> resourceLoaders = Application.get().getResourceSettings()
                .getStringResourceLoaders();
        Locale locale = Session.get().getLocale();
        String style = Session.get().getStyle();

        outerloop: for (IStringResourceLoader stringResourceLoader : resourceLoaders)
        {
            for(Class<?> clazz : targetClasses)
            {
                resourceValue = stringResourceLoader.loadStringResource(clazz, key, locale,
                        style, null);

                if (resourceValue != null)
                {
                    break outerloop;
                }
            }
        }

        StringConverterInterpolator interpolator = new StringConverterInterpolator(
                resourceValue != null ? resourceValue : "", vars, false, locale);

        return interpolator.toString();
    }
}
