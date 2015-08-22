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
 * The abstract shape provides basic functionality to all shapes
 * 
 * @author Tobias Soloschenko
 *
 */
public abstract class AbstractShape implements Shape
{

	private String transitionTime;


	@Override
	public String getTransitionTime()
	{
		return this.transitionTime;
	}


	/**
	 * Sets the transition the shape is used on hover effects
	 * 
	 * @param transitionTime
	 *            the transition time of the shape. Example: 5s (5 seconds)
	 */
	@Override
	public Shape transitionTime(String transitionTime)
	{
		this.transitionTime = transitionTime;
		return this;
	}
}
