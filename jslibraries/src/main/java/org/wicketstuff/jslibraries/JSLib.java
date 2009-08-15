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

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.wicketstuff.jslibraries.util.Assert;
import org.wicketstuff.jslibraries.util.WicketDeploymentState;

/**
 * <p>
 * The reason for this is the common use-case of fetching 'standard'-libs from
 * Google or Yahoo instead of the local app if possible, for caching and
 * parallelization reasons.
 * </p>
 * Usage:
 * <p>
 * <code>
 * JSLib.getHeaderContributor(VersionDescriptor.exactVersion(Library.JQUERY, 1,3,1))
 * </code> will serve the appropriate Script as bundled with this project, in
 * either readable or minimized (if Wicket is in
 * 'deployment'-configuration-type) form.
 * </p>
 * <p>
 * <code>
 * JSLib.getHeaderContributor(VersionDescriptor.exactVersion(Library.JQUERY, 1,3,1), CDN.ANY)
 * </code> will serve it from the any CDN where it is available, falling back to
 * the local version if necessary.
 * </p>
 * <p>
 * <code>
 * JSLib.getHeaderContributor(VersionDescriptor.exactVersion(Library.JQUERY, 1,3,1), true, CDN.GOOGLE)
 * </code> will serve it from Google (or the local Version as fallback) in
 * minimized form, no matter what Wicket´s configuration is.
 * </p>
 * <p>
 * Component Developers should not select providers.
 * </p>
 * <p>
 * Application Developers can set the providers they want to use on a per
 * Application basis by using <br />
 * <code>
 * JSLib.setOverrideProviders(Application.get(), LocalProvider.DEFAULT);
 * </code> for Local use only, or <br />
 * <code>
 * JSLib.setOverrideProviders(Application.get(), CDN.GOOGLE); 
 * </code> or even <br />
 * <code>
 * JSLib.setOverrideProviders(Application.get(), CDN.ANY); 
 * </code>
 * </p>
 * 
 */
public class JSLib
{
    private JSLib()
    {
    }

    private static final MetaDataKey<Provider[]> PROVIDER_KEY = new MetaDataKey<Provider[]>()
    {
    };

    /**
     * Not to be used by Component authors. This should be used as an
     * application-wide setting for which providers to use. If set, it will be
     * applied instead of the providers passed
     * 
     * @param app
     * @param providers
     */
    public static void setOverrideProviders(final Application app, final Provider... providers)
    {
        Assert.parameterNotNull(app, "app");
        Assert.parameterNotNull(providers, "providers");
        app.setMetaData(PROVIDER_KEY, providers);
    }

    /**
     * @param versionDescriptor
     * @param providers
     *            list of alternative providers (might be ignored if
     *            setOverrideProviders was used)
     * @return matching HeaderContributor from the first matching provider
     */
    public static HeaderContributor getHeaderContribution(final VersionDescriptor versionDescriptor,
            final Provider... providers)
    {
        return getHeaderContribution(versionDescriptor, WicketDeploymentState.isProduction(), providers);
    }

    /**
     * @param versionDescriptor
     * @param production
     *            if true tried to serve minimized versions
     * @param providers
     *            list of alternative providers (might be ignored if
     *            setOverrideProviders was used
     * @return matching HeaderContributor from the first matching provider
     */
    public static HeaderContributor getHeaderContribution(final VersionDescriptor versionDescriptor,
            final boolean production, final Provider... providers)
    {

        Provider[] prov = Application.get().getMetaData(PROVIDER_KEY);
        if (prov == null)
        {
            prov = providers;
        }

        if (prov != null)
        {
            for (final Provider provider : prov)
            {
                final HeaderContributor hc = provider.getHeaderContributor(versionDescriptor, production);
                if (hc != null)
                {
                    return hc;
                }
            }
        }

        final ResourceReference reference = JSReference.getReference(versionDescriptor, production);
        if (reference != null)
        {
            return JavascriptPackageResource.getHeaderContribution(reference);
        }
        else
        {
            return null;
        }
    }
}
