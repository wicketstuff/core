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

import java.io.Serializable;

import org.apache.wicket.Component;

/**
 * A push service provides a simple way to install push facility
 * on a component, and then push events to the component using
 * a {@link IPushTarget}.
 * <p>
 * Implementations of {@link IPushService} must be thread safe,
 * and thus easily reusable.
 * <p>
 * Here is a common example of usage:
 * <pre>
 * private static final IPushInstaller PUSH_INSTALLER = new IPushInstaller() {
 *   public void install(Component component, final IPushTarget pushTarget) {
 *     myServerEventService.addListener(new MyListener() {
 * 		public void onEvent(Event ev) {
 *     		if (pushTarget.isConnected()) {
 *				pushTarget.addComponent(myComponentToUpdate);
 *				pushTarget.appendJavascript("alert('event received from server:"+ev+"');");
 *				pushTarget.trigger();
 * 			} else {
 *				myServerEventService.removeListener(this);
 *			}
 *		}
 *     });
 *   }
 * }
 *
 * ...
 *
 * pushService.installPush(myComponent, PUSH_INSTALLER);
 * </pre>
 *
 * Using a unique instance of push installer is highly recommended, since it is then referenced
 * in the Wicket Application (using a different instance each time could thus lead to
 * a memory leak). If you need to pass different instances each time, you are responsible
 * for calling uninstallPush on the component to free the reference to the installer.
 * <p>
 * This service is usually used when you already have a facility for triggering and
 * listening server side events that you want to propagate to the clients.
 * <p>
 * For a mechanism including an event publish/subscribe mechanism, see {@link IChannelService}
 *
 * @author Xavier Hanin
 *
 * @see IChannelService
 * @see IPushInstaller
 */
public interface IPushService extends Serializable {
	/**
	 * Installs a push facility on the given component.
	 * <p>
	 * The component on which the push facility is installed doesn't really matter
	 * as soon as it is visible, except that only one push service can be installed by component.
	 * <p>
	 * Usually the page is used as component.
	 *
	 * @param component the component on which the push facility must be installed
	 */
	IPushTarget installPush(Component component);

	/**
	 * Uninstalls a push installer which has previously been installed on
	 * a component.
	 * <p>
	 * Calling this method after calling {@link #installPush(Component, IPushInstaller)}
	 * is mandatory if you use a different push installer instance for each component
	 * instance.
	 *
	 * @param component the component on which push service must be uninstalled
	 */
	void uninstallPush(Component component);
}
