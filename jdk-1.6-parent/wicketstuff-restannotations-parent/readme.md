#Wicket rest annotaions

REST-based API are becoming more and more popular around the web and the number of services based on this architecture is constantly increasing.

Wicket is well-known for its capability of transparently handling the state of web applications on server side, but it can be also easily adopted to create RESTful services.

This project provides a special resource class and a set of annotations to implement REST API/services in much the same way as we do it with Spring MVC or with the standard JAX-RS.

Quick overview
---------

The project provides class `AbstractRestResource` as generic abstract class to implement a Wicket resource that handles the request and the response using a particular data format (XML, JSON, etc...). <br/>
Subclassing `AbstractRestResource` we can create custom resources and map their pubblic methods to a given subpath with annotation `@MethodMapping`. The following snippet is taken from resource `PersonsRestResource` inside module 'restannotations-examples':

````java
	@MethodMapping("/persons")
	public List<PersonPojo> getAllPersons() {
		//method mapped at subpath "/persons" and HTTP method GET
	}
	
	@MethodMapping(value = "/persons/{personIndex}", httpMethod = HttpMethod.DELETE)
	public void deletePerson(int personIndex) {
		//method mapped at subpath "/persons/{personIndex}" and HTTP method DELETE. 
		//Segment {personIndex} will contain an integer value as index.
	}

	@MethodMapping(value = "/persons", httpMethod = HttpMethod.POST)
	public void createPerson(@RequestBody PersonPojo personPojo) {
		//creates a new instance of PersonPojo reading it from request body
	}
````

`@MethodMapping` requires to specify the subpath we want to map the method to. In addition we can specify also the HTTP method that must be used to invoke the method via REST (GET, POST, DELETE, etc...). This value can be specified with enum class `HttpMethod` and is GET by default. For more details on `@MethodMapping` see the section 'Annotations and advanced mapping'.<br/>
To promote the principle of *convention over configuration*, we don't need to use any annotation to map method parameters to path parameters if they are declared in the same order. If we need to manually bind method parameters to path parameters we can use annotation `PathParam`. See section 'Annotations and advanced mapping' to know how to use it.<br/>
If the mapped method returns a value, this last is automatically serialized to the supported data format and written to response object (we will shortly see how to work with data formats).<br/>
Annotation `@RequestBody` is used to extract the value of a method parameter from the request body.

**Note:** to convert strings to Java type, `AbstractRestResource` uses the standard Wicket mechanism based on the application converter locator:
````java
	IConverter converter = Application.get().getConverterLocator().getConverter(clazz);

	return converter.convertToObject(value, Session.get().getLocale()); 
````

To write/read objects to response/from request, `AbstractRestResource` uses an implementation of interface `IWebSerialDeserial` which defines the following methods: 

````java
public interface IWebSerialDeserial {
	
	public void objectToResponse(Object targetObject, WebResponse response, String mimeType) throws Exception;

	public <T> T requestToObject(WebRequest request, Class<T> argClass, String mimeType) throws Exception;

	public boolean isMimeTypeSupported(String mimeType);
}
````

The first two methods are the operations needed to write an object to the response body and to read an object from request body. Methods `isMimeTypeSupported` is used to know if a MIME format is supported by a given object serial/deserial. To work with MIME types we can use string constants from class `RestMimeTypes`. In addition to interface `IWebSerialDeserial` our serial/deserial can also implement interface `IObjectSerialDeserial` to better separate the serial/deserial process from web entities such as request and response. The interface is the following:<br/>

````java
public interface IObjectSerialDeserial<T>{
	
	public T serializeObject(Object target, String mimeType);
	
	public <E> E deserializeObject(T source, Class<E> targetClass, String mimeType);
}
````

