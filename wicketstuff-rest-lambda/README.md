Wicket Rest Lambda
===

Module to write microservices with Wicket in a functional way, as we do with [Spark](http://sparkjava.com/). the parameter `attributes` is an instanace of [AttributesWrapper](http://static.javadoc.io/org.wicketstuff/wicketstuff-restannotations/7.5.0/org/wicketstuff/rest/utils/wicket/AttributesWrapper.html):
```java
    public class MyApplication extends WebApplication 
    {
        @Override
        protected void init()
        {
            Map<String, Object> map = new HashMap<>();
            map.put("integer", 123);
            map.put("string", "message");
            
            LambdaRestMounter restMounter = new LambdaRestMounter(this);
            
            //return plain string
            restMounter.get("/testget", (attributes) -> "hello!", Object::toString);
            //specify a function to transform the returned object into text (json in this case)
            restMounter.post("/testjson", (attributes) -> map, JSONObject::valueToString);
            
            //return id value from url segment
            restMounter.options("/testparam/${id}", (attributes) -> {
                PageParameters pageParameters = attributes.getPageParameters();
                return pageParameters.get("id");
               }
            , Object::toString);
        }
    }
```
