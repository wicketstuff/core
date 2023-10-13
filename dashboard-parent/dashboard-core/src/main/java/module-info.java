module org.wicketstuff.dashboard {
	exports org.wicketstuff.dashboard;
	exports org.wicketstuff.dashboard.web;
	exports org.wicketstuff.dashboard.web.util;

	requires org.apache.wicket.core;
	requires org.apache.wicket.request;
	requires org.apache.wicket.util;
	requires xstream;
	requires com.google.gson;
}
