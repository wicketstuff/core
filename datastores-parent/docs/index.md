---
title: Page Stores
category: infra
description: IPageStore implementations for Redis, Hazelcast, Cassandra, Ignite and Memcached, for clustered deployments.
artifactId: wicketstuff-datastore-redis
tags: cluster session cache hazelcast cassandra ignite memcached
---

By default Wicket stores the stateful pages serialized on the local disk (`DiskPageStore`). When the application is heavily used this may lead to performance regressions.

The modules provided by this project aim to provide better implementations which store the data in memory and may provide replication facilities.

To use one of these you have to configure Wicket by providing a customized `IPageManagerProvider`, e.g. using Ignite as the persistent store:

```java
setPageManagerProvider(new DefaultPageManagerProvider(this) {
    @Override
    protected IPageStore newPersistentStore() {
        IIgniteSettings settings = new IgniteSettings();
        settings.getAddresses().add("localhost");
        return new SerializingPageStore(new IgniteDataStore(getName(), settings),
            getFrameworkSettings().getSerializer());
    }
});
```

## Modules

All modules have the group id `org.wicketstuff`. Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

### Memcached

Uses [Memcached](https://memcached.org/) as a backend and [spymemcached](https://github.com/couchbase/spymemcached) as a Java driver.

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datastore-memcached</artifactId>
</dependency>
```

### Cassandra

Uses [Apache Cassandra](https://cassandra.apache.org/) as a backend and the DataStax `java-driver-core` as a Java driver.

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datastore-cassandra</artifactId>
</dependency>
```

### Redis

Uses [Redis](https://redis.io/) as a backend and [Jedis](https://github.com/redis/jedis) as a Java driver.

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datastore-redis</artifactId>
</dependency>
```

### Hazelcast

Uses [Hazelcast](https://hazelcast.com/) as a backend and its official Java driver.

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datastore-hazelcast</artifactId>
</dependency>
```

### Ignite

Uses [Apache Ignite](https://ignite.apache.org/) as a backend and the Ignite thin client as a Java driver.

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datastore-ignite</artifactId>
</dependency>
```

### Common

Provides `SessionQuotaManagingDataStore`, an `IPageStore` implementation that manages the memory usage by HTTP session. It should be used as a wrapper around another `IPageStore` implementation.

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datastore-common</artifactId>
</dependency>
```

The `datastore-tests` module contains a mini testing framework and demo application shared by the other modules.

## License

Apache 2.0.

## Author

[Martin Grigorov](https://github.com/martin-g)

Source: [`datastores-parent`](https://github.com/wicketstuff/core/tree/master/datastores-parent)
