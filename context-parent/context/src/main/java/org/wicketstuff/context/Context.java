/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the field to locate the required component, model or model object in the page component hierarchy.
 * 
 * Examples of usage:
 * @Context ParentPanel parent; // parent component of the ParentPanel  
 * @Context Set<Section> sections;  // all parents of the given type 
 * @Context Set<IData> parents; // all parents that implements the interface or partcular class
 * @Context IModel<Data> dataModel; // the parent model of the Data type
 * 
 * Locate all labels that meet the qualifier = 'userdata'.
 * To add the qualifier use the Qualifier behavior: i.e new Label("x").add(new Qualifier("userdata"))
 * @Context(traverseStrategy=TOP_DOWN, qualifier="userdata") Set<Label> userData;
 * 
 * @author zruchala
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Context {

	/**
	 * Whether the dependency is required.
	 */
	boolean required() default true;

	/**
	 * The traversal strategy used to resolve the dependency.
	 */
	Traversal traversal() default Traversal.UP;

	/**
	 * (Optional) Additional qualifier that has to be set for a component to be matched.
	 */
	String qualifier() default "";

}
