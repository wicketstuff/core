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
import org.wicketstuff.rest.Developer;
import org.wicketstuff.rest.Person;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.contenthandling.webserialdeserial.TextualWebSerialDeserial;
import org.wicketstuff.restutils.http.HttpMethod;

public class ExtendsRestResource extends RestResourceFullAnnotated {

    public ExtendsRestResource(TextualWebSerialDeserial jsonSerialDeserial,
                                     IRoleCheckingStrategy roleCheckingStrategy)
    {
        super(jsonSerialDeserial, roleCheckingStrategy);
    }

    @MethodMapping(value = "/", httpMethod = HttpMethod.POST)
    public Developer testMethodPost()
    {
        Developer d = new Developer();
        d.setName("Mary");
        d.setSurname("Smith");
        d.setEmail("m.smith@gmail.com");
        return d;
    }
}
