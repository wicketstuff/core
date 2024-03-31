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
package org.wicketstuff.security.actions;

/**
 * Used by containers to specify that all of there children inherit these actions. For example if we
 * specify that a form has these rights {@link Inherit}+{@link Render} it means all the textboxes on
 * the form will be atleast readonly. If we specify however the form has Inherit (==Inherit+
 * {@link Access}) it means none of the textboxes is going to show up (unless we give them
 * individually read rights) because all components should have atleast Render rights before they
 * can be displayed.
 * 
 * @author marrink
 */
public interface Inherit extends WaspAction
{

}
