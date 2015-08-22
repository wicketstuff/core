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
package org.wicketstuff.whiteboard.resource;

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Reference Class for goog.css of Google Closure Library
 * 
 * @author andunslg
 */
public class GoogStyleSheetResourceReference extends CssResourceReference {
	private static final long serialVersionUID = 1L;

	private static final GoogStyleSheetResourceReference INSTANCE = new GoogStyleSheetResourceReference();

	public static GoogStyleSheetResourceReference get() {
		return INSTANCE;
	}

	private GoogStyleSheetResourceReference() {
		super(GoogStyleSheetResourceReference.class, "goog.css");
	}
}
