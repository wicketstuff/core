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
package org.apache.wicket.security.examples.multilogin.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.examples.multilogin.MySession;
import org.apache.wicket.security.examples.multilogin.components.navigation.ButtonContainer;
import org.apache.wicket.security.examples.multilogin.entities.Entry;
import org.apache.wicket.security.examples.pages.TopSecretPage;

/**
 * Page for commiting all transfers, requires second login.
 * 
 * @author marrink
 */
public class CommitTransferMoneyPage extends SecurePage implements TopSecretPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct. the transfers are read from the session.
	 */
	public CommitTransferMoneyPage()
	{
		add(new ButtonContainer("buttoncontainer", ButtonContainer.BUTTON_COMMIT));
		// copy list as it is at this moment
		final ListView<Entry> transactions =
			new ListView<Entry>("transactions", new ArrayList<Entry>(MySession.getSessesion()
				.getMoneyTransfers()))
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Entry> item)
				{
					item.add(new Label("when"));
					item.add(new Label("from"));
					item.add(new Label("to"));
					item.add(new Label("description"));
					item.add(new Label("amount"));
					if (item.getIndex() % 2 == 0)
						item.add(new SimpleAttributeModifier("class", "outside halfhour"));
				}

				@Override
				protected IModel<Entry> getListItemModel(
						IModel< ? extends List<Entry>> listViewModel, int index)
				{
					return new CompoundPropertyModel<Entry>(super.getListItemModel(listViewModel,
						index));
				}
			};
		add(transactions);
		// not a secure link because the page itself is already secure.
		add(new Link<Void>("commit")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				List<Entry> temp = transactions.getModelObject();
				for (int i = 0; i < temp.size(); i++)
				{
					if (MySession.getSessesion().getMoneyTransfers().remove(temp.get(i)))
					{
						// ok this is not the most briliant and optimized code,
						// it is an example :)
						temp.remove(i);
						i--;
					}
				}
			}
		});
	}
}
