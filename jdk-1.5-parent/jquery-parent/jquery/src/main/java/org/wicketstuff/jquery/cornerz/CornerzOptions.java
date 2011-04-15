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
package org.wicketstuff.jquery.cornerz;

import org.wicketstuff.jquery.Options;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CornerzOptions extends Options
{

	private static final long serialVersionUID = 1L;

	public CornerzOptions radius(int radius)
	{
		set("radius", radius);
		return this;
	}

	public CornerzOptions borderWidth(int borderWidth)
	{
		set("borderWidth", borderWidth);
		return this;
	}

	public CornerzOptions background(String background)
	{
		set("background", background);
		return this;
	}

	public CornerzOptions borderColor(String borderColor)
	{
		set("borderColor", borderColor);
		return this;
	}

	public CornerzOptions corners(String corners)
	{
		set("corners", corners);
		return this;
	}

}
