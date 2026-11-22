/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte;

import static org.apache.wicket.csp.CSPDirective.IMG_SRC;
import static org.apache.wicket.csp.CSPDirective.STYLE_SRC;
import static org.apache.wicket.csp.CSPDirective.STYLE_SRC_ATTR;
import static org.apache.wicket.csp.CSPDirectiveSrcValue.SELF;
import static org.apache.wicket.csp.CSPDirectiveSrcValue.UNSAFE_INLINE;

import org.apache.wicket.csp.CSPHeaderConfiguration;

/**
 * Content Security Policy additions that HugeRTE needs on top of Wicket's strict policy.
 * <p>
 * HugeRTE's scripts load under the strict policy, because {@code 'strict-dynamic'} trusts the
 * scripts that the nonced HugeRTE script loads. Its styles do not:
 * <ul>
 * <li>{@code style-src 'self'}: HugeRTE loads its skin and content style sheets with
 * {@code <link>} elements that carry no nonce.</li>
 * <li>{@code style-src-attr 'unsafe-inline'}: the editor UI and the edited content use
 * {@code style} attributes. This does not allow {@code <style>} elements or scripts.</li>
 * <li>{@code img-src data: blob:}: pasted and uploaded images are shown as data and blob
 * URLs.</li>
 * </ul>
 *
 * <pre>{@code
 * protected void init()
 * {
 * 	super.init();
 * 	HugeRteCsp.allow(getCspSettings().blocking());
 * }
 * }</pre>
 */
public final class HugeRteCsp
{
	private HugeRteCsp()
	{
	}

	/**
	 * Adds the sources HugeRTE needs to the policy.
	 *
	 * @param csp
	 *            the policy to extend, usually {@code getCspSettings().blocking()}
	 * @return the policy, for chaining
	 */
	public static CSPHeaderConfiguration allow(CSPHeaderConfiguration csp)
	{
		return csp.add(STYLE_SRC, SELF)
			.add(STYLE_SRC_ATTR, UNSAFE_INLINE)
			.add(IMG_SRC, "data:", "blob:");
	}
}
