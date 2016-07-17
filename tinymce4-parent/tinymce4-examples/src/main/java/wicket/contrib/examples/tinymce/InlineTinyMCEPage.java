package wicket.contrib.examples.tinymce;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import wicket.contrib.tinymce4.InPlaceEditComponent;
import wicket.contrib.tinymce4.ajax.TinyMceAjaxButton;

public class InlineTinyMCEPage extends TinyMCEBasePage
{
	private static final long serialVersionUID = 1L;
	private Component modelValue;

	public InlineTinyMCEPage()
	{
		Form<Void> form = new Form<>("form");
		InPlaceEditComponent editableComponent = new InPlaceEditComponent("editableComponent",
				"<p><b>Click me</b> and <i>edit me</i> with <font color=\"red\">tinymce</font>. "
						+ "This is a new feature introduced with version 4.</p>");
		form.add(editableComponent);
		form.add(modelValue = new Label("modelValue", editableComponent.getModel()));

		modelValue.setEscapeModelStrings(false);
		modelValue.setOutputMarkupId(true);

		form.add(new TinyMceAjaxButton("submit")
		{
            private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				super.onSubmit(target);
				target.add(modelValue);
			}
		});
		add(form);
	}
}
