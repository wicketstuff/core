/*
 * Copyright 2014 WicketStuff.
 *
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
package org.wicketstuff.wicket.mount.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is applied to a Wicket Application Class to denote to the auto mount processor that
 * the Application desires generated code for mounting pages.  All pages that are annotated with @MountPath
 * and exist in the packagesToScan will have a mount generated.
 *
 * @author jsarman
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface AutoMount
{
	/**
	 * Allow the configuration of the root path (not including contextPath) for auto generated paths of classes
	 *
	 * @return Configured root for pages not requiring security
	 */
	String defaultRoot() default "";

	/**
	 * Allow the configuration of mime type for auto generated paths of classes
	 *
	 * @return default mime type for auto generated paths. Defaults to "".
	 */
	String mimeExtension() default "";

	/**
	 * Allow explicit declaration of packages that should be scanned to generate the code for
	 * auto mounts.  If not set then the scanned packages are set to the package of the application
	 * annotated with @SecureMount as the root package and all packages that are a branch of the root.
	 * If packages are explicitly set then .* to end of packages allows the branches to be scanned. If a package is
	 * set without the .* then only that package is scanned.
	 *
	 * @return String array of packages that are scanned for auto mounting.
	 */
	String[] packagesToScan() default {};
}
