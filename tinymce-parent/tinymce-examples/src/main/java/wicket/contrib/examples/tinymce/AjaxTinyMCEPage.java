package wicket.contrib.examples.tinymce;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

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
		add(new AjaxLink<Void>("toggle") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {
				visible = !visible;
				container.setEnableTinymce(visible);
				target.addComponent(container);
			}
		});
	}
}
