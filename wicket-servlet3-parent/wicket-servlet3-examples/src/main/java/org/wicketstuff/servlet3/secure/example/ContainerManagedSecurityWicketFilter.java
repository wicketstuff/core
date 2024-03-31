/*
 * Copyright 2014 WicketStuff.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.servlet3.secure.example;

import static jakarta.servlet.DispatcherType.FORWARD;
import static jakarta.servlet.DispatcherType.REQUEST;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import org.apache.wicket.protocol.http.WicketFilter;

/**
 * Use servlet 3.0 Annotations to configure filter. Also set dispatcher types to
 * REQUEST and FORWARD. Request is default when not set but has to be set when
 * explicitly adding other dispatchers. FORWARD is set to allow the login page
 * to be served from the wicket filter. If this is not set and form-login-page
 * is set to a url of a wicket page the forward will not goto the wicket filter
 * and you will receive a 404.
 *
 * @author jsarman
 */
@WebFilter(value = "/cms/*", dispatcherTypes = {REQUEST, FORWARD},
		initParams = {
				@WebInitParam(name = "applicationClassName",
						value = "org.wicketstuff.servlet3.secure.example.ContainerManagedSecurityApp"),
				@WebInitParam(name = "filterMappingUrlPattern", value = "/cms/*"),
				@WebInitParam(name = "configuration", value = "deployment")
		}
)
public class ContainerManagedSecurityWicketFilter extends WicketFilter
{

}
