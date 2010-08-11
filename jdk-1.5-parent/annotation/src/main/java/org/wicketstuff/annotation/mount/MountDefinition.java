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

import org.apache.wicket.request.target.coding.*;

import java.lang.annotation.*;

/**
 * An annotation used to define basic information about a mount
 * annotation:
 *
 * <ul>
 * <li> UrlCodingStrategy class to use </li>
 * <li> Order to pass the mount annotations values to the constructor </li>
 * </ul>
 *
 * <p>Note:  Even though it doesn't show up in the Javadoc, if you have
 * a mount value that you would like to default to null, you can
 * specify <tt>default MountDefinition.NULL</tt> in the annotation definition.
 *
 * <p> {@link org.wicketstuff.annotation.strategy.MountMixedParam} is a good
 * example of the intended use of this annotation.  That Annotation defines
 * how to mount a page using {@link MixedParamUrlCodingStrategy}.
 *
 * <p>Note that
 * the {@link org.wicketstuff.annotation.strategy.MountMixedParam}
 * annotation also is marked as {@link Inherited} so that Pages which
 * subclass a Page annotated with MountMixedParam also inherit the definition.
 *
 * <p>Note that {@link MountPath} is not inherited as you typically want different
 * pages mounted at different locations.
 *
 * @author Doug Donohoe
 * @see org.wicketstuff.annotation.strategy.MountMixedParam MixedParam annotation for a specific example
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MountDefinition
{
    /**
     * In annotation we are marking, used to indicate a return value of null
     * since it is not possible to specify 'default null' with Java annotations.
     * Specify 'default MountDefinition.NULL' instead.
     */
    String NULL = "[null]";

    /**
     * @return a class that implements {@link IRequestTargetUrlCodingStrategy}
     */
    Class<? extends IRequestTargetUrlCodingStrategy> strategyClass();

    /**
     * In annotation we are marking, the order that the annotation's values
     * should be passed into the strategyClass's constructor as supplemental
     * constructor arguments after the default first two of String (mount path)
     * and Class (Page class).
     * <p>
     * <em>Worth Repeating:</em> The {@link org.wicketstuff.annotation.scan.AnnotatedMountScanner} assumes
     * that the first two parameters to any constructor are String (mount path)
     * and Class (Page class).
     * 
     * @return Order of arguments.
     */
    String[] argOrder();
}
