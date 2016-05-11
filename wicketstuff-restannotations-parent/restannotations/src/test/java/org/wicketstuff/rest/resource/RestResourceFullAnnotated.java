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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.junit.Assert;
import org.wicketstuff.rest.CustomValidator;
import org.wicketstuff.rest.Person;
import org.wicketstuff.rest.annotations.AuthorizeInvocation;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.CookieParam;
import org.wicketstuff.rest.annotations.parameters.HeaderParam;
import org.wicketstuff.rest.annotations.parameters.MatrixParam;
import org.wicketstuff.rest.annotations.parameters.PathParam;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.annotations.parameters.RequestParam;
import org.wicketstuff.rest.annotations.parameters.ValidatorKey;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.webserialdeserial.TextualWebSerialDeserial;
import org.wicketstuff.rest.utils.http.HttpMethod;

public class RestResourceFullAnnotated extends AbstractRestResource<TextualWebSerialDeserial>
{
	public RestResourceFullAnnotated(TextualWebSerialDeserial jsonSerialDeserial,
		IRoleCheckingStrategy roleCheckingStrategy)
	{
		super(jsonSerialDeserial, roleCheckingStrategy);
	}

	public RestResourceFullAnnotated(TextualWebSerialDeserial jsonSerialDeserial)
	{
		super(jsonSerialDeserial);
	}

	@Override
	protected void onInitialize(TextualWebSerialDeserial objSerialDeserial)
	{
		EmailAddressValidator emailValidator = EmailAddressValidator.getInstance();

		registerValidator("emailvalidator", emailValidator);
		registerValidator("customvalidator", new CustomValidator());
	}

	/**
	 * Method for GET requests and URLs like '<resource path>/5'. The id parameter is automatically
	 * extracted from URL.
	 */
	@MethodMapping(value = "/{id}", produces = RestMimeTypes.TEXT_PLAIN)
	public int testMethodInt(int id)
	{
		return id;
	}

	/**
	 * Method invoked for GET requests and URLs like '<resource path>/5' The id parameter is
	 * automatically extracted from URL The person parameter is automatically deserialized from
	 * request body (which is JSON) The returned object is automatically serialized to JSON and
	 * written in the response
	 */
	@MethodMapping(value = "/{id}", httpMethod = HttpMethod.POST)
	public Person testMethodPostComplex(int id, @RequestBody Person person)
	{
		return person;
	}

	@MethodMapping(value = "/boolean/{boolean}", httpMethod = HttpMethod.GET, produces = RestMimeTypes.TEXT_PLAIN)
	public String testMethodPostBoolean(boolean value)
	{
		return "testMethodPostBoolean:" + value;
	}

	@MethodMapping(value = "/monoseg", httpMethod = HttpMethod.POST, produces = RestMimeTypes.TEXT_PLAIN)
	public String testMethodPostSegFixed()
	{
		return "testMethodPostSegFixed";
	}

	@MethodMapping(value = "/", produces = RestMimeTypes.TEXT_PLAIN)
	public String testMethodNoArgs()
	{
		return "testMethodNoArgs";
	}

	@MethodMapping(value = "/", httpMethod = HttpMethod.POST)
	public Person testMethodPost()
	{
		Person person = createTestPerson();
		return person;
	}

	@MethodMapping(value = "/admin", produces = RestMimeTypes.TEXT_PLAIN)
	@AuthorizeInvocation("ROLE_ADMIN")
	public String testMethodAdminAuth()
	{
		return "testMethodAdminAuth";
	}

	@MethodMapping(value = "/products/{id}", produces = RestMimeTypes.TEXT_PLAIN)
	public String testMethodGetParameter(int productId, @RequestParam("price") float prodPrice)
	{
		Args.notNull(productId, "productId");
		Args.notNull(prodPrice, "price");

		return "testMethodGetParameter";
	}

	@MethodMapping(value = "/book/{id}", produces = RestMimeTypes.TEXT_PLAIN)
	public String testMethodHeaderParameter(int productId, @HeaderParam("price") float prodPrice)
	{
		Args.notNull(productId, "productId");
		Args.notNull(prodPrice, "price");

		return "testMethodHeaderParameter";
	}

