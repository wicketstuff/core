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
package org.wicketstuff.push.cometd;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.cometd.server.CometdServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.push.IPushChannel;
import org.wicketstuff.push.IPushEventHandler;

/**
 * This behavior will be asked by client side when it will receive a cometd event associated with
 * the kind of event
 *
 * There is currently no support for multiple cometd servlets. It is not possible to override which
 * URL to use via the {@link #getCometdServletPath()} overridable method. But two cometd instances
 * cannot be used simultaneously.
 *
 * @author Xavier Hanin
 * @author Rodolfo Hansen
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CometdPushBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(CometdPushBehavior.class);

	private static final String DEFAULT_COMETD_PATH = guessCometdServletPath();

	private static final ResourceReference COMETD = new CompressedResourceReference(
		CometdPushBehavior.class, "org/cometd.js");
	private static final ResourceReference COMETD_ACK = new CompressedResourceReference(
		CometdPushBehavior.class, "org/cometd/AckExtension.js");
	private static final ResourceReference COMETD_RELOAD = new CompressedResourceReference(
		CometdPushBehavior.class, "org/cometd/ReloadExtension.js");
	private static final ResourceReference COMETD_TIMESTAMP = new CompressedResourceReference(
		CometdPushBehavior.class, "org/cometd/TimeStampExtension.js");
	private static final ResourceReference COMETD_TIMESYNC = new CompressedResourceReference(
		CometdPushBehavior.class, "org/cometd/TimeSyncExtension.js");

	private static final PackagedTextTemplate TEMPLATE_INIT = new PackagedTextTemplate(
		CometdPushBehavior.class, "CometdPushInit.js");
	private static final PackagedTextTemplate TEMPLATE_EVENT_HANDLER = new PackagedTextTemplate(
		CometdPushBehavior.class, "CometdPushEventHandlerTemplate.js");
	private static final PackagedTextTemplate TEMPLATE_SUBSCRIBE = new PackagedTextTemplate(
		CometdPushBehavior.class, "CometdPushSubscribeTemplate.js");

	/**
	 * Parse the web.xml to find cometd context Path. This context path will be cache for all the
	 * application
	 *
	 * @return cometd context path
	 */
	private static String guessCometdServletPath()
	{
		final ServletContext servletContext = ((WebApplication)Application.get()).getServletContext();
		final InputStream is = servletContext.getResourceAsStream("/WEB-INF/web.xml");

		/*
		 * get the servlet name from class assignable to org.mortbay.cometd.CometdServlet
		 */
		try
		{
			final XmlPullParser parser = new XmlPullParser();
			parser.parse(is);
			String urlPattern = null;

			while (true)
			{
				XmlTag elem;
				// go down until servlet is found
				do {
          elem = (XmlTag)parser.nextTag();
        } while (elem != null && !(elem.getName().equals("servlet") && elem.isOpen()));

				// stop if elem is null
				if (elem == null) {
          break;
        }

				// get the servlet name for org.mortbay.cometd.CometdServlet
				String servletName = null, servletClassName = null;
				do
				{
					elem = (XmlTag)parser.nextTag();
					if (elem.isOpen()) {
            parser.setPositionMarker();
          } else if (elem.isClose() && elem.getName().equals("servlet-name")) {
            servletName = parser.getInputFromPositionMarker(elem.getPos()).toString();
          } else if (elem.isClose() && elem.getName().equals("servlet-class")) {
            servletClassName = parser.getInputFromPositionMarker(elem.getPos())
							.toString();
          }
				}
				while (servletClassName == null ||
					!CometdServlet.class.isAssignableFrom(Class.forName(servletClassName)));

				if (servletName == null) {
          break;
        }

				// go down until servlet-mapping is found
				do {
          elem = (XmlTag)parser.nextTag();
        } while (elem != null && !(elem.getName().equals("servlet-mapping") && elem.isOpen()));

				// stop if elem is null
				if (elem == null) {
          break;
        }

				// get the servlet name for org.mortbay.cometd.CometdServlet
				String servletNameMapping = null;
				do
				{
					elem = (XmlTag)parser.nextTag();
					if (elem.isOpen()) {
            parser.setPositionMarker();
          } else if (elem.isClose() && elem.getName().equals("servlet-name")) {
            servletNameMapping = parser.getInputFromPositionMarker(elem.getPos())
							.toString();
          }
				}
				while (!servletName.equals(servletNameMapping));

				// and the urlPattern
				do
				{
					elem = (XmlTag)parser.nextTag();
					if (elem.isOpen()) {
            parser.setPositionMarker();
          } else if (elem.isClose() && elem.getName().equals("url-pattern")) {
            urlPattern = parser.getInputFromPositionMarker(elem.getPos()).toString();
          }
				}
				while (urlPattern == null);

				// all it is found
				break;
			}

			if (urlPattern == null) {
        throw new ServletException("Error searching for cometd Servlet");
      }

			// Check for leading '/' and trailing '/*'.
			if (!urlPattern.startsWith("/") || !urlPattern.endsWith("/*")) {
        throw new ServletException(
					"Url pattern for cometd should start with / and finish with /*");
      }

			// Strip trailing '/*'.
			return servletContext.getContextPath() +
				urlPattern.substring(0, urlPattern.length() - 2);

		}
		catch (final Exception ex)
		{
			final String path = servletContext.getContextPath() + "/cometd";
			LOG.warn("Error finding filter cometd servlet in web.xml using default path " + path,
				ex);
			return path;
		}
	}

	private final String _cometdChannelId;
	private final String _cometdChannelIdWithoutSlash;

	private final Map<CometdPushChannel, IPushEventHandler> _handlers = new HashMap<CometdPushChannel, IPushEventHandler>(
		2);

	/**
	 * Construct a cometd Behavior
	 */
	CometdPushBehavior()
	{
		_cometdChannelIdWithoutSlash = UUID.randomUUID().toString().replaceAll("-", "");
		_cometdChannelId = "/" + _cometdChannelIdWithoutSlash;
	}

	/**
	 * @return JavaScript string containing the client side logic when a new event comes into the
	 *         channel.
	 */
	private String _renderEventHandlerScript()
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("COMETD_CHANNEL_ID", _cometdChannelIdWithoutSlash);
		map.put("CALLBACK_URL", getCallbackUrl().toString());

		return TEMPLATE_EVENT_HANDLER.asString(map);
	}

	/**
	 * Javascript allowing cometd to be initialized on commetd
	 *
	 * @return javascript to initialize cometd on client side
	 */
	private String _renderInitScript()
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		final RuntimeConfigurationType configurationType = Application.get().getConfigurationType();
		if (configurationType.equals(RuntimeConfigurationType.DEVELOPMENT)) {
      params.put("logLevel", "info");
    } else {
      params.put("logLevel", "error");
    }
		params.put("cometdServletPath", getCometdServletPath());
		return TEMPLATE_INIT.asString(params);
	}

	/**
	 * @return JavaScript to subscribe to a cometd channel and handle cometd events
	 */
	private String _renderSubscribeScript()
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("COMETD_CHANNEL_ID", _cometdChannelIdWithoutSlash);
		return TEMPLATE_SUBSCRIBE.asString(params);
	}

	<EventType> CometdPushChannel<EventType> addPushChannel(
		final IPushEventHandler<EventType> pushEventHandler)
	{
		final CometdPushChannel<EventType> channel = new CometdPushChannel<EventType>(
			_cometdChannelId);
		_handlers.put(channel, pushEventHandler);
		return channel;
	}

	/**
	 * get the channel where this behavior will wait for event
	 *
	 * @return channelId channel where this behavior will wait for event
	 */
	public String getCometdChannelId()
	{
		return _cometdChannelId;
	}

	/**
	 * Returns the behaviour's cometd servlet path.
	 *
	 * Uses the {@link #DEFAULT_COMETD_PATH} provided by {@link #guessCometdServletPath()}. Override
	 * if you have an unusual setup.
	 *
	 * @return the behaviour's cometd servlet path.
	 */
	protected String getCometdServletPath()
	{
		return DEFAULT_COMETD_PATH;
	}

	int removePushChannel(final IPushChannel<?> channel)
	{
		_handlers.remove(channel);
		return _handlers.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(Component c, final IHeaderResponse response)
	{
		super.renderHead(c, response);

		response.renderJavascriptReference(COMETD);

		// Add all extension...
		response.renderJavascriptReference(COMETD_ACK);
		response.renderJavascriptReference(COMETD_RELOAD);
		response.renderJavascriptReference(COMETD_TIMESTAMP);
		response.renderJavascriptReference(COMETD_TIMESYNC);

		response.renderJavascript(_renderInitScript(), "cometd-push-initialization");
		response.renderJavascript(_renderEventHandlerScript(), null);
		response.renderJavascript(_renderSubscribeScript(), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void respond(final AjaxRequestTarget target)
	{
		final CometdPushService pushService = CometdPushService.get();

		// retrieve all collected events and process them
		for (final Entry<CometdPushChannel, IPushEventHandler> entry : _handlers.entrySet()) {
      for (final Object event : pushService.pollEvents(entry.getKey())) {
        try
				{
					entry.getValue().onEvent(target, event);
				}
				catch (final RuntimeException ex)
				{
					LOG.error("Failed while processing event", ex);
				}
      }
    }
	}
}