package org.wicketstuff.examples.tinymce;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.wicketstuff.tinymce6.TinyMceBehavior;

public class AjaxTinyMCEPage extends TinyMCEBasePage
{
	private static final long serialVersionUID = 1L;
	private TextArea<String> ta;
	private boolean visible;

	public AjaxTinyMCEPage()
	{
		ta = new TextArea<>("ta");
		ta.setVisible(false);
		ta.setOutputMarkupPlaceholderTag(true);
		ta.add(new TinyMceBehavior());
		add(ta);

		add(new AjaxLink<Void>("toggle")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				visible = !visible;
				ta.setVisible(visible);
				target.add(ta);
			}
		});
	}
}
