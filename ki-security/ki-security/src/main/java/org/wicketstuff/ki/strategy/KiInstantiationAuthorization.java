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
package org.wicketstuff.ki.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.PACKAGE, ElementType.TYPE } )
@Documented
@Inherited
public @interface KiInstantiationAuthorization {

  /**
   * @return if the Subject has a valid Principal
   */
  boolean loggedIn() default true;
  
  /**
   * @return if the Subject must be authenticated <b>in this session</b>
   */
  boolean authenticated() default false;

  /**
   * Gets the roles that are allowed to take the action.
   * 
   * @return the roles that are allowed. Returns a zero length array by default
   */
  String[] role() default {};

  /**
   * Gets the roles that are allowed to take the action.
   * 
   * @return the roles that are allowed. Returns a zero length array by default
   */
  String[] permission() default {};
}
