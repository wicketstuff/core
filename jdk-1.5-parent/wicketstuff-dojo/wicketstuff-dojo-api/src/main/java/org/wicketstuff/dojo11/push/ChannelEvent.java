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
package org.wicketstuff.dojo11.push;

import java.util.HashMap;
import java.util.Map;


/**
 * A event containing data to send by server pushing.
 * This event has to be published by {@link IChannelPublisher} instance.
 * <p>
 * a pushEvent is specific of a channel represented by a string and can contains some
 * datas to send to the listener.
 * </p>
 *
 *
 * @author Vincent Demay
 */
public class ChannelEvent
{
	private String channel;
	private final HashMap<String, Object> _data;
	private String id = "0";

	/**
	 * Construct.
	 * @param channel channel where the event will be sent
	 */
	public ChannelEvent(final String channel){
		this.channel = channel;
		_data = new HashMap<String, Object>();
	}

	/**
	 * return the channel of the event
	 * @return the channel of the event
	 */
	public String getChannel()
	{
		return channel;
	}

	/**
	 * set the channel of the event
	 * @param channel event channel
	 */
	public void setChannel(final String channel)
	{
		this.channel = channel;
	}

	/**
	 * Same as {@link #addData(String, Object)} but here to keep binary compatibility
	 * 
	 * @param key
	 * @param data
	 * @return this
	 */
	public ChannelEvent addData(String key, String data) {
		_data.put(key, data);
		return this;
	}
	
	/**
	 * Add a data in the event. This event will be transfered to
	 * all the listener of the channel
	 * @param key data key
	 * @param data data to add in the event
	 * @return this
	 */
	public ChannelEvent addData(final String key, final Object data){
		_data.put(key, data);
		return this;
	}
	
	/**
	 * Returns all data in the event
	 * @return all data in the event
	 */
	public Map<String, Object> getData(){
		return _data;
	}

	/**
	 * @return event id
	 */
	public String getId() {
		return id ;
	}

	/**
	 * @param id event id
	 */
	public void setId(final String id) {
		this.id = id;
	}


}
