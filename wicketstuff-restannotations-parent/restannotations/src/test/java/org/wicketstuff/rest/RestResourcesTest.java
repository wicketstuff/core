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

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.xml.bind.JAXB;
import javax.xml.transform.stream.StreamResult;

import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.objserialdeserial.TestJsonDesSer;
import org.wicketstuff.rest.contenthandling.webserialdeserial.JsonTestWebSerialDeserial;
import org.wicketstuff.rest.resource.AbstractRestResource;
import org.wicketstuff.rest.resource.RestResourceFullAnnotated;
import org.wicketstuff.rest.utils.test.BufferedMockRequest;
import org.wicketstuff.rest.utils.wicket.bundle.DefaultBundleResolver;


/**
 * Simple test using the WicketTester
 */
public class RestResourcesTest
{
	private WicketTester tester;
	private final Roles roles = new Roles();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication(roles));
	}

	@After
	public void tearDown()
	{
		// session must remain temporary.
		Assert.assertTrue(Session.get().isTemporary());
		tester.destroy();
	}

	@Test
	public void testMethodParametersTypeResolving()
	{
		// start and render the test page
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api");
		testIfResponseStringIsEqual("testMethodNoArgs");

		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/12345");
		testIfResponseStringIsEqual("12345");

		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/hjjzj");
		Assert.assertEquals(400, tester.getLastResponse().getStatus());

		tester.getRequest().setMethod("POST");
		tester.executeUrl("./api/monoseg");
		testIfResponseStringIsEqual("testMethodPostSegFixed");

		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/boolean/true");
		testIfResponseStringIsEqual("testMethodPostBoolean:true");

		tester.getRequest().setMethod("GET");
		tester.getRequest().setParameter("price", "" + 12.34);
		tester.executeUrl("./api/products/112");
		testIfResponseStringIsEqual("testMethodGetParameter");

		tester.getRequest().setMethod("POST");
		tester.getRequest().setCookies(new Cookie[] { new Cookie("name", "bob") });
		tester.executeUrl("./api/person/113;height=170");
		testIfResponseStringIsEqual("testMethodCookieParameter:113bob");

		tester.getRequest().setMethod("POST");
		tester.getRequest().setParameter("title", "The divine comedy.");
		tester.executeUrl("./api/book/113");
		testIfResponseStringIsEqual("testPostRequestParameter");

		tester.getRequest().setMethod("POST");
		tester.getRequest().setHeader("credential", "bob");
		tester.executeUrl("./api/test/with/headerparams");
		testIfResponseStringIsEqual("testHeaderParams");

		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/variable/31/order/segtext");
		testIfResponseStringIsEqual("testParamOutOfOrder");

		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/testreqdef");
		testIfResponseStringIsEqual("testRequiredDefault");
	}

	@Test
	public void testWrongParamValue() throws Exception
	{
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/wrongParamValue?intValue=AAA");
		testIfResponseStringIsEqual("");
		Assert.assertEquals(400, tester.getLastResponse().getStatus());
	}

	@Test
	public void testSimilarMountedPath() throws Exception
	{
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/path/get_from_another_path?term=ff");
		testIfResponseStringIsEqual("getFromAnotherPath");
	}

	@Test
	public void testJsonDeserializedParamRequest()
	{
		// test @RequestBody annotation
		BufferedMockRequest jsonMockRequest = new BufferedMockRequest(tester.getApplication(),
			tester.getHttpSession(), tester.getServletContext(), "POST");
		jsonMockRequest.setReader(new BufferedReader(new StringReader(TestJsonDesSer.getJSON())));

		tester.setRequest(jsonMockRequest);
		tester.executeUrl("./api/19");
	}

	@Test
	public void testJsonSerializedResponse()
	{
		// test JSON response
		tester.getRequest().setMethod("POST");
		tester.executeUrl("./api");

		JSONObject actual = new JSONObject(tester.getLastResponseAsString());
		Assert.assertEquals("Smith", actual.getString("surname"));
		Assert.assertEquals("Mary", actual.getString("name"));
		Assert.assertEquals("m.smith@gmail.com", actual.getString("email"));
	}

	@Test
	public void testMethodNotFound() throws Exception
	{
		tester.executeUrl("./api2/foo");
		String response = tester.getLastResponseAsString();

		Assert.assertTrue(response.contains(AbstractRestResource.NO_SUITABLE_METHOD_FOUND));
	}

	@Test
	public void rolesAuthorizationMethod()
	{
		// without roles must get a 401 HTTP code (user unauthorized)
		roles.clear();
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/admin");
		Assert.assertEquals(401, tester.getLastResponse().getStatus());
		testIfResponseStringIsEqual(AbstractRestResource.USER_IS_NOT_ALLOWED);

		// add the role to pass the test
		roles.add("ROLE_ADMIN");
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api/admin");
		Assert.assertEquals(200, tester.getLastResponse().getStatus());
		testIfResponseStringIsEqual("testMethodAdminAuth");
	}

	@Test
	public void testValidator()
	{
		tester.getRequest().setMethod("GET");
		tester.getRequest().setParameter("email", "noMailValue");
		tester.executeUrl("./api/emailvalidator");
		testIfResponseStringIsEqual("The value inserted as email is not valid.");

		String email = "avalid@mail.com";

		tester.getRequest().setMethod("GET");
		tester.getRequest().setParameter("email", email);
		tester.executeUrl("./api/emailvalidator");
		testIfResponseStringIsEqual(email);

		tester.getRequest().setMethod("GET");
		tester.getRequest().setParameter("customvalidator", "customvalidator");
		tester.executeUrl("./api/customvalidator");

		String response = tester.getLastResponseAsString();
		DefaultBundleResolver resolver = new DefaultBundleResolver(RestResourceFullAnnotated.class);

		assertEquals(resolver.getMessage("CustomValidator", Collections.EMPTY_MAP), response);
	}

	@Test
	public void testRoleCheckinRequired()
	{
		// RestResourceFullAnnotated uses annotation AuthorizeInvocation
		// hence it needs a roleCheckingStrategy to be built
		exception.expect(WicketRuntimeException.class);
		new RestResourceFullAnnotated(new JsonTestWebSerialDeserial());
	}

	@Test
	public void testMethodParamWithOtherAnnotations()
	{
		tester.getRequest().setMethod("POST");
		tester.getRequest().setParameter("title", "The divine comedy.");
		tester.executeUrl("./api/param/31/annotated/james");

		testIfResponseStringIsEqual("testAnnotatedParameters");
	}

	@Test
	public void testRegExpResource() throws Exception
	{
		tester.getRequest().setMethod("GET");
		tester.getRequest().setCookies(new Cookie[] { new Cookie("credential", "bob") });
		tester.executeUrl("./api2/recordlog/message/07-23-2007_success");

		Assert.assertEquals(200, tester.getLastResponse().getStatus());

		tester.getRequest().setMethod("GET");
		tester.getRequest().setCookies(new Cookie[] { new Cookie("credential", "bob") });
		tester.executeUrl("./api2/recordlog/message/34xxxxx");

		Assert.assertEquals(400, tester.getLastResponse().getStatus());
	}

	@Test
	public void testMultiFormat() throws Exception
	{
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./api3/person");

		assertEquals(RestMimeTypes.APPLICATION_XML, tester.getLastResponse().getContentType());

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);

		JAXB.marshal(RestResourceFullAnnotated.createTestPerson(), result);

		assertEquals(writer.toString(), tester.getLastResponseAsString());
	}

	protected void testIfResponseStringIsEqual(String value)
	{
		Assert.assertEquals(value, tester.getLastResponseAsString());
	}
}
