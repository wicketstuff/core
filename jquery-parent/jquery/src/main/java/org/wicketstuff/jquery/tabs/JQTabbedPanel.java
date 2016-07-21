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

package org.wicketstuff.jquery.tabs;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * JQuery based implementation of client side tabbed panel.
 * <p>
 * This component JQuery and the plugin tabs to create client side tabs. Being
 * client side, all tabs will be rendered and sent to the client at first time,
 * which can be useful in forms, for instance.
 * <p>
 * The API of this component is the same as {@link TabbedPanel}, and can thus be
 * used as a drop in replacement of {@link TabbedPanel}.
 * <p>
 * 
 * JQuery: http://jquery.com/ <br/>
 * Tabs: http://stilbuero.de/jquery/tabs/ <br/>
 * 
 * 
 * @author Xavier Hanin
 * @see TabbedPanel
 */
public class JQTabbedPanel extends Panel {
	private static final long serialVersionUID = 1L;
	/**
	 * id used for child panels
	 */
	private static final String TAB_PANEL_ID = "panel";
	private String options;

	/**
	 * Constructs a JQTabbedPanel with the given id and list of tabs.
	 * <p>
	 * This constructor can be used as a drop in replacement of
	 * <code>new TabbedPanel(id, tabs)</code>
	 * 
	 * @param id
	 *            component id. Must not be null
	 * @param tabs
	 *            list of ITab objects used to represent tabs. Must not be null.
	 */
	public JQTabbedPanel(String id, List<ITab> tabs) {
		this(id, tabs, "");
	}

	/**
	 * Constructs a JQTabbedPanel with the given id, list of tabs and options.
	 * <p>
	 * The options are used when initializing the tabs.
	 * <p>
	 * See <a href="http://stilbuero.de/jquery/tabs/">tabs documentation</a> for
	 * details on the available options.
	 * <p>
	 * Some examples:<br/>
	 * <code>{ fxSlide: true }</code><br/>
	 * <code>{ fxFade: true, fxSpeed: 'fast' }</code><br/>
	 * 
	 * @param id
	 *            the id of this component. Must not be null.
	 * @param tabs
	 *            the list of tabs to use in this component. Must not be null.
	 * @param options
	 *            the options to use to init the tabs component. May be null.
	 */
	public JQTabbedPanel(String id, List<ITab> tabs, String options) {
		super(id);
		this.options = options == null ? "" : options;

		add(new JQueryBehavior());
		final WebMarkupContainer parent = new WebMarkupContainer("tabs");
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
				return "$('#" + parent.getMarkupId() + "').tabs("
						+ getTabsOptions() + ");";
			}
		}).setEscapeModelStrings(false));

		RepeatingView titles = new RepeatingView("tab");
		parent.add(titles);
		RepeatingView contents = new RepeatingView("content");
		parent.add(contents);

		for (int i = 0; i < tabs.size(); i++) {
			final WebMarkupContainer content = tabs.get(i).getPanel(
					TAB_PANEL_ID + i);
			content.setOutputMarkupId(true);
			contents.add(content);
			WebMarkupContainer title = new WebMarkupContainer(TAB_PANEL_ID
					+ "-title" + i);
			title.setOutputMarkupId(true);
			title.add(new AbstractLink("link") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag) {
					super.onComponentTag(tag);
					tag.put("href", "#" + content.getMarkupId());
				}
			}.add(new Label("title", tabs.get(i).getTitle())));
			titles.add(title);
		}
	}

	/**
	 * Returns the options to use when initializing the tabs.
	 * <p>
	 * See <a href="http://stilbuero.de/jquery/tabs/">tabs documentation</a> for
	 * details on the available options.
	 * <p>
	 * Some examples: <code>{ fxSlide: true }</code>
	 * <code>{ fxFade: true, fxSpeed: 'fast' }</code>
	 * 
	 * @return the options to use to init the tabs
	 */
	protected String getTabsOptions() {
		return options;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.render(CssHeaderItem
				.forReference(new PackageResourceReference(JQTabbedPanel.class,
						"jquery.tabs.css")));
		response.render(JavaScriptHeaderItem
				.forReference(new PackageResourceReference(JQTabbedPanel.class,
						"jquery.tabs.pack.js")));

	}

}
