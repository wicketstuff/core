/*
 * $Id: ListContactsPage.java 903 2006-08-30 09:08:51Z ivaynberg $
 * $Revision: 903 $
 * $Date: 2006-08-30 02:08:51 -0700 (Wed, 30 Aug 2006) $
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
package wicket.contrib.phonebook.web.page;

import java.io.Serializable;
import java.util.*;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.GoAndClearFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.web.CheckBoxColumn;
import wicket.contrib.phonebook.web.ContactsDataProvider;

/**
 * Display a Pageable List of Contacts.
 * 
 * @author igor
 */
public class ListContactsPage extends BasePage {
	@SpringBean(name = "contactDao")
	private ContactDao dao;

	private final DefaultDataTable<Contact> users;

	private final Set<Long> selectedContactIds = new HashSet<Long>();

	/**
	 * Provides a composite User Actions panel for the Actions column.
	 * 
	 * @author igor
	 */
	private static class UserActionsPanel extends Panel {
		public UserActionsPanel(String id, IModel<Contact> contactModel) {
			super(id);
			addEditLink(contactModel);
			addDeleteLink(contactModel);

		}

		private void addDeleteLink(IModel<Contact> contactModel) {
			add(new Link<Contact>("deleteLink", contactModel) {
				/**
				 * Go to the Delete page, passing this page and the id of the
				 * Contact involved.
				 */
				@Override
				public void onClick() {
					setResponsePage(new DeleteContactPage(getPage(), getModel()));
				}
			});
		}

		private void addEditLink(IModel<Contact> contactModel) {
			add(new Link<Contact>("editLink", contactModel) {
				/**
				 * Go to the Edit page, passing this page and the id of the
				 * Contact involved.
				 */
				@Override
				public void onClick() {
					setResponsePage(new EditContactPage(getPage(), getModel()));
				}
			});
		}

	}

	/**
	 * Constructor. Having this constructor public means that the page is
	 * 'bookmarkable' and hence can be called/ created from anywhere.
	 */
	public ListContactsPage() {

		addCreateLink();

		// set up data provider
		ContactsDataProvider dataProvider = new ContactsDataProvider(dao);

		// create the form used to contain all filter components
		final FilterForm form = new FilterForm("filter-form", dataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				users.setCurrentPage(0);
			}
		};

		form.add(new Button("delete-selected") {
			@Override
			public void onSubmit() {
				for (Long selectedContactId : selectedContactIds) {
					dao.delete(selectedContactId);
				}
				// clear out the set, we no longer need the selection
				selectedContactIds.clear();
			}
		});

		// create the data table
		users = new DefaultDataTable<Contact>("users", createColumns(),
				dataProvider, 10);
		users.addTopToolbar(new FilterToolbar("", users, form, dataProvider));
		form.add(users);

		add(form);
	}

	private List<IColumn<Contact>> createColumns() {
		List<IColumn<Contact>> columns = new ArrayList<IColumn<Contact>>();
		columns.add(new CheckBoxColumn<Contact>(
				new PropertyModel<Collection<Serializable>>(this,
						"selectedContactIds")) {

			@Override
			protected Serializable getModelObjectToken(IModel model) {
				return ((Contact) model.getObject()).getId();
			}

		});
		columns.add(createActionsColumn());
		columns.add(createColumn("first.name", "firstname", "firstname"));
		columns.add(new ChoiceFilteredPropertyColumn<Contact, String>(
				new ResourceModel("last.name"), "lastname", "lastname",
				new LoadableDetachableModel<List<? extends String>>() {
					@Override
					protected List<String> load() {
						List<String> uniqueLastNames = dao.getUniqueLastNames();
						uniqueLastNames.add(0, "");
						return uniqueLastNames;
					}
				}));
		columns.add(createColumn("phone", "phone", "phone"));
		columns.add(createColumn("email", "email", "email"));
		return columns;
	}

	private TextFilteredPropertyColumn<Contact, String> createColumn(
			String key, String sortProperty, String propertyExpression) {
		return new TextFilteredPropertyColumn<Contact, String>(
				new ResourceModel(key), sortProperty, propertyExpression);
	}

	/**
	 * Create a composite column extending FilteredAbstractColumn. This column
	 * adds a UserActionsPanel as its cell contents. It also provides the
	 * go-and-clear filter control panel.
	 */
	private FilteredAbstractColumn<Contact> createActionsColumn() {
		return new FilteredAbstractColumn<Contact>(new Model<String>(
				getString("actions"))) {
			// return the go-and-clear filter for the filter toolbar
			public Component getFilter(String componentId, FilterForm form) {
				return new GoAndClearFilter(componentId, form,
						new ResourceModel("filter"), new ResourceModel("clear"));
			}

			// add the UserActionsPanel to the cell item
			public void populateItem(Item<ICellPopulator<Contact>> cellItem,
					String componentId, IModel<Contact> rowModel) {
				cellItem.add(new UserActionsPanel(componentId, rowModel));
			}
		};
	}

	private void addCreateLink() {
		add(new Link<Void>("createLink") {
			/**
			 * Go to the Edit page when the link is clicked, passing an empty
			 * Contact details
			 */
			@Override
			public void onClick() {
				setResponsePage(new EditContactPage(getPage(),
						new Model<Contact>(new Contact())));
			}
		});
	}
}
