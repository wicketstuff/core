package org.wicketstuff.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

public class ShiroServletRequestModel extends
		LoadableDetachableModel<HttpServletRequest> {
	
	@Override
	protected HttpServletRequest load() {
		Request request = RequestCycle.get().getRequest();
		return ((ServletWebRequest) request).getHttpServletRequest();
	}
}
