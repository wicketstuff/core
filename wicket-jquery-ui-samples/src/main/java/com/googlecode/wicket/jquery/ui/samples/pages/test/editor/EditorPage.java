package com.googlecode.wicket.jquery.ui.samples.pages.test.editor;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;
import com.googlecode.wicket.jquery.ui.samples.pages.test.editor.EditorPanel.Html;

public class EditorPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public EditorPage()
	{
		final Model<Html> model = Model.of(new Html("the text"));

		// ModalWindow //
		final ModalWindow popup = new EditorWindow("popup", model);
		this.add(popup.setOutputMarkupId(true));

		// Form //
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		form.add(new AjaxButton("open")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
		     {
		         popup.show(target);
		     }
		});

		form.add(new AjaxButton("update")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
		     {
		         model.setObject(new Html("my new model object"));
		     }
		});
	}
}
