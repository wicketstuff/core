/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.extensions.markup.html.tree;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.util.io.IClusterable;

/**
 * A set of CSS declarations that the tree components used to write as an inline {@code style}
 * attribute. Wicket's Content Security Policy blocks inline style attributes, so the declarations
 * are rendered as a rule in a {@code <style>} header item instead, which Wicket gives the CSP
 * nonce of the page (also in Ajax responses). The element that needs the declarations gets
 * {@link #getCssClass()} as a CSS class.
 * <p>
 * The declarations are marked {@code !important} so the rule takes precedence over the tree's
 * stylesheets the same way the inline style attribute did.
 * <p>
 * This class is used internally by the tree components.
 */
public final class StyleRule implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private final String cssClass;

	private final String css;

	/**
	 * Creates a rule for the given declarations.
	 * 
	 * @param prefix
	 *            prefix of the generated CSS class name, such as {@code wicket15-tree-icon}
	 * @param declarations
	 *            CSS declarations without trailing semicolon, such as {@code width:25%}
	 */
	public StyleRule(String prefix, String... declarations)
	{
		StringBuilder body = new StringBuilder();
		for (String declaration : declarations)
		{
			body.append(declaration).append(" !important;");
		}
		cssClass = prefix + "-" + digest(body.toString());
		// the doubled class selector raises the specificity above the selectors in the tree's
		// stylesheets, !important does the rest
		css = "." + cssClass + "." + cssClass + "{" + body + "}";
	}

	/**
	 * @return the CSS class an element needs to get the declarations of this rule
	 */
	public String getCssClass()
	{
		return cssClass;
	}

	/**
	 * @return the CSS rule
	 */
	public String getCss()
	{
		return css;
	}

	/**
	 * Renders the rule as a {@code <style>} header item. Rendering the same rule more than once
	 * per page renders it only once.
	 * 
	 * @param response
	 *            the header response
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.render(CssHeaderItem.forCSS(css, cssClass));
	}

	private static String digest(String value)
	{
		try
		{
			byte[] hash = MessageDigest.getInstance("SHA-256").digest(
				value.getBytes(StandardCharsets.UTF_8));
			// 64 bits are plenty to tell apart the few rules a page has
			return HexFormat.of().formatHex(hash, 0, 8);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new IllegalStateException("SHA-256 is not available", e);
		}
	}
}
