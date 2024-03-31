package org.wicketstuff.modalx.example;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.modalx.IWindowCloseListener;
import org.wicketstuff.modalx.MessageBox;
import org.wicketstuff.modalx.ModalContentWindow;
import org.wicketstuff.modalx.ModalFormPanel;


/**
 * Created by IntelliJ IDEA. User: chrisc Date: 2/08/11 Time: 10:24 AM To change this template use
 * File | Settings | File Templates.
 */

public class TestForm extends ModalFormPanel
{
	private static final long serialVersionUID = 1L;
	protected Person person;

	/**
	 * Constructs the object
	 */
	public TestForm(ModalContentWindow iModalContentWindow,
		IWindowCloseListener iWindowCloseListener, Person iPerson)
	{
		super(iModalContentWindow, iWindowCloseListener);

		person = iPerson;
	}

	/**
	 * Overridden in derived classes to add the form components.
	 */
	@Override
	public void addFormComponents()
	{
		form.add(new RequiredTextField<String>("firstName", new PropertyModel<String>(person,
			"firstName")).setLabel(Model.of("First Name")));
		form.add(new RequiredTextField<String>("lastName", new PropertyModel<String>(person,
			"lastName")).setLabel(Model.of("Last Name")));
		form.add(new RequiredTextField<String>("address1", new PropertyModel<String>(person,
			"address1")).setLabel(Model.of("Address line 1")));
		form.add(new RequiredTextField<String>("address2", new PropertyModel<String>(person,
			"address2")).setLabel(Model.of("Address line 2")));
		form.add(new RequiredTextField<String>("state", new PropertyModel<String>(person, "state")).setLabel(Model.of("State")));
		form.add(new RequiredTextField<String>("country", new PropertyModel<String>(person,
			"country")).setLabel(Model.of("Country")));

		form.add(new AjaxLink<Void>("showMsgBox")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MessageBox.show(
					target,
					"It's <i>easy</i> to show a message box from a modal form without needing to explicitly add extra ModalWindow div's to the form's markup.");
			}
		});
		form.add(new AjaxLink<Void>("showFormDefSource")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MessageBox.show(
					target,
					"It's <i>very</i> simple to create modal form class with ModalX. e.g.\n\n"
						+ "<pre>"
						+ "public class TestForm extends ModalFormPanel {\n"
						+ "    public TestForm(ModalContentWindow iModalContentWindow, \n        IWindowCloseListener iWindowCloseListener, Person iPerson) {\n"
						+ "        super(iModalContentWindow, iWindowCloseListener);\n\n"
						+ "        person = iPerson;\n"
						+ "        setTitle(\"Personal Details\");\n"
						+ "    }\n"
						+ "    public void addFormComponents() {\n"
						+ "        form.add(new RequiredTextField(\"firstName\",\n            new PropertyModel(person, \"firstName\")).setLabel(new Model(\"First Name\")));\n"
						+ "        form.add(new RequiredTextField(\"lastName\", \n            new PropertyModel(person, \"lastName\")).setLabel(new Model(\"Last Name\")));\n"
						+ "        etc.,\n" + "    }\n" + "}" + "</pre>\n");
			}
		});

		form.add(new AjaxLink<Void>("showFormOpenSource")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MessageBox.show(
					target,
					"It's <i>very</i> simple to open a ModalX form: e.g.\n\n"
						+ "<pre>"
						+ "TestForm testForm = new TestForm(allocateModalWindow(), null, person);\n\n"
						+ "testForm.show(target);"
						+ "</pre>\n\n"
						+ "Desktop application developers will notice how similar this looks to opening a form in MFC, .NET, OWL, Swing etc.,");
			}
		});
	}
}
