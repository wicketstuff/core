package org.wicketstuff.jquery.ui.samples;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.wicketstuff.jquery.ui.widget.dialog.AbstractDialog;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButton;


public class MyDialog extends AbstractDialog<String>
{
	private static final long serialVersionUID = 1L;

	public MyDialog(String id, String title)
	{
		super(id, title);
	}

	@Override
	public void onClose(IPartialPageRequestHandler handler, DialogButton button)
	{
	}
}
