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
package org.wicketstuff.rest.contenthandling.json.webserialdeserial;

import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.webserialdeserial.TextualWebSerialDeserial;

/**
 * Web serializer/deserailizer that works with JSON format and UTF-8 encoding charset.
 * 
 * @author andrea del bene
 * @see TextualWebSerialDeserial
 */
public class JsonWebSerialDeserial extends TextualWebSerialDeserial
{

    public JsonWebSerialDeserial(IObjectSerialDeserial<String> objectSerialDeserial)
    {
	super("UTF-8", RestMimeTypes.APPLICATION_JSON, objectSerialDeserial);
    }
}
