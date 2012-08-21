package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.List;

import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.dialog.DialogButtons;

public abstract class SimpleDialog extends AbstractDialog<String>
{
	private static final long serialVersionUID = 1L;

	public SimpleDialog(String id, String title, Model<String> model)
	{
		super(id, title, model, true);
	}

	@Override
	public boolean isResizable()
	{
		return true;
	}

	@Override
	protected List<DialogButton> getButtons()
	{
		return DialogButtons.OK_CANCEL.toList(); //this syntax is allowed until the button state (enable and/or visible) is not altered
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public void onEvent(IEvent<?> event)
//	{
//		if (event.getPayload() instanceof DialogEvent<?>)
//		{
//			DialogEvent<String> payload = (DialogEvent<String>) event.getPayload();
//
//			if (payload.isClicked(BTN_REFRESH))
//			{
//				System.out.println(BTN_REFRESH);
////				payload.getTarget().(this);
//			}
//			else
//			{
//				super.onEvent(event); //close the dialog
//			}
//		}
//	}
}
