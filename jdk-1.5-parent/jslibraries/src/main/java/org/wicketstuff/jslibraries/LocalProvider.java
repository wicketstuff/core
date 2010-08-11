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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.wicketstuff.jslibraries.util.Assert;


public class LocalProvider implements Provider {

    static final LocalProvider DEFAULT = new LocalProvider();
    private Map<Library, String> localFileNames = new HashMap<Library, String>();
    private Map<Library, String> localProductionSignifiers = new HashMap<Library, String>();

    private LocalProvider() {
        //
        localFileNames.put(Library.JQUERY, "jquery");
        localFileNames.put(Library.JQUERY_UI, "jquery-ui");
        localFileNames.put(Library.MOOTOOLS_CORE, "mootools-core");
        localFileNames.put(Library.MOOTOOLS_MORE, "mootools-more");
        localFileNames.put(Library.PROTOTYPE, "prototype");
        localFileNames.put(Library.SCRIPTACULOUS, "scriptaculous");
        localFileNames.put(Library.YUI, "yuiloader");
        localFileNames.put(Library.DOJO, "dojo");
        localFileNames.put(Library.SWFOBJECT, "swfobject");
        localFileNames.put(Library.EXT_CORE, "ext-core");
        //
        localProductionSignifiers.put(Library.JQUERY, ".min");
        localProductionSignifiers.put(Library.JQUERY_UI, ".min");
        localProductionSignifiers.put(Library.MOOTOOLS_CORE, ".min");
        localProductionSignifiers.put(Library.MOOTOOLS_MORE, ".min");
        localProductionSignifiers.put(Library.PROTOTYPE, null);
        localProductionSignifiers.put(Library.SCRIPTACULOUS, null);
        localProductionSignifiers.put(Library.YUI, ".min");
        localProductionSignifiers.put(Library.DOJO, ".min");
        localProductionSignifiers.put(Library.SWFOBJECT, ".min");
        localProductionSignifiers.put(Library.EXT_CORE, ".min");
    }

    public HeaderContributor getHeaderContributor(
            final VersionDescriptor versionDescriptor, boolean production) {
        
        Assert.parameterNotNull(versionDescriptor,
                "versionDescriptor");
        
        return JavascriptPackageResource.getHeaderContribution(JSReference
                .getReference(versionDescriptor, production));
    }

    String getLocalFileName(Library lib) {
        Assert.parameterNotNull(lib, "lib");

        String name = localFileNames.get(lib);
        if (name == null)
            throw new IllegalArgumentException("Library '"+lib+"' is unknown to '"
                    + getClass().getSimpleName() + "'");
        return name;
    }

    String getProductionSignifier(Library lib) {
        Assert.parameterNotNull(lib, "lib");

        String signifier = localProductionSignifiers.get(lib);
        return signifier;
    }

}
