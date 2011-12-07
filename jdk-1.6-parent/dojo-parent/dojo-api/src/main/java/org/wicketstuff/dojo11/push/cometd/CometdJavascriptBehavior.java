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
package org.wicketstuff.dojo11.push.cometd;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.collections.MiniMap;
import org.wicketstuff.dojo11.push.IChannelListener;
import org.wicketstuff.dojo11.push.IChannelTarget;
import org.wicketstuff.dojo11.templates.DojoPackagedTextTemplate;

/**
 * @author stf
 */
public class CometdJavascriptBehavior extends CometdBehavior
{

	/**
	 * name of the default callback, that evals the evalscript property of a message
	 */
	public static final String DEFAULT_CALLBACK = "WicketDojo.Cometd._eval";

	private static final IChannelListener DUMMY_LISTENER = new IChannelListener() {

		public void onEvent(String channel, Map<String, String> data, IChannelTarget target)
		{
			throw new WicketRuntimeException("unexpected dummy listener event");
		}
		
	};
	
	private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
	
	private final String _callbackMethod;

	private final String _behaviorId = "js" + NEXT_ID.getAndIncrement();
	
	/**
	 * Construct.
	 * @param channelId
	 */
	public CometdJavascriptBehavior(String channelId)
	{
		this(channelId, null);
	}
	
	/**
	 * Construct.
	 * @param channelId
	 * @param callbackMethod 
	 */
	public CometdJavascriptBehavior(String channelId, String callbackMethod)
	{
		this(channelId, callbackMethod, null);
	}

	/**
	 * Construct.
	 * @param channelId
	 * @param callbackMethod 
	 * @param listener 
	 */
	public CometdJavascriptBehavior(String channelId, String callbackMethod, IChannelListener listener)
	{
		super(channelId, listener == null ? DUMMY_LISTENER : listener);
		if (callbackMethod == null || "".equals(callbackMethod)) {
			if (getListener() != DUMMY_LISTENER) {
				throw new WicketRuntimeException("can't use default callback with listener");
			}
			_callbackMethod = DEFAULT_CALLBACK;
		} else {
			_callbackMethod = callbackMethod;
		}
	}
	
	/**
	 * @see org.wicketstuff.dojo11.push.cometd.CometdAbstractBehavior#getCometdInterceptorScript()
	 */
	@Override
	public String getCometdInterceptorScript()
	{
		final MiniMap map = new MiniMap(3);
		map.put("markupId", isComponentIndependent() ? _behaviorId : getComponent().getMarkupId());
		map.put("url", isComponentIndependent() ? "" : getCallbackUrl().toString());
		map.put("callback", _callbackMethod);
		return new DojoPackagedTextTemplate(CometdBehavior.class, "CometdJavascriptBehaviorTemplate.js")
						.asString(map);
	}

	/**
	 * @see org.wicketstuff.dojo11.push.cometd.CometdAbstractBehavior#getPartialSubscriber()
	 */
	public CharSequence getPartialSubscriber() {
		return isComponentIndependent() ?  "'onEventFor"+ _behaviorId + "'" : super.getPartialSubscriber();
	}
	
	protected String getJavascriptId()
	{
		// don't add component
		return isComponentIndependent() ? _behaviorId + "@" + getChannelId() : super.getJavascriptId();
	}
	
	private boolean isComponentIndependent() {
		return (getListener() == DUMMY_LISTENER);
	}
}
