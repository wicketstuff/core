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
package org.wicketstuff.rest.lambda.mounter;

import org.apache.wicket.protocol.http.WebApplication;
import org.junit.Test;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.test.RestTestCase;

public class LambdaRestApplicationTest extends RestTestCase 
{	
	@Override
	protected WebApplication newApplication() 
	{
		return new TestApplication();
	}
	
	@Test
	public void testResources() throws Exception 
	{
		assertUrlResponse("./testget", HttpMethod.POST, "");
		assertUrlResponse("./testget", HttpMethod.GET, "hello!");
		assertUrlResponse("./testjson", HttpMethod.POST, TestApplication.toJson(TestApplication.map));
		assertUrlResponse("./testparam/45", HttpMethod.OPTIONS, "45");
		assertUrlResponse("./deleteit", HttpMethod.DELETE, "deleted");
	}
}
