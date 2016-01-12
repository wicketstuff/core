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

import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.ResourcePath;
import org.wicketstuff.rest.contenthandling.webserialdeserial.JsonTestWebSerialDeserial;

@ResourcePath("/mountedpath")
public class MountedResource extends AbstractRestResource<JsonTestWebSerialDeserial>
{

	public MountedResource()
	{
		super(new JsonTestWebSerialDeserial());
	}

	public MountedResource(JsonTestWebSerialDeserial serialDeserial)
	{
		super(serialDeserial);
	}

	@MethodMapping("/")
	public String dummyMethod()
	{
		return "I'm dummy!";
	}
}
