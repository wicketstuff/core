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
 * Creates an ellipse shape
 * 
 * @author Tobias Soloschenko
 *
 */
public class Ellipse extends AbstractShape
{

	private String xradius;
	private String yradius;
	private String x;
	private String y;


	/**
	 * Creates an ellipse shape
	 * 
	 * @param xradius
	 *            the radius of the x axis
	 * @param yradius
	 *            the radius of the y axis
	 */
	public Ellipse(String xradius, String yradius)
	{
		this(xradius, yradius, null, null);
		this.xradius = xradius;
		this.yradius = yradius;
	}

	/**
	 * Creates an ellipse shape
	 * 
	 * @param xradius
	 *            the radius of the x axis
	 * @param yradius
	 *            the radius of the y axis
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 */
	public Ellipse(String xradius, String yradius, String x, String y)
	{
		this.xradius = xradius;
		this.yradius = yradius;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName()
	{
		return "ellipse";
	}

	@Override
	public String getValues()
	{
		return this.xradius + " " + this.yradius +
			(this.x != null && this.y != null ? " at " + this.x + " " + this.y : "");
	}
}
