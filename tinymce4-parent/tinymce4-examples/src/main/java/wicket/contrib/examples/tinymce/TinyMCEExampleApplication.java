package wicket.contrib.examples.tinymce;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEExampleApplication extends WebApplication
{

	public TinyMCEExampleApplication()
	{
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@Override
	public Class getHomePage()
	{
		return TinyMCEBasePage.class;
	}
}
