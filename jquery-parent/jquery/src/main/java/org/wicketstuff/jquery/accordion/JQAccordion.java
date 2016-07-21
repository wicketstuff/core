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

package org.wicketstuff.jquery.accordion;

import java.util.Iterator;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * JQuery based implementation of client side accordion.
 * <p>
 * This component uses JQuery and the accordion plugin to create client side
 * accordion. Being client side, all content is rendered and sent to the client
 * at first time.
 * <p>
 * This component is abstract and must be subclassed similarly to
 * {@link RefreshingView}. Each accordion item to be populated must have two sub
 * components: a 'title', and a 'content'. You can use Label for these, or for
 * complex content use a {@link Panel} or a {@link WebMarkupContainer}.
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * new JQAccordion(&quot;accordion1&quot;) {
 * 	private static final long serialVersionUID = 1L;
 * 
 * 	protected Iterator getItemModels() {
 * 		return new ArrayIteratorAdapter(new Object[] { &quot;A&quot;, &quot;B&quot;, &quot;C&quot; }) {
 * 
 * 			protected IModel model(Object obj) {
 * 				return new Model((Serializable) obj);
 * 			}
 * 		};
 * 	}
 * 
 * 	protected void populateItem(Item item) {
 * 		item.add(new Label(&quot;title&quot;, item.getModel()));
 * 		item.add(new Label(&quot;content&quot;, item.getModelObjectAsString()
 * 				+ &quot; content&quot;));
 * 	}
 * }
 * </pre>
 * 
 * JQuery: http://jquery.com/ <br/>
 * Accordion: http://bassistance.de/jquery-plugins/jquery-plugin-accordion/
 * <br/>
 * 
 * @author Xavier Hanin
 */
public abstract class JQAccordion extends Panel {
	private static final long serialVersionUID = 1L;

	private String options;

	/**
	 * Constructs an accordion component with the given id.
	 * 
	 * @param id
	 *            the id of the component. Must not be <code>null</code>.
	 */
	public JQAccordion(String id) {
		this(id, "");
	}

	/**
	 * Constructs an accordion component with the given id and options.
	 * <p>
	 * See <a
	 * href="http://bassistance.de/jquery-plugins/jquery-plugin-accordion/"
	 * >accordion plugin documentation</a> for details about options.
	 * 
	 * @param id
	 *            the id of the component. Must not be <code>null</code>.
	 * @param options
	 *            the options to use for the javascript accordion component.
	 *            Must not be <code>null</code>.
	 */
	public JQAccordion(String id, String options) {
		super(id);
		this.options = options;

		add(new JQueryBehavior());

		final WebMarkupContainer parent = new WebMarkupContainer("accordion");
		parent.setOutputMarkupId(true);
		add(parent);

		/*
		 * we inject the script in the component body and not as a header
		 * contribution because the script needs to be called each time the
		 * component is refreshed using wicket ajax support.
		 */
		add(new Label("script", new Model<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				String options = getOptions().trim();
				if (options.length() > 0) {
					options = "header: 'h3', " + options;
				} else {
					options = "header: 'h3'";
				}
				return "$('#" + parent.getMarkupId() + "').Accordion({"
						+ options + "});";
			}
		}).setEscapeModelStrings(false));

		parent.add(newRepeatingView("item"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.render(CssHeaderItem
				.forReference(new PackageResourceReference(JQAccordion.class,
						"jquery.accordion.css")));
		response.render(JavaScriptHeaderItem
				.forReference(new PackageResourceReference(JQAccordion.class,
						"jquery.accordion.pack.js")));
	}

	protected String getOptions() {
		return options;
	}

	protected RefreshingView<String> newRepeatingView(String id) {
		return new RefreshingView<String>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<String>> getItemModels() {
				return JQAccordion.this.getItemModels();
			}

			@Override
			protected void populateItem(Item<String> item) {
				JQAccordion.this.populateItem(item);
			}
		};
	}

	/**
	 * Populates one accordion panel item.
	 * <p>
	 * Two components must be added to the item:
	 * <ul>
	 * <li>"title"</li>
	 * the title of the panel
	 * <li>"content"</li>
	 * the content of the panel
	 * </ul>
	 * 
	 * @param item
	 *            the item to populate
	 */
	protected abstract void populateItem(Item<String> item);

	protected abstract Iterator<IModel<String>> getItemModels();

}
