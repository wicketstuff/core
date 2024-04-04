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
package org.wicketstuff.html5.shape;

/**
 * Creates a inset shape - it's like a rectangle
 * 
 * @author Tobias Soloschenko
 *
 */
public class Inset extends AbstractShape
{

	private String top;
	private String right;
	private String bottom;
	private String left;
	private String border_radius;

	/**
	 * Creates a inset shape
	 * 
	 * @param top
	 *            the top distance
	 * @param right
	 *            the right distance
	 * @param bottom
	 *            the bottom distance
	 * @param left
	 *            the left distance
	 * @param border_radius
	 *            the radius
	 */
	public Inset(String top, String right, String bottom, String left, String border_radius)
	{
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		this.border_radius = border_radius;
	}

	public Inset(String top, String right, String bottom, String left)
	{
		this(top, right, bottom, left, null);
	}

	@Override
	public String getName()
	{
		return "inset";
	}

	@Override
	public String getValues()
	{
		return this.top + this.right + this.bottom + this.left +
			(this.border_radius != null ? this.border_radius : "");
	}

}
