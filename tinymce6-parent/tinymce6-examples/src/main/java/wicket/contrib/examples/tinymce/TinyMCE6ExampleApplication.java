package wicket.contrib.examples.tinymce;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCE6ExampleApplication extends WebApplication {

	public TinyMCE6ExampleApplication() {
	}

	@Override
	protected void init() {
		super.init();

		getCspSettings().blocking().clear();
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class getHomePage() {
		return TinyMCEBasePage.class;
	}
}
