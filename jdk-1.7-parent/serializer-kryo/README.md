Serializer Kryo
===============
is an implementation of `org.apache.wicket.serialize.ISerializer` for Wicket 1.5

Such serializer can be used to convert almost any kind of object to/from byte array. Almost any because Kryo may need your help for some complex graph of objects. Refer to Kryo documentation to understand more about that.

When configured with


	public class MyApplication extends WebApplication
	{
		@Override
		public void init()
		{
			super.init();

			getFrameworkSettings().setSerializer(new KryoSerializer());
		}
	}


it will be used to serialize any page for the IPageStore needs.

It is based on [Kryo](http://code.google.com/p/kryo/) and [kryo-serializers](https://github.com/magro/kryo-serializers). 

Notes
----
* serializer-kryo is not heavily tested so it may have need for more custom serializers for some of the Wicket classes. Let us know if you face a problem by creating an issue. Pull requests are more than welcome!

* serializer-kryo uses SUN/Oracle propriate APIs (sun.reflect.ReflectionFactory) and thus cannot be used on different JDKs.
