/*
 * $Id$
 * $Revision$ $Date$
 *
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.jasperreports.examples;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * Wicket application class for jasper reports example.
 *
 * @author Eelco Hillenius
 */
public class JasperReportsApplication extends WebApplication {
	/**
	 * Constructor.
	 */
	public JasperReportsApplication() {
	}

	/**
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init() {
		getCspSettings().blocking().disabled();
		getResourceSettings().setResourcePollFrequency(Duration.ofSeconds(1));

		final File base = new File(System.getProperty("jasperreportsDirectory"));
		final File reportFile = new File(base, "WebappReport.jasper");
		try {
			if (!base.exists()) {
				base.mkdirs();
			}
			try (InputStream rep = getClass().getResourceAsStream("/jasperreports/WebappReport.jasper")) {
				Files.copy(rep, reportFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			final File images = new File(base, "../images");
			if (!images.exists()) {
				images.mkdirs();
			}
			try (InputStream rep = getClass().getResourceAsStream("/jasperreports/jasperreports.gif")) {
				Files.copy(rep, new File(images, "jasperreports.gif").toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}
}
