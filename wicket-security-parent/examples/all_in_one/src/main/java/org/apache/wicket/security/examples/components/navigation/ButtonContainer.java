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
package org.apache.wicket.security.examples.components.navigation;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.checks.LinkSecurityCheck;
import org.apache.wicket.security.components.markup.html.links.SecureBookmarkablePageLink;

/**
 * Simple container to display some menu buttons.
 * 
 * @author marrink
 * 
 */
public abstract class ButtonContainer extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * The buttons.
	 */
	protected Integer[] BUTTONS;

	/**
	 * Names for the buttons.
	 */
	protected String[] NAMES;

	/**
	 * The destination for the button clicks.
	 */
	protected List<Class< ? extends Page>> PAGES;

	/**
	 * 
	 * Construct.
	 * 
	 * @param id
	 * @param selectedButton
	 *            one of BUTTON_....
	 */
	public ButtonContainer(String id, final Integer selectedButton)
	{
		super(id);
		setupButtons();
		setRenderBodyOnly(true);
		ListView<Integer> buttons = new ListView<Integer>("buttons", Arrays.asList(BUTTONS))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Integer> item)
			{
				Integer button = item.getModelObject();
				SecureBookmarkablePageLink<Void> link =
					new SecureBookmarkablePageLink<Void>("button", PAGES.get(button.intValue()))
					{
						private static final long serialVersionUID = 1L;

						/**
						 * Overrides default behavior purely for the sake of the
						 * stylesheet. The wicket default already does a good job in
						 * disabling the links
						 * 
						 * @see org.apache.wicket.markup.html.link.AbstractLink#disableLink(org.apache.wicket.markup.ComponentTag)
						 */
						@Override
						protected void disableLink(final ComponentTag tag)
						{
							// if the tag is an anchor proper
							if (tag.getName().equalsIgnoreCase("a")
								|| tag.getName().equalsIgnoreCase("link")
								|| tag.getName().equalsIgnoreCase("area"))
							{
								// Remove any href from the old link
								tag.remove("href");

								tag.put("onclick", "return false;");
								tag.put("id", "disabledButton");
							}
							// if the tag is a button or input
							else if ("button".equalsIgnoreCase(tag.getName())
								|| "input".equalsIgnoreCase(tag.getName()))
							{
								tag.put("disabled", "disabled");
							}
						}
					};
				// always render the buttons unless the link itself does not
				// have enough rights
				((LinkSecurityCheck) link.getSecurityCheck()).setUseAlternativeRenderCheck(true);
				link.add(new Label("title", NAMES[button.intValue()]).setRenderBodyOnly(true));
				item.add(link);
				// stylesheet stuff
				if (selectedButton.equals(button))
				{
					link.add(new AttributeModifier("id", true, new Model<String>("activeButton")));
				}
				item.setRenderBodyOnly(true);
			}
		};
		add(buttons);
	}

	/**
	 * Allows subclasses to setup the buttons.
	 */
	protected abstract void setupButtons();
}
