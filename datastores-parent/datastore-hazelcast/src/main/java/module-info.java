module org.wicketstuff.datastore.hazelcast {
	exports org.wicketstuff.datastores.hazelcast;

	requires org.apache.wicket.core;
	requires org.apache.wicket.util;

	requires transitive com.hazelcast.core;
	requires org.slf4j;

	requires static jakarta.servlet;
}
