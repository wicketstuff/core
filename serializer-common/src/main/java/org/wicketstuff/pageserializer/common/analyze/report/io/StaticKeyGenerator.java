/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson jeremy@thomersonfamily.com
 * 	2012 Michael Mosmann michael@mosmann.de
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
package org.wicketstuff.pageserializer.common.analyze.report.io;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;


public class StaticKeyGenerator implements IReportKeyGenerator {

	private String _staticValue;

	public StaticKeyGenerator(String staticValue) {
		_staticValue = staticValue;
	}

	@Override
	public String keyOf(ISerializedObjectTree tree) {
		return _staticValue;
	}

}
