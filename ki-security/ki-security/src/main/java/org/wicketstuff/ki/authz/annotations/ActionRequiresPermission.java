/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.ki.authz.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.wicketstuff.ki.authz.AuthzAction;


@Target( ElementType.TYPE )
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ActionRequiresPermission {

  /**
   * The permission string which will be passed to {@link org.jsecurity.subject.Subject#isPermitted(String)}
   * to determine if the user is allowed to invoke the code protected by this annotation.
   */
  String permission();

  AuthzAction action() default AuthzAction.RENDER;
}

