/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.logback;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Implementation of {@link AbstractWebFormattingConverter} that uses wicket to
 * locate the current {@link HttpServletRequest} object in {@link #getRequest()}
 * method. Used by {@link WicketWebPatternEncoder}.
 * 
 * @author akiraly
 */
public class WicketWebFormattingConverter extends
		AbstractWebFormattingConverter {

	@Override
	protected HttpServletRequest getRequest() {
		RequestCycle cycle = RequestCycle.get();
		if (cycle == null)
			return null;

		Request request = cycle.getRequest();

		if (request == null)
			return null;

		Object containerRequest = request.getContainerRequest();

		if (!(containerRequest instanceof HttpServletRequest))
			return null;

		return (HttpServletRequest) containerRequest;
	}
}
