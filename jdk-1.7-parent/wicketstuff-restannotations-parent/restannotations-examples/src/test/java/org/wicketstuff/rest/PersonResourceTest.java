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
package org.wicketstuff.rest;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.rest.domain.PersonPojo;
import org.wicketstuff.rest.utils.test.BufferedMockRequest;

import com.google.gson.Gson;

/**
 * Simple test using the WicketTester
 */
public class PersonResourceTest extends Assert
{
    private WicketTester tester;
    
    final private Gson gson = new Gson();
    
    private BufferedMockRequest mockRequest;

    @Before
    public void setUp()
    {
	tester = new WicketTester(new WicketApplication());
	mockRequest = new BufferedMockRequest(tester.getApplication(),
		tester.getHttpSession(), tester.getServletContext(), "POST");
    }

    @After
    public void tearDown()
    {
	tester.destroy();
    }

    @Test
    public void testCreatePerson()
    {
	String jsonObj = gson.toJson(new PersonPojo("James Smith",
		"james@smith.com", "changeit"));

	mockRequest.setTextAsRequestBody(jsonObj);

	tester.setRequest(mockRequest);
	tester.executeUrl("./personsmanager/persons");

	assertEquals(jsonObj, tester.getLastResponseAsString());

	tester.getRequest().setMethod("GET");
	tester.executeUrl("./personsmanager/persons");

	assertTrue(tester.getLastResponseAsString().contains(jsonObj));
    }

    @Test
    public void testValidatorBundle()
    {
	String jsonObj = gson.toJson(new PersonPojo("James Smith",
		"notValidMail", "changeit"));

	mockRequest.setTextAsRequestBody(jsonObj);

	tester.setRequest(mockRequest);
	tester.executeUrl("./personsmanager/persons");

	assertTrue(tester.getLastResponseAsString().contains(
		"Email field not valid!"));
    }
}