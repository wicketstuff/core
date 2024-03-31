/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.jeeweb;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Iterator;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The JEEWebResolver is used to embed Servlet, JSP and JSF content into wicked HTML pages, by a
 * custom Wicket-Tag. It is tested with Wicket 6.x / 7.x. Because include is used to apply the
 * content, every restrictions of include is applied. (No header modifications and so on) To use it
 * you should registered it to the page settings in the init-Method of the Wicket-Application:
 * <code><pre>
 * 	{@literal @}Override
 * 	protected void init() {
 * 		super.init();
 * 		getPageSettings().addComponentResolver(new JEEWebResolver());
 * 	}
 * </pre></code> A tag specifies the location which JSP to load. (The argument is given to the
 * getRequestDispatcher method of the ServletContext):
 * <code><pre>&lt;wicket:jsp file="/de/test/jspwicket/TestPage.jsp"&gt;&lt;/wicket:jsp&gt;</pre></code>
 * or
 * <code><pre>&lt;wicket:servlet path="/de/test/jspwicket/Servlet"&gt;&lt;/wicket:servlet&gt;</pre></code>
 * or <code><pre>&lt;wicket:jsf file="/Page.xhtml"&gt;&lt;/wicket:jsf&gt;</pre></code> <b>Links:</b><br>
 * https://cwiki.apache.org/confluence/display/WICKET/Including+JSP+files+in+ HTML+templates<br>
 * http://apache-wicket.1842946.n4.nabble.com/Wicket-1-5-and-JSP-servlet- wrapping-td4407174.html<br>
 * <br>
 *
 * @see org.apache.wicket.protocol.http.WebApplication.init()
 *
 * @author Tobias Soloschenko
 */
public class JEEWebResolver implements IComponentResolver
{

	private static final long serialVersionUID = 1L;

	private static final String SERVLET_AND_JSP_ENCODING = "UTF-8";

	private static final Logger LOGGER = LoggerFactory.getLogger(JEEWebResolver.class);

	// Registration of the tag identifier
	static
	{
		if (LOGGER.isTraceEnabled())
		{
			LOGGER.trace("Registering" + JEEWebResolver.class.getName());
		}
		WicketTagIdentifier.registerWellKnownTagName("jsp");
		WicketTagIdentifier.registerWellKnownTagName("jsf");
		WicketTagIdentifier.registerWellKnownTagName("servlet");
	}

