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
package org.wicketstuff.html5.media.webvtt;

/**
 * A time time representation of WebVTT
 * 
 * @author Tobias Soloschenko
 * 
 */
public class VttTime
{

	private int h = 0;

	private int m = 0;

	private int s = 0;

	private int mi = 0;

	/**
	 * Gets the hours
	 * 
	 * @return the hours
	 */
	public int getH()
	{
		return h;
	}

	/**
	 * Sets the hour (has to be blow 100)
	 * 
	 * @param h
	 *            the hour
	 * @return the time to perform further operations
	 */
	public VttTime setH(int h)
	{
		if (h >= 100)
		{
			throw new IllegalArgumentException("Hours should not be greater then 99");
		}
		this.h = h;
		return this;
	}

	/**
	 * Gets the minutes
	 * 
	 * @return the minutes
	 */
	public int getM()
	{
		return m;
	}

	/**
	 * Sets the minutes (has to be below 100)
	 * 
	 * @param m
	 *            the minutes
	 * @return the time to perform further operations
	 */
	public VttTime setM(int m)
	{
		if (m >= 100)
		{
			throw new IllegalArgumentException("Minutes should not be greater then 99");
		}
		this.m = m;
		return this;
	}

	/**
	 * Gets the seconds
	 * 
	 * @return the seconds
	 */
	public int getS()
	{
		return s;
	}

	/**
	 * Sets the seconds (has to be below 100)
	 * 
	 * @param s
	 *            the seconds
	 * @return the time to perform further operations
	 */
	public VttTime setS(int s)
	{
		if (s >= 100)
		{
			throw new IllegalArgumentException("Seconds should not be greater then 99");
		}
		this.s = s;
		return this;
	}

	/**
	 * Gets the milliseconds
	 * 
	 * @return the milliseconds
	 */
	public int getMi()
	{
		return mi;
	}

	/**
	 * Sets the milliseconds (has to be below 1000)
	 * 
	 * @param mi
	 *            the milliseconds
	 * @return the time to perform further operations
	 */
	public VttTime setMi(int mi)
	{
		if (mi >= 1000)
		{
			throw new IllegalArgumentException("Milliseconds should not be greater then 999");
		}
		this.mi = mi;
		return this;
	}

	/**
	 * Gets the representation as String
	 * 
	 * @return the representation as String
	 */
	public String getRepresentation()
	{
		return String.format("%02d:%02d:%02d.%03d", h, m, s, mi);
	}
}
