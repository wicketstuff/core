/**
 * Copyright (C) 2009 Uwe Schaefer <uwe@codesmell.de>
 *
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
package org.wicketstuff.jslibraries;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.wicketstuff.jslibraries.util.Assert;

public enum CDN implements Provider {

	GOOGLE {

		private volatile Map<Library, GoogleRenderer> renderers;

		public URL render(final Library lib, final Version version,
				boolean production) {
			Assert.parameterNotNull(lib, "lib");
			Assert.parameterNotNull(version, "version");

			if (renderers == null) {
				renderers = new HashMap<Library, GoogleRenderer>();
				renderers.put(Library.JQUERY, new GoogleRenderer("jquery",
						"jquery", ".js", ".min.js"));
				renderers.put(Library.JQUERY_UI, new GoogleRenderer("jqueryui",
						"jquery-ui", ".js", ".min.js"));
				renderers.put(Library.MOOTOOLS_CORE, new GoogleRenderer(
						"mootools", "mootools", ".js", "-yui-compressed.js"));
				renderers.put(Library.PROTOTYPE, new GoogleRenderer(
						"prototype", "prototype", ".js"));
				renderers.put(Library.SCRIPTACULOUS, new GoogleRenderer(
						"scriptaculous", "scriptaculous", ".js"));
				renderers.put(Library.YUI, new GoogleRenderer("yui",
						"build/yuiloader/yuiloader", ".js", "-min.js"));
				renderers.put(Library.DOJO, new GoogleRenderer("dojo",
						"dojo/dojo.xd.js", ".uncompressed.js", ""));
				renderers.put(Library.SWFOBJECT, new GoogleRenderer(
						"swfobject", "swfobject", "_src.js", ".js"));
				renderers.put(Library.EXT_CORE, new GoogleRenderer("ext-core",
						"ext-core", "-debug.js", ".js"));
			}
			URLRenderer r = renderers.get(lib);
			if (r == null) {
				return null;
			}
			return r.render(lib, version, production);
		}
	}

	,
	YAHOO {

		@Override
		public URL render(Library lib, Version version, boolean production) {
			Assert.parameterNotNull(version, "version");
			Assert.parameterNotNull(lib, "lib");
			
			if (lib == Library.YUI) {
				String sign = production ? "-min" : "";
				try {
					return new URL("http://yui.yahooapis.com/"
							+ version.renderVersionNumbers()
							+ "/build/yuiloader/yuiloader" + sign + ".js");
				} catch (MalformedURLException e) {
					throw new IllegalStateException(
							"Construction of Yahoo-URL failed.",e);
				}
			}
			return null;
		}

	};

	protected abstract URL render(Library lib, Version v, boolean production);

	public HeaderContributor getHeaderContributor(
			final VersionDescriptor versionDescriptor, boolean production) {

		Assert.parameterNotNull(versionDescriptor, "versionDescriptor");

		Library lib = versionDescriptor.getLibrary();
		Version v = versionDescriptor.getVersion(this);
		if (lib.getVersions(this).contains(v)) {
			URL url = render(lib, v, production);
			if (url != null) {
				return JavascriptPackageResource.getHeaderContribution(url
						.toExternalForm());
			}
		}
		return null;
	}

	static class GoogleRenderer implements URLRenderer {
		private static final String URL_PREFIX = "http://ajax.googleapis.com/ajax/libs/";
		private final String path;
		private final String file;
		private final String suffix;
		private final String productionSuffix;

		private GoogleRenderer(final String path, final String file,
				final String suffix, final String productionSuffix) {
			this.path = path;
			this.file = file;
			this.suffix = suffix;
			this.productionSuffix = productionSuffix;
		}

		private GoogleRenderer(final String path, final String file,
				final String suffix) {
			this(path, file, suffix, suffix);
		}

		public URL render(final Library lib, final Version v, boolean production) {
			Assert.parameterNotNull(lib, "lib");
			Assert.parameterNotNull(v, "v");

			String version = v.renderVersionNumbers();
			String spec = URL_PREFIX + path + "/" + version + "/" + file
					+ (production ? productionSuffix : suffix);
			try {
				return new URL(spec);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public static final CDN[] ANY = values();

}
