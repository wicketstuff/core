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
import org.wicketstuff.rest.Person;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.webserialdeserial.MultiFormatSerialDeserial;

public class MultiFormatRestResource extends AbstractRestResource<MultiFormatSerialDeserial>
{

	public MultiFormatRestResource(MultiFormatSerialDeserial jsonSerialDeserial,
		IRoleCheckingStrategy roleCheckingStrategy)
	{
		super(jsonSerialDeserial, roleCheckingStrategy);
	}

	public MultiFormatRestResource(MultiFormatSerialDeserial jsonSerialDeserial)
	{
		super(jsonSerialDeserial);
	}

	@MethodMapping(value = "/person", produces = RestMimeTypes.APPLICATION_XML)
	public Person returnMarshaledObject()
	{
		return RestResourceFullAnnotated.createTestPerson();
	}
}
