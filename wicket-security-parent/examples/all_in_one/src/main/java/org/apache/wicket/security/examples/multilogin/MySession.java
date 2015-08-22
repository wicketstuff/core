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
package org.apache.wicket.security.examples.multilogin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.examples.multilogin.entities.Entry;

/**
 * custom session to keep track of moneytransfers.
 * 
 * @author marrink
 */
public class MySession extends WaspSession
{
	private static final long serialVersionUID = 1L;

	private List<Entry> moneyTransfers;

	/**
	 * Construct.
	 * 
	 * @param application
	 * @param request
	 */
	public MySession(WaspApplication application, Request request)
	{
		super(application, request);
		moneyTransfers = new ArrayList<Entry>();
	}

	/**
	 * Gets moneytransfers.
	 * 
	 * @return moneytransfers
	 */
	public List<Entry> getMoneyTransfers()
	{
		return moneyTransfers;
	}

	/**
	 * Return the session.
	 * 
	 * @return the session
	 */
	public static final MySession getSessesion()
	{
		return (MySession) Session.get();
	}
}
