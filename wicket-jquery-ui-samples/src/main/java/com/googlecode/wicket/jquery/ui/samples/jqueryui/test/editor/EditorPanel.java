package com.googlecode.wicket.jquery.ui.samples.jqueryui.test.editor;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.WysiwygEditor;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.DefaultWysiwygToolbar;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.test.editor.EditorPanel.Html;

public class EditorPanel extends GenericPanel<Html>
{
	private static final long serialVersionUID = 1L;

	public EditorPanel(String id, IModel<Html> model)
	{
		super(id, new CompoundPropertyModel<Html>(model));

		final DefaultWysiwygToolbar toolbar = new DefaultWysiwygToolbar("toolbar");
		final WysiwygEditor editor  = new WysiwygEditor("wysiwyg");

		this.add(editor, toolbar);
	}

	static class Html implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		String wysiwyg;

		public Html(String text)
		{
			wysiwyg = text;
		}

		public String getWysiwyg()
		{
			return wysiwyg;
		}

		public void setWysiwyg(String wysiwyg)
		{
			this.wysiwyg = wysiwyg;
		}
	}
}
