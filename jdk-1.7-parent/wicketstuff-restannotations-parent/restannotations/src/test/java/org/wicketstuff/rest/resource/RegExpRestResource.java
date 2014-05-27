/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.resource;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.util.lang.Args;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.CookieParam;
import org.wicketstuff.rest.contenthandling.webserialdeserial.JsonTestWebSerialDeserial;

public class RegExpRestResource extends RestResourceFullAnnotated
{
	public RegExpRestResource(JsonTestWebSerialDeserial jsonSerialDeserial,
		IRoleCheckingStrategy roleCheckingStrategy)
	{
		super(jsonSerialDeserial, roleCheckingStrategy);
	}

	public RegExpRestResource(JsonTestWebSerialDeserial jsonSerialDeserial)
	{
		super(jsonSerialDeserial);
	}

	@MethodMapping("recordlog/message/{day:\\d{2}}-{month:\\d{2}}-{year:\\d{4}}_{message}")
	public void testLogMessage(@CookieParam("credential") String credential, int day, int month,
		int year, String message)
	{
		Args.notNull(credential, "credential");
		Args.notNull(day, "day");
		Args.notNull(month, "month");
		Args.notNull(year, "year");
		Args.notNull(message, "message");
	}
}
