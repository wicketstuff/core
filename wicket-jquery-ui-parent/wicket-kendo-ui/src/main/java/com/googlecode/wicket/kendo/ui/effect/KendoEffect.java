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
package com.googlecode.wicket.kendo.ui.effect;

/**
 * Provides an enumeration of available Kendo UI effects
 *
 * @author Sebastien Briquet - sebfz1
 */
public enum KendoEffect
{
	FADE_IN("fade('in')"),
	FADE_OUT("fade('out')"),
	SLIDEIN_LEFT("slideIn('left')"),
	SLIDEIN_RIGHT("slideIn('right')"),
	SLIDEIN_UP("slideIn('up')"),
	SLIDEIN_DOWN("slideIn('down')"),
	FLIP_HORIZONTAL("flipHorizontal()"),
	EXPAND_VERTICAL("expand('vertical')");
	
	private final String method;

	private KendoEffect(String method)
	{
		this.method = method;
	}

	@Override
	public String toString()
	{
		return this.method;
	}
}
