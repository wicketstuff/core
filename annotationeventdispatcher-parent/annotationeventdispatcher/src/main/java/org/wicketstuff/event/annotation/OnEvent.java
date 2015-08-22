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
package org.wicketstuff.event.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used to tag methods that should be called by {@link AnnotationEventDispatcher} to
 * handle the events. The methods should be public and take exactly one parameter. The method will
 * be called only if the event payload type matches the method's parameter type.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface OnEvent
{
	/**
	 * Whether to stop further broadcast of this event. Left unspecified it
	 * defaults to false.
	 *
	 * @return whether to stop further broadcast of this event
	 */
	boolean stop() default false;

	/**
	 * Optional classes related to the event which may be use to distinguish
	 * methods for event handling purposes.
	 *
	 * @return generic classes in the event
	 */
	Class<?>[] types() default {};
}