	@Override
	public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag)
	{
		if (tag instanceof WicketTag)
		{
			WicketTag wtag = (WicketTag)tag;
			if ("jsp".equalsIgnoreCase(wtag.getName()))
			{
				String file = wtag.getAttributes().getString("file");
				if (file == null || file.trim().length() == 0)
				{
					throw new MarkupException(
						"Wrong format of <wicket:jsp file='/foo.jsp'>: attribute 'file' is missing");
				}
				return new ServletAndJspFileContainer(file, Type.JSP);
			}
			else if ("jsf".equalsIgnoreCase(wtag.getName()))
			{
				String file = wtag.getAttributes().getString("file");
				if (file == null || file.trim().length() == 0)
				{
					throw new MarkupException(
						"Wrong format of <wicket:jsf file='/foo.xhtml'>: attribute 'file' is missing");
				}
				return new ServletAndJspFileContainer(file, Type.JSF);
			}
			else if ("servlet".equalsIgnoreCase(wtag.getName()))
			{
				String path = wtag.getAttributes().getString("path");
				if (path == null || path.trim().length() == 0)
				{
					throw new MarkupException(
						"Wrong format of <wicket:servlet path='/Test'>: attribute 'path' is missing");
				}
				return new ServletAndJspFileContainer(path, Type.SERVLET);
			}
		}
		return null;
	}

	/**
	 * The JSP container which contains the JSP output and renders it to the Wicket HTML page
	 */
	private static class ServletAndJspFileContainer extends MarkupContainer
	{

		private static final long serialVersionUID = -4296125929087527034L;

		private final String resource;

		private final Type type;

		public ServletAndJspFileContainer(String resource, Type type)
		{
			super(resource);
			this.resource = resource;
			this.type = type;
		}

		/**
		 * Required so that the tag don't have to be opened / closed. It can also be defined as
		 * empty tag.
		 */
		@Override
		protected void onComponentTag(final ComponentTag tag)
		{
			tag.setType(XmlTag.TagType.OPEN);
			super.onComponentTag(tag);
		}

		/**
		 * Renders the component tag body with the content of the JSP output
		 */
		@Override
		public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
		{

			// Get the everything required to include the jsp file
			RequestCycle cycle = getRequestCycle();
			ServletRequest request = (HttpServletRequest)cycle.getRequest().getContainerRequest();
			JSPIncludeHttpServletResponseWrapper response = new JSPIncludeHttpServletResponseWrapper(
				(HttpServletResponse)cycle.getResponse().getContainerResponse());
			ServletContext context = ((WebApplication)Application.get()).getServletContext();

			// Handle a missing jsp file
			handleMissingResource(context);

			try
			{
				// include the JSP file by the given request / response
				context.getRequestDispatcher(resource).include(request, response);

				// replace the component tag body with the result of the JSP
				// output
				replaceComponentTagBody(markupStream, openTag, response.getOutput());
			}
			catch (ServletException | IOException e)
			{
				throw new WicketRuntimeException(e);
			}

		}

		/**
		 * Handles missing files.
		 *
		 * @param context
		 *            the servlet context
		 * @throws WicketRuntimeException
		 *             if the resource file couldn't be resolved or an exception should be thrown if
		 *             it is missing
		 */
		private void handleMissingResource(ServletContext context) throws WicketRuntimeException
		{
			try
			{
				if (type == Type.JSP || type == Type.JSF)
				{
					if (context.getResource(resource) == null)
					{
						promptMissingResource(context, type);
					}
				}
				else
				{

					boolean found = false;
					Iterator<? extends ServletRegistration> servletRegistrationIterator = context
						.getServletRegistrations().values().iterator();
					while (servletRegistrationIterator.hasNext())
					{
						Iterator<String> mappingsIterator = servletRegistrationIterator.next()
							.getMappings().iterator();
						while (mappingsIterator.hasNext())
						{
							String mapping = mappingsIterator.next();
							if (resource.equals(mapping))
							{
								found = true;
							}
						}
					}
					if (!found)
					{
						promptMissingResource(context, type);
					}

				}
			}
			catch (MalformedURLException e)
			{
				throw new WicketRuntimeException(e);
			}
		}

		/**
		 * This method throws an exception if wicket is configured to throw an exception for missing
		 * resources, or gives a warning message through the logging mechanism.
		 *
		 * @param context
		 *            the context to be printed.
		 * @param type
		 *            the type
		 */
		private void promptMissingResource(ServletContext context, Type type)
		{
			if (shouldThrowExceptionForMissingFile())
			{
				throw new WicketRuntimeException(String.format(
					"Cannot locate resource %s of type: %s within current context: %s", resource,
					type.toString(), context.getContextPath()));
			}
			else
			{
				LOGGER
					.warn(
						"Resource of type: {} will not be processed. Cannot locate it {} within current context: {}",
						type.toString(), resource, context.getContextPath());
			}
		}

		/**
		 * Checks if an exception should be thrown, if a resource file is missing.
		 *
		 * @return if an exception should be thrown
		 */
		private boolean shouldThrowExceptionForMissingFile()
		{
			return Application.get().getResourceSettings().getThrowExceptionOnMissingResource();
		}
	}

	/**
	 * The ByteArrayServletOutputStream writes bytes to the given ByteArrayOutputStream
	 */
	private static class ByteArrayServletOutputStream extends ServletOutputStream
	{
		private ByteArrayOutputStream baos;

		public ByteArrayServletOutputStream(ByteArrayOutputStream baos)
		{
			this.baos = baos;
		}

		@Override
		public void write(int param) throws IOException
		{
			baos.write(param);
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
		}
	}

	/**
	 * This HttpServletResponseWrapper is used to get the JSP's output by including it with a
	 * request dispatcher.
	 */
	private static class JSPIncludeHttpServletResponseWrapper extends HttpServletResponseWrapper
	{

		private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		private final ServletOutputStream byteArrayServletOutputStream = new ByteArrayServletOutputStream(
			byteArrayOutputStream);
		private final PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);

		public JSPIncludeHttpServletResponseWrapper(HttpServletResponse response)
		{
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream()
		{
			return byteArrayServletOutputStream;
		}

		@Override
		public PrintWriter getWriter() throws IOException
		{
			return printWriter;
		}

		public String getOutput() throws UnsupportedEncodingException
		{
			// if something has been written with the writer - we need to flush
			// it!
			printWriter.flush();
			return byteArrayOutputStream.toString(SERVLET_AND_JSP_ENCODING);
		}
	}

	/**
	 * If the markup container is a jsp or a servlet
	 */
	private enum Type
	{
		JSP, JSF, SERVLET
	}

}
