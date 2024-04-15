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
package com.googlecode.wicket.jquery.ui.effect;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link JQueryEffectContainer} and the {@link JQueryEffectBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IEffectListener extends IClusterable
{
	/**
	 * Indicates whether the callback should be triggered when the effect completes.<br>
	 * If true, the {@link #onEffectComplete(AjaxRequestTarget)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isCallbackEnabled();

	/**
	 * Triggered when the effects is completed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 *
	 * @see #isCallbackEnabled()
	 */
	void onEffectComplete(AjaxRequestTarget target);
}