	@MethodMapping(value = "/person/{id}", httpMethod = HttpMethod.POST, produces = RestMimeTypes.TEXT_PLAIN)
	public String testMethodCookieParameter(@CookieParam("name") String name, int id,
		@MatrixParam(segmentIndex = 1, parameterName = "height") float height)
	{
		Args.notNull(id, "id");
		Args.notNull(name, "name");
		Args.notNull(height, "name");

		return "testMethodCookieParameter:" + id + name;
	}

	@MethodMapping(value = "/book/{id}", httpMethod = HttpMethod.POST, produces = RestMimeTypes.TEXT_PLAIN)
	public String testPostRequestParameter(int productId, @RequestParam("title") String title)
	{
		Args.notNull(productId, "productId");
		Args.notNull(title, "title");

		return "testPostRequestParameter";
	}

	@MethodMapping(value = "/param/{id}/annotated/{name}", httpMethod = HttpMethod.POST, produces = RestMimeTypes.TEXT_PLAIN)
	public String testAnnotatedParameters(int id, @TestAnnotation String name,
		@TestAnnotation @RequestParam("title") String title)
	{
		Args.notNull(id, "id");
		Args.notNull(name, "name");
		Args.notNull(title, "title");

		return "testAnnotatedParameters";
	}

	@MethodMapping(value = "/test/with/headerparams", httpMethod = HttpMethod.POST, produces = RestMimeTypes.TEXT_PLAIN)
	public String testHeaderParams(@HeaderParam("credential") String credential)
	{
		Args.notNull(credential, "credential");

		return "testHeaderParams";
	}

	@MethodMapping(value = "/variable/{p1}/order/{p2}", produces = RestMimeTypes.TEXT_PLAIN)
	public String testParamOutOfOrder(@PathParam("p2") String textParam,
		@PathParam("p1") int intParam)
	{
		Args.notNull(textParam, "textParam");
		Args.notNull(intParam, "intParam");

		return "testParamOutOfOrder";
	}

	@MethodMapping(value = "/emailvalidator", produces = RestMimeTypes.TEXT_PLAIN)
	public String testEmailValidator(
		@RequestParam("email") @ValidatorKey("emailvalidator") String email)
	{
		return email;
	}

	@MethodMapping(value = "/customvalidator", produces = RestMimeTypes.TEXT_PLAIN)
	public String testCustomValidator(
		@RequestParam("customvalidator") @ValidatorKey("customvalidator") String customfield)
	{
		return customfield;
	}

	@MethodMapping(value = "/testreqdef", produces = RestMimeTypes.TEXT_PLAIN)
	public String testRequiredDefault(
		@RequestParam(value = "fromRequest", required = false) String request,
		@CookieParam(value = "cookie", defaultValue = "true") boolean cookie,
		@HeaderParam(value = "price", defaultValue = "12.6") float price,
		@MatrixParam(parameterName = "matrixp", segmentIndex = 0, required = false, defaultValue = "0") int matrixp)
	{

		Assert.assertNull("request must be null!", request);
		Assert.assertTrue("must be true!", cookie);
		Assert.assertEquals(Float.parseFloat("12.6"), price, 0.01d);

		return "testRequiredDefault";
	}

	public static Person createTestPerson()
	{
		return new Person("Mary", "Smith", "m.smith@gmail.com");
	}

	@MethodMapping(value = "/path/get_from_path", produces = RestMimeTypes.TEXT_PLAIN)
	public String getFromPath(@RequestParam("term") String term)
	{
		return "getFromPath";
	}

	@MethodMapping(value = "/path/get_from_another_path", produces = RestMimeTypes.TEXT_PLAIN)
	public String getFromAnotherPath(@RequestParam("term") String term)
	{
		return "getFromAnotherPath";
	}

	@MethodMapping(value = "/wrongParamValue", produces = RestMimeTypes.TEXT_PLAIN)
	public String wrongParamValue(@RequestParam("intValue") int intValue)
	{
		return "wrongParamValue";
	}


}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface TestAnnotation
{
}
