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

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.wicketstuff.push.cometd.CometdService;
import org.wicketstuff.push.timer.TimerChannelService;

/**
 * A service providing channel based communication facility in wicket based applications.
 * <p>
 * Implementation of this interface are the basis of a channel based communication implementation.
 * You usually store one IChannelService implementation in your application instance,
 * and then delegate all your channel related operations to this service,
 * allowing very easy switching between channel communication implementations.
 * <p>
 * Here is how you usually use an IChannelService implementation:
 * <pre>
 *  IChannelService channelService = MyApplication.get().getChannelService();
 *   // I want to send an event when i click a button
 *  [...]
 *    onClick(AjaxRequestTarget target){
 *      channelService.publish(new ChannelEvent("channel"));
 *    }
 *  [...]
 *
 *  // All pages listening this event should add
 *  [...]
 *    channelService.addChannelListener(this, "channel", new IChannelListener() {
 *      public void onEvent(String channel, Map datas, IChannelTarget target){
            target.[...]
          }
 *    });
 *  [...]
 * </pre>
 *
 * @author Xavier Hanin
 *
 * @see TimerChannelService
 * @see CometdService
 * @see IChannelTarget
 */
public interface IChannelService {
  /**
   * Adds a behavior to the given component so that it will be notified of
   * {@link ChannelEvent}s.
   * <p>
   * Usually the component if a {@link Page}, even if it's not mandatory.
   * Indeed the components reacting to the event are defined in the {@link IChannelListener},
   * by calling {@link IChannelTarget} methods. Hence any component on the page
   * can serve as the host of the behavior, as soon as it is visible.
   *
   * @param component the component to which the behavior should be added
   * @param channel the channel for which the listener should be notified
   * @param listener the {@link IChannelListener} which should be notified of {@link ChannelEvent}s
   */
  void addChannelListener(Component component, String channel, IChannelListener listener);
  /**
   * Publishes a channel event which will be sent to the channel listeners.
   *
   * @param event the event to publish to the listeners
   */
  void publish(ChannelEvent event);

}
