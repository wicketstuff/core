package org.wicketstuff.modalx.example;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.modalx.IWindowCloseListener;
import org.wicketstuff.modalx.MessageBox;
import org.wicketstuff.modalx.ModalFormPanel;
import org.wicketstuff.modalx.optional.ModalXPage;


/**
 * Homepage - derive from convenience class ModalXPage
 */
public class HomePage extends ModalXPage implements IWindowCloseListener
{

	private static final long serialVersionUID = 1L;

	private final Person person = new Person();

	@SuppressWarnings("unused")
	private String resultText = "No answer yet";

	private final Label resultLabel;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{
		super(parameters);

		// Add the simplest type of label
		add(new Label("message", "ModalX Example"));

		resultLabel = new Label("result", new PropertyModel<String>(this, "resultText"));
		resultLabel.setOutputMarkupId(true);

		add(resultLabel);

		add(new AjaxLink<Void>("showShortMsgBox")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MessageBox.show(
					target,
					"Bringing up a MessageBox is as <i>simple</i> as calling:\n\n<pre>MessageBox.show(target, message, title);</pre>\n\nfrom any page or panel. ModalX <b>kindly auto sizes</b> the height for you so that you're not left with excessive white space.");
			}
		});

		add(new AjaxLink<Void>("showTallMsgBox")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MessageBox.show(
					target,
					"This is a <h2>Taller</h2> version of the short message box test.\n\nModalX kindly auto sizes the height for you so that you're not left with excessive white space."
						+ "\n\nLines breaks can be created using the classic <pre>\\n\\n</pre> combination.\n\nI'm using lots of line breaks to make this MessageBox tall."
						+ "\n\nYou can set the size of each individual MessageBox by specifying the width parameter in the show method or you can set the default width of all MessageBoxS by calling\n\n<pre>MessageBox.setDefaultWidth(int width)</pre>");
			}
		});

		add(new AjaxLink<Void>("showModalForm")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				TestForm testForm = new TestForm(allocateModalWindow(), null, person);

				testForm.show(target);
			}
		});

		add(new AjaxLink<Void>("showAnswerForm")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				MessageBox.show(
					target,
					"This message box demonstrates the use of a 'Modal Result', a term borrowed from the desktop application development world. ModalResult can be queried after a form is closed to determine which button was used to close it. \n\nIt also demonstrates the use of the IWindowCloseListener interface."
						+ "\n\nThe HomePage implements IWindowCloseListener interface and so will be notified when this form is closed and then it can check the modal result status of the closed form."
						+ "\n\nAre you feeling positive?",
					MessageBox.MB_YES_NO_CANCEL, HomePage.this);
			}
		});

	}

	/*
	 * Implement IWindowCloseListener to create a listener that can be notified when a
	 * ModalFormPanel (including MessageBox) is closed. Pass the listener into the constructor of
	 * the ModelFormPanel or MessageBox
	 */
        @Override
	public void windowClosed(Panel panel, AjaxRequestTarget target)
	{
		if (panel instanceof ModalFormPanel)
		{
			ModalFormPanel modalFormPanel = (ModalFormPanel)panel;

			switch (modalFormPanel.getModalResult())
			{
				case MessageBox.MR_YES :
					resultText = "Positive";
					break;

				case MessageBox.MR_NO :
					resultText = "Negative";
					break;

				case ModalFormPanel.MR_CANCEL :
					resultText = "Not sure heh?";
					break;
			}

			target.add(resultLabel);
		}
	}
}
