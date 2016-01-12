/*
 * $Id: IJRResourceHandler.java 5335 2010-06-14 13:28:39Z cdeal $ $Revision:
 * 1.3 $ $Date: 2010-06-14 09:28:39 -0400 (Mon, 14 Jun 2010) $
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

import net.sf.jasperreports.engine.JRAbstractExporter;

/**
 * @author cdeal
 */
public interface IJRResourceHandler
{
	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getContentType()
	 */
	public String getContentType();

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getExtension()
	 */
	public String getExtension();

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#newExporter()
	 */
	public JRAbstractExporter newExporter();
}
