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
package org.wicketstuff.push;

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
 * @author Rodolfo Hansen
 */
public class ChannelEvent
{
	private String channel;
	private final Map<String, String> data;
  private String id = "0";

	/**
	 * Construct.
	 * @param channel channel where the event will be sent
	 */
	public ChannelEvent(final String channel){
		this.channel = channel;
		data = new HashMap<String, String>();
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
	 * Add a data in the event. This event will be transfered to
	 * all the listener of the channel
	 * @param key data key
	 * @param data data to add in the event
	 */
	public void addData(final String key, final String data){
		this.data.put(key, data);
	}

	/**
	 * Returns all data in the event
	 * @return all data in the event
	 */
	public Map<String, String> getData(){
		return data;
	}

  public String getId() {
    return id ;
  }

  public void setId(final String id) {
    this.id = id;
  }


}
