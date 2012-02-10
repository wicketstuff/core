/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.portlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

/**
 * This class serves as a shortcut to all Portlet related attributes
 *
 * @author Peter Pastrnak
 */
public class ThreadPortletContext {
	public static ServletContext getServletContext() {
		Application application = ThreadContext.getApplication();
		return (application != null) ? ((WebApplication) application).getServletContext() : null;
	}

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) ThreadContext.getRequestCycle().getRequest().getContainerRequest();
	}
	
	public static HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) ThreadContext.getRequestCycle().getResponse().getContainerResponse();
	}
	
	public static PortletConfig getPortletConfig() {
		HttpServletRequest servletRequest = getHttpServletRequest();
		return (servletRequest != null) ? (PortletConfig) servletRequest.getAttribute("javax.portlet.config") : null;
	}
	
	public static PortletRequest getPortletRequest() {
		HttpServletRequest servletRequest = getHttpServletRequest();
		return (servletRequest != null) ? (PortletRequest) servletRequest.getAttribute("javax.portlet.request") : null;
	}
	
	public static PortletResponse getPortletResponse() {
		HttpServletRequest servletRequest = getHttpServletRequest();
		return (servletRequest != null) ? (PortletResponse) servletRequest.getAttribute("javax.portlet.response") : null;
	}
	
	public static PortletMode getPortletMode() {
		PortletRequest portletRequest = getPortletRequest();
		return (portletRequest != null) ? portletRequest.getPortletMode() : null;
	}
	
	public static PortletPreferences getPortletPreferences() {
		PortletRequest portletRequest = getPortletRequest();
		return (portletRequest != null) ? portletRequest.getPreferences() : null;
	}
	
	public static String getNamespace() {
		PortletResponse portletResponse = getPortletResponse();
		return (portletResponse != null) ? portletResponse.getNamespace() : "";
	}
	
	public static String getWindowID() {
		PortletRequest portletRequest = getPortletRequest();
		return (portletRequest != null) ? portletRequest.getWindowID() : null;
	}
	
	public static boolean isAjax() {
		RequestCycle requestCycle = ThreadContext.getRequestCycle();
		return (requestCycle != null) ? ((WebRequest) requestCycle.getRequest()).isAjax() : false;
	}
	
	public static boolean isResourceRequest() {
		PortletRequest portletRequest = getPortletRequest();
		return (portletRequest != null) && (portletRequest.getAttribute(PortletRequest.LIFECYCLE_PHASE).equals(PortletRequest.RESOURCE_PHASE));
	}
	
	public static boolean isEmbedded() {
		return !((isAjax()) || (isResourceRequest()));
	}
}
