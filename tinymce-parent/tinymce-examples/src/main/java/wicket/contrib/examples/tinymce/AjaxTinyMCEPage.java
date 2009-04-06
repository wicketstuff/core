package wicket.contrib.examples.tinymce;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.tinymce.settings.TinyMCESettings;

public class AjaxTinyMCEPage extends TinyMCEBasePage {
	private static final long serialVersionUID = 1L;

	private boolean visible;

	public AjaxTinyMCEPage() {
		super();

		// TextArea ta = new TextArea("ta");
		// ta.add(new TinyMceBehavior());
		// add(ta);

		final TinyMCEContainer container = new TinyMCEContainer(
				"tinyMCEContainer");
		add(container);
		add(new AjaxLink("toggle") {

			public void onClick(AjaxRequestTarget target) {
				visible = !visible;
				container.setEnableTinymce(visible);
				target.addComponent(container);
			}
		});
	}
}
