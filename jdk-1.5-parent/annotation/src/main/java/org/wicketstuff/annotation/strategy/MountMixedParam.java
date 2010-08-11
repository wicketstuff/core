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
package org.wicketstuff.annotation.strategy;

import org.apache.wicket.request.target.coding.*;
import org.wicketstuff.annotation.mount.*;

import java.lang.annotation.*;

/**
 * Annotation to use for mounting a page using {@link MixedParamUrlCodingStrategy}.
 *
 * <p>Uses the {@link MixedParamUrlCodingStrategy#MixedParamUrlCodingStrategy(String mountPath,
 * Class bookmarkablePageClass, String pageMapName, String[] parameterNames)} constructor.
 *
 * @author Doug Donohoe
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@MountDefinition(strategyClass = MixedParamUrlCodingStrategy.class, argOrder = {"pageMapName", "parameterNames"})
@Inherited
@Documented
public @interface MountMixedParam
{
    /**
     * @return pageMapName constructor parameter.  Default is null.
     */
    String pageMapName() default MountDefinition.NULL;

    /**
     * @return parameterNames constructor parameter
     */
    String[] parameterNames();
}
