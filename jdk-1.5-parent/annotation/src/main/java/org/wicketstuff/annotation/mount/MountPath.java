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
package org.wicketstuff.annotation.mount;

import java.lang.annotation.*;

/**
 * Specify the primary and alternate paths to mount a Page.  If this annotation
 * is used by itself, the default encoding strategy is used (see
 * {@link org.wicketstuff.annotation.scan.AnnotatedMountScanner#getDefaultStrategy}).
 *
 * <p>If you do not wish to use the default strategy, you also need to annotate the
 * page with an annotation that defines which strategy to use.  Those annotations are
 * annotated with {@link MountDefinition}.
 *
 * <p>A good example is {@link org.wicketstuff.annotation.strategy.MountMixedParam}.
 *
 * <p> The primary mount path is listed ahead of alternate paths in any list returned
 * by {@link org.wicketstuff.annotation.scan.AnnotatedMountScanner}.  This is done because
 * of the implementation of
 * {@link org.apache.wicket.protocol.http.request.WebRequestCodingStrategy#getMountEncoder(org.apache.wicket.IRequestTarget)}
 * getMountEncoder() returns the first mount that matches the given Page.  Thus, when determining which path to
 * mount a page on, it always picks the first one found.
 * @author Doug Donohoe
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MountPath
{
    /**
     * @return primary mount path
     */
    String path();

    /**
     * @return alternate mount paths
     */
    String[] alt() default {};
}
