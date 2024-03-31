module org.wicketstuff.select2 {
	exports org.wicketstuff.select2;
	exports org.wicketstuff.select2.json;
	exports org.wicketstuff.select2.util;

	requires transitive com.github.openjson;

	requires org.apache.wicket.core;
	requires org.apache.wicket.request;
	requires org.apache.wicket.util;

	requires de.agilecoders.wicket.webjars;

	requires org.slf4j;
}
