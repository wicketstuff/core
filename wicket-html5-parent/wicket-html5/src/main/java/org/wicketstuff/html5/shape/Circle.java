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
 * Creates a circle shape
 * 
 * @author Tobias Soloschenko
 *
 */
public class Circle extends AbstractShape
{

	private String radius;
	private String x;
	private String y;

	/**
	 * Creates a circle shape
	 * 
	 * @param radius
	 *            the radius of the circle
	 */
	public Circle(String radius)
	{
		this(radius, null, null);
	}

	/**
	 * Creates a circle shape
	 * 
	 * @param radius
	 *            the radius of the circle
	 * @param x
	 *            the x position of the circle
	 * @param y
	 *            the y position of the circle
	 */
	public Circle(String radius, String x, String y)
	{
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName()
	{
		return "circle";
	}

	@Override
	public String getValues()
	{
		return this.radius +
			(this.x != null && this.y != null ? " at " + this.x + " " + this.y : "");
	}

}
