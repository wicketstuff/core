/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.jeeweb.jsp;

import static jakarta.servlet.jsp.tagext.Tag.SKIP_BODY;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import org.apache.wicket.Page;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tag Support to generate a wicket url within a jsp. The tag library can be defined within the
 * <b>&lt;jsp-config&gt;</b> tag in <b>the web.xml</b>. Every jar which is included in the
 * <b>lib</b> folder of the webapp project will automatically scanned if a taglib.tld is located in
 * the <b>META-INF</b> folder. If so the tag library is automatically added.
 *
 * Example of web.xml definition:
 *
 * <pre>
 * &lt;taglib&gt;
 * 	&lt;taglib-uri&gt;uri&lt;/taglib-uri&gt;
 * 	&lt;taglib-location&gt;/WEB-INF/jsp/mytaglib.tld&lt;taglib-location&gt;
 * &lt;/taglib&gt;
 * </pre>
 *
 * Usage: To use the taglib and this tag you only have to define it in the jsp:
 *
 * <pre>
 * &lt;%@ taglib prefix="wicket" uri="http://wicketstuff-jee-web.org/functions/jsp" %&gt;
 *
 * Tag: url // Parameters: page(required), query(optional) // Example:
 * &lt;a href="&lt;wicket:url page="mypage.MyTestPage" query="param1=value1&amp;param2=value2"/&gt;"&gt;LINK&lt;/a&gt;
 * </pre>
 *
 * @author Tobias Soloschenko
 */
public class JspUrlTagSupport extends TagSupport
{

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JspUrlTagSupport.class);

	private String page = null;

	private String query = null;

	/**
	 * Applies the url of wicket to the tag
	 */
	@Override
	public int doStartTag() throws JspException
	{
		try
		{
			JspWriter out = pageContext.getOut();
			PageParameters pageParameters = new PageParameters();
			if (query != null)
			{
				RequestUtils.decodeParameters(query, pageParameters);
			}
			Class<Page> resolveClass = WicketObjects.resolveClass(page);
			CharSequence urlFor = RequestCycle.get().urlFor(resolveClass, pageParameters);
			out.write(urlFor.toString());
			out.flush();
		}
		catch (IOException e)
		{
			LOGGER.error("Error while generating url for page " + page, e);
			throw new JspException("Error while generating url for page ", e);
		}
		return SKIP_BODY;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}
}
