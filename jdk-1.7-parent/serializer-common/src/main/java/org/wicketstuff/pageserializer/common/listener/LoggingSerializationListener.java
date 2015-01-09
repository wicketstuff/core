/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * writes basic infos to log(Level=DEBUG)
 * 
 * @author mosmann
 * 
 */
public class LoggingSerializationListener implements ISerializationListener
{

	private final static Logger LOG = LoggerFactory.getLogger(LoggingSerializationListener.class);

	@Override
	public void begin(Object object)
	{
		LOG.debug("Start for object: '{}'", object.getClass());
	}

	@Override
	public void before(int position, Object object)
	{
		LOG.debug("Start at '{}' byte for object:  '{}'", position,
			object != null ? object.getClass() : "NULL");
	}

	@Override
	public void after(int position, Object object)
	{
		LOG.debug("End at   '{}' bytes for object: '{}'", position,
			object != null ? object.getClass() : "NULL");
	}

	@Override
	public void end(Object object, Exception exception)
	{
		LOG.debug("End for object:   '{}'", object.getClass());
	}

}
