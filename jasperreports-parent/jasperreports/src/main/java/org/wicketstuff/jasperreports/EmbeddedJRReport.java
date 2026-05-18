/*
 * $Id$
 * $Revision$ $Date$
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.jasperreports;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Component for embedding a jasper report in a page. This component must be attached to an
 * &lt;object&gt; tag. If you don't want to embed the report, but have a link to it instead, use
 * {@link ResourceReference}.
 *
 * @author <a href="mailto:evanchooly@gmail.com">Justin Lee</a>
 */
public final class EmbeddedJRReport extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private final JRResource resource;
	private final ResourceReference ref = new ResourceReference(EmbeddedJRReport.class, "report") {
		@Override
		public IResource getResource() {
			return new AbstractResource() {
				@Override
				protected ResourceResponse newResourceResponse(Attributes attributes) {
					ResourceResponse resp = resource.newResourceResponse(attributes);
					resp.setContentDisposition(ContentDisposition.INLINE);
					return resp;
				}
			};
		}
	};

	/**
	 * Construct.
	 *
	 * @param componentID
	 *            component componentID
	 * @param resource
	 *            the resource
	 */
	public EmbeddedJRReport(String componentID, JRResource resource)
	{
		super(componentID);
		this.resource = resource;
	}

	/**
	 * Make sure we work only with object tags
	 *
	 * @param tag
	 *            tag applied to component.
	 * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {
		checkComponentTag(tag, "object");
		tag.put("data", RequestCycle.get().urlFor(ref, null));
		tag.put("type", resource.getContentType());
		super.onComponentTag(tag);
	}
}