<br/>
The main module comes with class `TextualObjectSerialDeserial` which implements both `IWebSerialDeserial` and `IObjectSerialDeserial` and that can be used as base class to implement serials/deserials that work with a textual MIME type and that need to know which charset encoding should be used.<br/>
As JSON is de-facto standard format for REST API, the project comes also with a ready-to-use resource (`GsonRestResource`) and a serial/deserial (`GsonSerialDeserial`) that work with JSON format (both inside module 'restannotations-json'). These classes use [Gson](http://code.google.com/p/google-gson/) as Json library. Resource `PersonsRestResource` in the example module is based on `GsonRestResource`.

Use multiple data format
---------
Annotation `@MethodMapping` has two optional attributes, _consumes_ and _produces_, that can be used to specify which MIME type must be expected in the request and which one must be used to serialize data to response. Their default value is "application/json". 
The following code is taken from class `MultiFormatRestResource` in the main module `restannotations` and it shows a mapped method that produces "text/xml":

````java
	@MethodMapping(value = "/person", produces = RestMimeTypes.TEXT_XML)
	public Person returnMarshaledObject(){
		//The instance returned will be marshaled to XML.
	}
````
If we want to use multiple mime types with our REST resource, we must use an implementation of `IWebSerialDeserial` that supports all the required types. For this special purpose we can use class `MultiFormatSerialDeserial` as base class for our custom `IWebSerialDeserial`.<br/> 
The class implements a custom version of _Composite pattern_ allowing to register a given `IWebSerialDeserial` for a specific MIME type. Utility class `RestMimeTypes` contains different MIME types as tring constants. The following is an example of usage of `MultiFormatSerialDeserial` taken from class `WicketApplication` in the main module `restannotations`:

````java
	MultiFormatSerialDeserial multiFormat = new MultiFormatSerialDeserial();
	//register one serial/deserial for JSON and another one for XML	
	multiFormat.registerSerDeser(new TestJsonDesSer(), RestMimeTypes.APPLICATION_JSON);
	multiFormat.registerSerDeser(new XmlSerialDeser(), RestMimeTypes.TEXT_XML);
				
````

Annotations and advanced mapping
---------
In the following list we will explore the annotations we can use to map resource methods and to create complex mapping rules. The code examples for annotations are taken from class `RestResourceFullAnnotated` in the main module `restannotations`.

+ **_@PathParam_:** This annotation indicates which path parameter must be used as value for a method parameter. Example:

````java
	@MethodMapping(value = "/variable/{p1}/order/{p2}", produces = RestMimeTypes.PLAIN_TEXT)
	public String testParamOutOfOrder(@PathParam("p2") String textParam, @PathParam("p1") int intParam) {
		//method parameter textParam is taken from path param 'p2', while intParam uses 'p1'
	}
````

+ **_@RequestParam_:** This annotation indicates that the value of a method parameter must be read from a request parameter. Example:

````java
	@MethodMapping(value = "/products/{id}", produces = RestMimeTypes.PLAIN_TEXT)
	public String testMethodGetParameter(int productId, @RequestParam("price") float prodPrice) {
		//method parameter prodPrice is taken from the request parameter named 'price'
	}
````

+ **_@HeaderParam_:**This annotation indicates that the value of a method parameter must be read from a header parameter. Example:

````java
	@MethodMapping(value = "/book/{id}", produces = RestMimeTypes.PLAIN_TEXT)
	public String testMethodHeaderParameter(int productId, @HeaderParam("price") float prodPrice) {
		//method parameter prodPrice is taken from the header parameter named 'price'
	}
````

+ **_@MatrixParam_:**This annotation indicates that the value of a method parameter must be read from a [matrix parameter](http://www.w3.org/DesignIssues/MatrixURIs.html). Example:

````java
	@MethodMapping(value = "/person/{id}", httpMethod = HttpMethod.POST, produces = RestMimeTypes.PLAIN_TEXT)
	public String testMethodCookieParameter(int id, @MatrixParam(segmentIndex = 1, parameterName = "height") float height) {
		//method parameter prodPrice is taken from the matrix parameter of the second URL segment and named 'height'.
		//Matching URL example: ./person/1;height=500
	}
````
The annotation needs to know the name of the matrix parameter and the index (zero-based) of the segment that contains the parameter.
+ **_@CookieParam_:** This annotation indicates that the value of a method parameter must be read from a cookie.


````java
	@MethodMapping(value = "/person/{id}", httpMethod = HttpMethod.POST, produces = RestMimeTypes.PLAIN_TEXT)
	public String testMethodCookieParameter(@CookieParam("name") String name, int id) {
		//method parameter name is taken from cookie parameter named 'name'.
	}
````

+ **_@AuthorizeInvocation_:**
With annotation `@AuthorizeInvocation` we can apply security restrictions to mapped methods specifing which user roles are allowed to execute a specific method. 

````java
	@MethodMapping(value = "/admin", httpMethod = HttpMethod.GET)
	@AuthorizeInvocation("ROLE_ADMIN")
	public void testMethodAdminAuth() {

	}
````

To use annotation `@AuthorizeInvocation` we must specify in the resource construcor an instance of Wicket interface `IRoleCheckingStrategy`.

**Note:** annotations `@HeaderParam`, `@CookieParam`, `@MatrixParam`, `@RequestParam` expose flag `required` to make them optional, and attribute `defaultValue` to specify a default value.


### Advanced mapping ###

Every URL segment can contain multiple path parameters and each of them can specify the regular expression to use to match incoming requests. For example module `restannotations` contains class `RegExpRestResource` which maps the following method:

````java
	@MethodMapping("recordlog/message/{day:\\d{2}}-{month:\\d{2}}-{year:\\d{4}}_{message}")
	public void testLogMessage(@CookieParam("credential") String credential, int day, int month, int year, String message){
		//integer parameters of the method are read from path parameters that contain a regular expression.
	}
````

As you can see in the code above, the syntax to write a regular expression is _{variableName:regExp}_.

Hook methods
---------
To customize the configuration and the behavior of our resource, the following hook methods are provided:

+ **_configureObjSerialDeserial(T objSerialDeserial)_:** called by constructor to configure the object serial/deserial.
+ **_onBeforeMethodInvoked(MethodMappingInfo mappedMethod,Attributes attribs)_:** triggered just before the mapped method is invoked to serve the request. The method takes in input `mappedMethod` which contains the details on the method that is going to be invoked, and `attribs` which is the current Attributes object.
+ **_onAfterMethodInvoked(MethodMappingInfo mappedMethod,Attributes attribs,Object res)_:** triggered just after the mapped method is invoked to serve the request. In addition to the parameters exposed by _onBeforeMethodInvoked_, in this method we find also the object returned by the invoked method.
