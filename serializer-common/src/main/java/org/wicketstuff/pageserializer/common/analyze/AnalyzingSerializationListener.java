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
package org.wicketstuff.pageserializer.common.analyze;

import org.wicketstuff.pageserializer.common.listener.ThreadLocalContextSerializationListener;

/**
 * serialization listener with some aggregation hooks
 * 
 * @author mosmann
 * 
 */
public class AnalyzingSerializationListener extends
	ThreadLocalContextSerializationListener<ObjectTreeTracker>
{
	private final IObjectLabelizer labelizer;
	private final ISerializedObjectTreeProcessor treeProcessor;

	/**
	 * creates an listener which processes tree data from serialization process 
	 * @param labelizer a hook for label creation
	 * @param treeProcessor tree processor
	 */
	public AnalyzingSerializationListener(IObjectLabelizer labelizer,
		ISerializedObjectTreeProcessor treeProcessor)
	{
		this.labelizer = labelizer;
		this.treeProcessor = treeProcessor;
	}

	@Override
	protected ObjectTreeTracker createContext(Object object)
	{
		return new ObjectTreeTracker(labelizer, object);
	}

	@Override
	public void begin(ObjectTreeTracker treeTracker, Object object)
	{
	}

	@Override
	public void before(ObjectTreeTracker treeTracker, int position, Object object)
	{
		if (object != null)
			treeTracker.newItem(position, object);
	}

	@Override
	public void after(ObjectTreeTracker treeTracker, int position, Object object)
	{
		if (object != null)
			treeTracker.closeItem(position, object);
	}

	@Override
	public void end(ObjectTreeTracker treeTracker, Object object, Exception exception)
	{
		if (exception == null)
			treeProcessor.process(treeTracker.end(object));
	}

}
