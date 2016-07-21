/*
 * $Id: RtfResourceHandler.java 5335 2010-06-14 13:28:39Z cdeal $ $Revision:
 * 1.4 $ $Date: 2010-06-14 09:28:39 -0400 (Mon, 14 Jun 2010) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.jasperreports.handlers;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;

/**
 * @author cdeal
 */
public class RtfResourceHandler implements IJRResourceHandler, Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.wicketstuff.jasperreports.handlers.IJRResourceHandler#newExporter()
	 */
	public JRAbstractExporter newExporter()
	{
		return new JRRtfExporter();
	}

	/**
	 * @see org.wicketstuff.jasperreports.handlers.IJRResourceHandler#getContentType()
	 */
	public String getContentType()
	{
		return "text/rtf";
	}

	/**
	 * @see org.wicketstuff.jasperreports.handlers.IJRResourceHandler#getExtension()
	 */
	public String getExtension()
	{
		return "rtf";
	}
}
