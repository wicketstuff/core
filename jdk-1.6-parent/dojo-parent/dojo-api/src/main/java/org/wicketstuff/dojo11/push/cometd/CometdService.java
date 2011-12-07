package org.wicketstuff.dojo11.push.cometd;

import javax.servlet.ServletContext;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.cometd.Bayeux;
import org.cometd.Channel;
import org.cometd.RemoveListener;
import org.mortbay.cometd.BayeuxService;
import org.wicketstuff.dojo11.push.ChannelEvent;
import org.wicketstuff.dojo11.push.IChannelListener;
import org.wicketstuff.dojo11.push.IChannelService;


/**
 * Cometd based implementation of {@link IChannelService}.
 * <p>
 * This service is based on cometd client implemented by the dojo toolkit, which
 * must be embedded in your application.
 * <p>
 * This implementation relies on cometd for updating the page, but actually uses
 * regular cometd events, which, if a channel listener is properly installed on
 * a component of the page using
 * {@link #addChannelListener(Component, String, IChannelListener)}, will
 * trigger a wicket ajax call to get the page actually refreshed using regular
 * wicket ajax mechanisms.
 * <p>
 * This mean that each time an event is published, a new connection is made to
 * the server to get the actual page update required by the
 * {@link IChannelListener}.
 * 
 * @author Xavier Hanin
 * @author Rodolfo Hasen
 * 
 * @see IChannelService
 */
public class CometdService implements IChannelService
{

	/**
	 * 
	 */
	public static final String BAYEUX_CLIENT_PREFIX = "wicket-push";

	private BayeuxService _bayeuxService;

	private ServletContext _servletContext;

	/**
	 * Default constructor, make sure to call {@link #setServletContext(ServletContext)}
	 */
	public CometdService()
	{
		
	}
	
	/**
	 * Construct.
	 * @param servletContext
	 */
	public CometdService(ServletContext servletContext)
	{
		_servletContext = servletContext;
	}

	
	/**
	 * @return the servlet context
	 */
	public ServletContext getServletContext()
	{
		return _servletContext;
	}
	
	/**
	 * @param context the servlet context
	 */
	public void setServletContext(ServletContext context)
	{
		_servletContext = context;
	}

	/**
	 * @see org.wicketstuff.dojo11.push.IChannelService#addChannelListener(org.apache.wicket.Component,
	 *      java.lang.String, org.wicketstuff.dojo11.push.IChannelListener)
	 */
	@SuppressWarnings("deprecation")
	public void addChannelListener(final Component component, final String channel,
			final IChannelListener listener)
	{
		// handle calls with deprecated DummyChannelListener
		if (listener instanceof org.wicketstuff.dojo11.push.DummyChannelListener) {
			addJavascriptChannelListener(component, channel, null);
			return;
		}
		
		CometdBehavior behave = new CometdBehavior(channel, listener);
		component.add(behave);
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null)
		{
			target.prependJavascript(behave.getSubscriberScript().toString());
		}
	}


	/**
	 * @see org.wicketstuff.dojo11.push.IChannelService#removeChannelListener(org.apache.wicket.Component,
	 *      java.lang.String)
	 */
	public void removeChannelListener(Component component, String channel)
	{
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		for (final Object o : component.getBehaviors())
		{
			if (o instanceof CometdAbstractBehavior)
			{
				final CometdAbstractBehavior behave = (CometdAbstractBehavior)o;
				if (behave.getChannelId().equals(channel))
				{
					component.remove(behave);
					if (target != null)
					{
						target.prependJavascript(behave.getUnsuscribeScript().toString());
					}
				}
			}
		}
	}

	/**
	 * Cometd Specific method to Listen for client removals
	 * 
	 * @param channel
	 * @param listener
	 */
	public void addChannelRemoveListener(final String channel, final RemoveListener listener)
	{
		getBayeuxService().getClient().addListener(listener);
	}

	/**
	 * Implementation of {@link IChannelService#publish(ChannelEvent)}, which
	 * actually sends a cometd event to the client with a "proxy" attribute set
	 * to "true", which in turn triggers a wicket ajax call to get the listener
	 * notified and update the page.
	 * 
	 * @event the event to publish, which will be modify with "proxy" set to
	 *        "true"
	 */
	public void publish(final ChannelEvent event)
	{
		/*
		 * to avoid using implementation specific events, we set the proxy data
		 * here.
		 * 
		 * this property is used by CometdDefaultBehaviorTemplate.js to know
		 * that the event should actually be converted in a wicket ajax call to
		 * get the actual page refresh
		 */
		event.addData("proxy", "true");
		final Channel channel = getBayeuxService().getBayeux().getChannel("/" + event.getChannel(),
				true);
		channel.publish(getBayeuxService().getClient(), event.getData(), event.getId());
	}

	private final BayeuxService getBayeuxService()
	{
		if (_bayeuxService == null)
		{
			_bayeuxService = new BayeuxService((Bayeux)_servletContext
					.getAttribute(Bayeux.DOJOX_COMETD_BAYEUX), BAYEUX_CLIENT_PREFIX)
			{

			};
		}
		return _bayeuxService;
	}

	/**
	 * @see org.wicketstuff.dojo11.push.IChannelService#addJavascriptChannelListener(org.apache.wicket.Component, java.lang.String, java.lang.String)
	 */
	public void addJavascriptChannelListener(Component component, String channel, String javascriptMethod) {
		addJavascriptChannelListener(component, channel, javascriptMethod, null);
	}
	
	/**
	 * @see org.wicketstuff.dojo11.push.IChannelService#addJavascriptChannelListener(org.apache.wicket.Component, java.lang.String, java.lang.String, org.wicketstuff.dojo11.push.IChannelListener)
	 */
	public void addJavascriptChannelListener(Component component, String channel, String javascriptMethod, IChannelListener listener)
	{
		CometdJavascriptBehavior behave = new CometdJavascriptBehavior(channel, javascriptMethod, listener);
		component.add(behave);
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null)
		{
			target.prependJavascript(behave.getSubscriberScript().toString());
		}
	}

}
