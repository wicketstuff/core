package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.TinyMCESettings;

public class TinyMCEContainer extends Panel implements IHeaderContributor{
	public boolean enableTinymce;

	public TinyMCEContainer(String id) {
		super(id);
		setOutputMarkupId(true);

		TextArea textArea = new TextArea("ta") {

			public boolean isVisible() {
				return enableTinymce;
			}
		};
		textArea.add(new TinyMceBehavior());
		add(textArea);
	}

	public void setEnableTinymce(boolean enableTinymce) {
		this.enableTinymce = enableTinymce;
	}

	/**
	 * This is needed because even though {@link TinyMceBehavior} implements IHeaderContributor,
	 * the header doesn't get contributed when the component is first rendered though an AJAX call.
	 * @see https://issues.apache.org/jira/browse/WICKET-618 (which was closed WontFix) 
	 */
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(TinyMCESettings.javaScriptReference());
	}
}
