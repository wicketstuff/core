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
package org.apache.wicket.security.examples.multilogin.entities;

import java.io.Serializable;

/**
 * A simple data object. Completely irrelevant to security.
 * 
 * @author marrink
 */
public final class Entry implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Who initiated the transfer
	 */
	public String from;

	/**
	 * What was the destination.
	 */
	public String to;

	/**
	 * When was it send.
	 */
	public String when;

	/**
	 * Why was it send.
	 */
	public String description;

	/**
	 * How much was send.
	 */
	public String amount;

	/**
	 * The name of the beneficiary.
	 */
	public String owner;

	/**
	 * The bank of the beneficiary
	 */
	public String bank;

	/**
	 * Construct.
	 */
	public Entry()
	{
		super();
	}

	/**
	 * Construct.
	 * 
	 * @param from
	 * @param to
	 * @param when
	 * @param description
	 * @param amount
	 */
	public Entry(String from, String to, String when, String description, String amount)
	{
		super();
		this.from = from;
		this.to = to;
		this.when = when;
		this.description = description;
		this.amount = amount;
	}

}