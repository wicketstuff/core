/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.component;

import java.util.List;

import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.widget.dialog.AbstractDialog;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButton;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButtons;

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
