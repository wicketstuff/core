/*
 * $Id: EditContactPage.java 634 2006-03-26 18:28:10 -0800 (Sun, 26 Mar 2006) ivaynberg $
 * $Revision: 634 $
 * $Date: 2006-03-26 18:28:10 -0800 (Sun, 26 Mar 2006) $
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

import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;

/**
 * Edit the Contact. Display details if an existing contact, then persist them if saved.
 *
 * @author igor
 *
 */
public class EditContactPage extends BasePage
{
	private static final long serialVersionUID = 1L;
	private final Page backPage;
	@SpringBean(name = "contactDao")
	private ContactDao contactDao;

	/**
	 * Constructor. Create or edit the contact. Note that if you don't need the page to be
	 * bookmarkable, you can use whatever constructor you need, such as is done here.
	 *
	 * @param backPage
	 *            The page that the user was on before coming here
	 * @param contactModel
	 *            Model that contains the contact we will edit
	 */
	public EditContactPage(Page backPage, IModel<?> contactModel)
	{
		this.backPage = backPage;

		Contact contact = (Contact)contactModel.getObject();
		Form<Contact> form = new Form<Contact>("contactForm", new CompoundPropertyModel<Contact>(
			contact));
		add(form);

		form.add(newRequiredTextField("firstname", 32));
		form.add(newRequiredTextField("lastname", 32));
		form.add(newRequiredTextField("phone", 16));
		form.add(new TextField<String>("email").add(new StringValidator(null, 128)).add(
			EmailAddressValidator.getInstance()));
		form.add(new CancelButton());
		form.add(new SaveButton());
	}

	private RequiredTextField<String> newRequiredTextField(String id, int maxLenght)
	{
		RequiredTextField<String> textField = new RequiredTextField<String>(id);
		textField.add(new StringValidator(null, maxLenght));
		return textField;
	}

	private final class CancelButton extends Button
	{
		private static final long serialVersionUID = 1L;

		private CancelButton()
		{
			super("cancel", new ResourceModel("cancel"));
			setDefaultFormProcessing(false);
		}

		@Override
		public void onSubmit()
		{
			String msg = getLocalizer().getString("status.cancel", this);
			getSession().info(msg);
			setResponsePage(backPage);
		}
	}

	private final class SaveButton extends Button
	{
		private static final long serialVersionUID = 1L;

		private SaveButton()
		{
			super("save", new ResourceModel("save"));
		}

		@Override
		public void onSubmit()
		{
			Contact contact = (Contact)getForm().getModelObject();
			contactDao.save(contact);
			String msg = MapVariableInterpolator.interpolate(
				getLocalizer().getString("status.save", this),
				Map.of("name", contact.getFullName()));
			getSession().info(msg);
			setResponsePage(backPage);
		}
	}
}
