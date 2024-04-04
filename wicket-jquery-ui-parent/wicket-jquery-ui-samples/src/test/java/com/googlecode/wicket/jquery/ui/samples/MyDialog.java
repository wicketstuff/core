package com.googlecode.wicket.jquery.ui.samples;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;


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
