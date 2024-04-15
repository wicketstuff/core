package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.List;

import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;

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
}
