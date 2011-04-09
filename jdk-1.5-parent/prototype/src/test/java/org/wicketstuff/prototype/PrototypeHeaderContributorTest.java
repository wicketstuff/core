package org.wicketstuff.prototype;

import junit.framework.TestCase;

import org.apache.wicket.markup.html.internal.HeaderResponse;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Test for {@link PrototypeHeaderContributor}.
 */
public class PrototypeHeaderContributorTest extends TestCase {

	public void test() throws Exception {
		new WicketTester();

		final StringBuilder builder = new StringBuilder();

		HeaderResponse mockResponse = new HeaderResponse() {

			@Override
			protected Response getRealResponse() {

				return new Response() {

					@Override
					public void write(CharSequence arg0) {
						builder.append(arg0);
					}

					@Override
					public Object getContainerResponse() {
						throw new UnsupportedOperationException();
					}

					@Override
					public String encodeURL(CharSequence url) {
						throw new UnsupportedOperationException();
					}

					@Override
					public void write(byte[] array) {
						throw new UnsupportedOperationException();
					}
				};
			}
		};

		new PrototypeHeaderContributor().renderHead(null, mockResponse);

		assertEquals(
				"<script type=\"text/javascript\" src=\"wicket/resource/org.wicketstuff.prototype.PrototypeResourceReference/prototype.js\"></script>\n",
				builder.toString());
	}
}
