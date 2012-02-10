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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify the primary and alternate paths to mount a Page.
 * 
 * <p>
 * If no value (or an empty string) is provided, the AnnotatedMountScanner class will resolve a
 * value based on the page class (by default, <code>pageClass.getSimpleName()</code>).
 * 
 * <p>
 * The primary mount path is listed ahead of alternate paths in any list returned by
 * {@link org.wicketstuff.annotation.scan.AnnotatedMountScanner}. This is done because of the
 * implementation of
 * {@link org.apache.wicket.protocol.http.request.WebRequestCodingStrategy#getMountEncoder(org.apache.wicket.IRequestTarget)}
 * getMountEncoder() returns the first mount that matches the given Page. Thus, when determining
 * which path to mount a page on, it always picks the first one found.
 * 
 * @author Doug Donohoe
 * @author Ronald Tetsuo Miura
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MountPath
{
	/**
	 * @return primary mount path. If no value (or an empty string) is provided, the
	 *         AnnotatedMountScanner class will resolve a value based on the page class (by default,
	 *         <code>pageClass.getSimpleName()</code>).
	 */
	String value() default "";

	/**
	 * @return alternate mount paths
	 */
	String[] alt() default { };
}
