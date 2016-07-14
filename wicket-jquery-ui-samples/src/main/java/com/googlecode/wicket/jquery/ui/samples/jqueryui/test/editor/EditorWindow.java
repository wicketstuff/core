package com.googlecode.wicket.jquery.ui.samples.jqueryui.test.editor;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.samples.jqueryui.test.editor.EditorPanel.Html;

public class EditorWindow extends ModalWindow
{
	private static final long serialVersionUID = 1L;

	public EditorWindow(String id, Model<Html> model)
	{
		super(id, model);

		final EditorPanel panel = new EditorPanel(this.getContentId(), model);
		this.setContent(panel);
	}

	@Override
	public boolean isResizable()
	{
		return true;
	}
}
