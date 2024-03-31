// -[KeepHeading]-


// -[Copyright]-

/**
 * � 2006, 2009. Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package org.wicketstuff.modalx;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// -[Class]-

/**
 * Class Name : MessageBox Diagram : Wicket Generic Modal Windows Project : Echobase framework Type
 * : concrete Implements a simple way to communicate messages to users via a modal message box. It
 * is designed to be used by calling the static show methods, not via the constructor directly.
 * 
 * This class represents the body 'panel' of the message box.
 * 
 * This code donated from the pagebloom project to the open source Modal X project.
 * 
 * @author Chris Colman
 */
public class MessageBox extends ModalFormPanel
{
	private static final long serialVersionUID = 1L;
// -[KeepWithinClass]-
	public static final int MB_OK = 0;
	public static final int MB_OK_CANCEL = 1;
	public static final int MB_YES_NO = 2;
	public static final int MB_YES_NO_CANCEL = 3;

	public static final int MR_YES = 3;
	public static final int MR_NO = 4;


// -[Fields]-


	/**
	 * NoDesc
	 */
	protected transient String message;


	/**
	 * The button set to use - currently ignored will always just show OK.
	 */
	protected transient int buttonConfig;


	/**
	 * The default width of all MessageBoxS. Changed the default width of all MessageBoxS by calling
	 * setDefaultWidth.
	 */
	private static int defaultWidth = 400;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates.
	private static Logger logger = LoggerFactory.getLogger(MessageBox.class);


// -[Methods]-

	/**
	 * Sets defaultWidth
	 */
	public static void setDefaultWidth(int iDefaultWidth)
	{
		defaultWidth = iDefaultWidth;
	}


	/**
	 * Returns defaultWidth
	 */
	public static int getDefaultWidth()
	{
		return defaultWidth;
	}

	/**
	 * Adds the button controls to the form. By default OK and Cancel buttons are added but this
	 * method can be overridden to add a different button set like YES, NO etc.,
	 */
	@Override
	public void addControlComponents()
	{
		// OK button
		if (buttonConfig == MB_OK || buttonConfig == MB_OK_CANCEL)
		{
			Button button = new Button("ok");
			button.add(new AjaxEventBehavior("click")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					setModalResult(ModalFormPanel.MR_OK);

					closeModal(target);
				}
			});
			button.setDefaultFormProcessing(false);
			form.add(button);
		}
		else
		{
			Button button = new Button("ok");
			button.setVisible(false);
			form.add(button);
		}

		// Yes/No buttons
		if (buttonConfig == MB_YES_NO || buttonConfig == MB_YES_NO_CANCEL)
		{
			Button button = new Button("yes");
			button.add(new AjaxEventBehavior("click")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					setModalResult(MessageBox.MR_YES);

					closeModal(target);
				}
			});
			button.setDefaultFormProcessing(false);
			form.add(button);

			button = new Button("no");
			button.add(new AjaxEventBehavior("click")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					setModalResult(MessageBox.MR_NO);

					closeModal(target);
				}
			});
			button.setDefaultFormProcessing(false);
			form.add(button);
		}
		else
		{
			Button button = new Button("yes");
			button.setVisible(false);
			form.add(button);

			button = new Button("no");
			button.setVisible(false);
			form.add(button);
		}

		// Cancel button
		if (buttonConfig == MB_OK_CANCEL || buttonConfig == MB_YES_NO_CANCEL)
		{
			Button button = new Button("cancel");
			button.add(new AjaxEventBehavior("click")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					setModalResult(ModalFormPanel.MR_CANCEL);

					onCancel(target);
				}
			});
			button.setDefaultFormProcessing(false);
			form.add(button);
		}
		else
		{
			Button button = new Button("cancel");
			button.setVisible(false);
			form.add(button);
		}
	}

	/**
	 * Overridden in derived classes to add the form components. Calll form.add to add components,
	 * not just add.
	 */
	@Override
	public void addFormComponents()
	{
		MultiLineLabel body = new MultiLineLabel("message", message);
		body.setEscapeModelStrings(false);
		form.add(body);
	}

	/**
	 * Describe here
	 */
	public static MessageBox show(AjaxRequestTarget target, String message)
	{
		return MessageBox.show(target, message, MessageBox.MB_OK, null);
	}

	/**
	 * Describe here
	 */
	public static MessageBox show(AjaxRequestTarget target, String message,
		int buttonConfig,
		IWindowCloseListener iWindowCloseListener)
	{
		Page page = target.getPage();
		if (!(page instanceof ModalMgr))
		{
			logger.error("Page is not a ModalMgr. Can not open message box");
			return null;
		}

		ModalMgr modalMgr = (ModalMgr)page;

		MessageBox messageBox = new MessageBox(modalMgr.allocateModalWindow(), message,
			buttonConfig, iWindowCloseListener);

		messageBox.show(target);

		return messageBox;
	}

	/**
	 * Constructs the object
	 */
	public MessageBox(ModalContentWindow iModalContentWindow, String iMessage,
		int iButtonConfig, IWindowCloseListener iWindowCloseListener)
	{
		super(iModalContentWindow, iWindowCloseListener);

		message = iMessage;
		buttonConfig = iButtonConfig;
	}

}
