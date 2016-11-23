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

/**
 * Provides an enumeration of available jQuery UI effects<br>
 * It can be used as argument on {@link JQueryEffectContainer#play(org.apache.wicket.core.request.handler.IPartialPageRequestHandler, Effect)}
 *
 * @author Sebastien Briquet - sebfz1
 */
public enum Effect
{
	Blind("blind"),
	Bounce("bounce"),
	Clip("clip"),
	Drop("drop"),
	Explode("explode"),
	Fold("fold"),
	Highlight("highlight"), //produce nothing !?
	Puff("puff"),
	Pulsate("pulsate"),
	Scale("scale"), //produce nothing !?
	Shake("shake"),
	Size("size"), //produce nothing !?
	Slide("slide");
//	Transfer("transfer"); // cause an error, to be investigated

	private final String name;

	private Effect(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
