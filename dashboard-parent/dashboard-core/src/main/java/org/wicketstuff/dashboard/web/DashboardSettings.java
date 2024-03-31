/*
 * Copyright 2012 Decebal Suiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.web;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * {@link DashboardSettings} which allows to specify set of resources for inclusion in an page header
 * @author Decebal Suiu
 */
public class DashboardSettings {

	@SuppressWarnings("serial")
	private static final MetaDataKey<DashboardSettings> KEY = new MetaDataKey<DashboardSettings>() {};

	private ResourceReference javaScriptReference = new PackageResourceReference(
			DashboardSettings.class, "res/dashboard.js");
	private ResourceReference cssReference = new PackageResourceReference(
			DashboardSettings.class, "res/dashboard.css");
	private ResourceReference rtlCssReference = new PackageResourceReference(
			DashboardSettings.class, "res/dashboard-rtl.css");
	private ResourceReference jqueryUIReference = new PackageResourceReference(
			DashboardSettings.class, "res/jquery-ui-1.13.0.min.js");

	private boolean includeJQueryUI = true;
	private boolean includeJavaScript = true;
	private boolean includeCss = true;

	/**
	 * Private constructor, use {@link #get()} instead.
	 */
	private DashboardSettings() {
	}

	public boolean isIncludeJQueryUI() {
		return includeJQueryUI;
	}

	public DashboardSettings setIncludeJQueryUI(boolean includeJQueryUI) {
		this.includeJQueryUI = includeJQueryUI;
		return this;
	}

	public boolean isIncludeJavaScript() {
		return includeJavaScript;
	}

	public DashboardSettings setIncludeJavascript(boolean includeJavaScript) {
		this.includeJavaScript = includeJavaScript;
		return this;
	}

	public boolean isIncludeCss() {
		return includeCss;
	}

	public DashboardSettings setIncludeCss(boolean includeCss) {
		this.includeCss = includeCss;
		return this;
	}

	public ResourceReference getJQueryUIReference() {
		return jqueryUIReference;
	}

	public DashboardSettings setJQueryUIReference(ResourceReference jqueryUIReference) {
		this.jqueryUIReference = jqueryUIReference;
		return this;
	}

	public ResourceReference getJavaScriptReference() {
		return javaScriptReference;
	}

	public DashboardSettings setJavaScriptReference(ResourceReference javaScriptReference) {
		this.javaScriptReference = javaScriptReference;
		return this;
	}

	public ResourceReference getCssReference() {
		return cssReference;
	}

	public DashboardSettings setCssReference(ResourceReference cssReference) {
		this.cssReference = cssReference;
		return this;
	}

	public ResourceReference getRtlCssReference() {
		return rtlCssReference;
	}

	public DashboardSettings setRtlCssReference(ResourceReference rtlCssReference) {
		this.rtlCssReference = rtlCssReference;
		return this;
	}

	/**
	 * Retrieves the instance of settings object.
	 *
	 * @return settings instance
	 */
	public static DashboardSettings get() {
		Application application = Application.get();
		DashboardSettings settings = application.getMetaData(KEY);
		if (settings == null) {
			synchronized (application) {
				settings = application.getMetaData(KEY);
				if (settings == null) {
					settings = new DashboardSettings();
					application.setMetaData(KEY, settings);
				}
			}
		}

		return application.getMetaData(KEY);
	}

}
