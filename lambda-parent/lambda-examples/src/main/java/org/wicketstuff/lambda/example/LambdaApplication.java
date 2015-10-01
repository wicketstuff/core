package org.wicketstuff.lambda.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * {@link WebApplication} for the lambda examples.
 */
public class LambdaApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

}
