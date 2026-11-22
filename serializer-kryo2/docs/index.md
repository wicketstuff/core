---
title: Kryo Serializer
category: infra
description: A fast ISerializer for page storage, based on Kryo.
artifactId: wicketstuff-serializer-kryo2
tags: serialization performance
---

`KryoSerializer` is an implementation of `org.apache.wicket.serialize.ISerializer`.

Such serializer can be used to convert almost any kind of object to/from byte array. Almost any because Kryo may need your help for some complex graph of objects. Refer to Kryo documentation to understand more about that.

When configured with

```java
public class MyApplication extends WebApplication
{
	@Override
	public void init()
	{
		super.init();

		getFrameworkSettings().setSerializer(new KryoSerializer());
	}
}
```

it will be used to serialize any page for the `IPageStore` needs.

It is based on [Kryo](https://github.com/EsotericSoftware/kryo) and [kryo-serializers](https://github.com/magro/kryo-serializers).

## Note

The Kryo serializer is not heavily tested so it may have need for more custom serializers for some of the Wicket classes. Let us know if you face a problem by creating an issue. Pull requests are more than welcome!

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-serializer-kryo2</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [module README](https://github.com/wicketstuff/core/blob/wicket-11.x/serializer-kryo2/README.md) has more details.

Source: [`serializer-kryo2`](https://github.com/wicketstuff/core/tree/wicket-11.x/serializer-kryo2)
