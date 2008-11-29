package org.wicketstuff.yui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractHeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YuiHeaderContributor extends AbstractHeaderContributor
{
    
    
    private static final Logger log = LoggerFactory.getLogger(YuiHeaderContributor.class);
    
    static final String DEFAULT_YUI_BUILD = "2.5.2";
    static final String YUI_BUILD_ROOT = "inc";
    
    static final Map<String, ResourceReference> moduleCache = Collections.synchronizedMap(new HashMap<String, ResourceReference>());
    
    static final YuiDependencyResolver dependencyResolver = new YuiDependencyResolver();
    
    List<YuiModuleHeaderContributor> contributors = new ArrayList<YuiModuleHeaderContributor>();
    

    @Override
    public IHeaderContributor[] getHeaderContributors()
    {
        return contributors.toArray(new IHeaderContributor[]{});
    }
    
    private void addModuleHeaderContributor(String name, String build, boolean debug)
    {
        contributors.add(new YuiModuleHeaderContributor(name, build, debug));
    }
    
    public static YuiHeaderContributor forModule(String module) {
        return forModule(module, null);
    }
    
    public static YuiHeaderContributor forModule(String module, String[] optionalDependencies) {
        return forModule(module, optionalDependencies, false);
    }
    
    public static YuiHeaderContributor forModule(String name, String[] optionalDependencies, boolean debug)
    {
        return forModule(name, optionalDependencies, debug, DEFAULT_YUI_BUILD);
    }
    
    public static YuiHeaderContributor forModule(String name, String[] optionalDependencies, boolean debug, String build)
    {
        YuiHeaderContributor yhc = new YuiHeaderContributor();
        Set<String> dependencies = dependencyResolver.resolveDependencies(name, YUI_BUILD_ROOT + "/" + build);
        for (String dep : dependencies) {
            yhc.addModuleHeaderContributor(dep, build, debug);
        }
        if(null != optionalDependencies && optionalDependencies.length != 0) {
            for (String opts : optionalDependencies) {
                yhc.addModuleHeaderContributor(opts, build, debug);
            }
        }
        
        yhc.addModuleHeaderContributor(name, build, debug);
        
        return yhc;
    }
    
    class YuiModuleHeaderContributor implements IHeaderContributor 
    {
        private final String name;
        private final String build;
        private final boolean debug;
        
        
        
        public YuiModuleHeaderContributor(String name, String build, boolean debug)
        {
            this.name = name;
            this.build = build;
            this.debug = debug;
        }


        private String getRealModuleName(String path, String module) {
            final String fullPath = path + "/" + module + "/";
            URL url = getClass().getResource( fullPath + module + ".js");
            if(null != url) {
                return module;
            }
            
            url = getClass().getResource(fullPath + module + "-beta.js");
            
            if(null != url) {
                return module + "-beta";
            }
            
            url = getClass().getResource(fullPath + module + "-experimental.js");
            
            if(null != url) {
                return module + "experimental";
            }
            
            return null;
        }

        /**
         *  Classic head as YuiLoader is still in Beta
         *
         */        
        public void renderHead(IHeaderResponse response)
        {
            final String buildPath = YUI_BUILD_ROOT + "/" + build;
            
            final String realName = getRealModuleName(buildPath, name);
            
            
            if (null != realName) {
                final String path = buildPath + "/" + name + "/" + realName + ((debug) ? "-debug.js" : ".js");
                final ResourceReference moduleScript;
                if (YuiHeaderContributor.this.moduleCache.containsKey(path)) {
                    moduleScript = YuiHeaderContributor.this.moduleCache.get(path);
                } else {
                    if (debug) {
                        moduleScript = new ResourceReference(YuiHeaderContributor.class, path);
                    } else {
                        moduleScript = new JavascriptResourceReference(YuiHeaderContributor.class, path);
                    }
                    YuiHeaderContributor.this.moduleCache.put(path, moduleScript);
                }
                response.renderJavascriptReference(moduleScript);
                if (dependencyResolver.hasCssAsset(name, YUI_BUILD_ROOT + "/" + build)) {
                    final String assetPath = YUI_BUILD_ROOT + "/" + build + "/" + name + "/assets/" + name + ".css";
                    final ResourceReference assetRef;
                    if (YuiHeaderContributor.this.moduleCache.containsKey(assetPath)) {
                        assetRef = YuiHeaderContributor.this.moduleCache.get(assetPath);
                    } else {
                        assetRef = new CompressedResourceReference(YuiHeaderContributor.class, assetPath);
                        YuiHeaderContributor.this.moduleCache.put(assetPath, assetRef);
                    }

                    response.renderCSSReference(assetRef, "screen");
                }
            } else {
                log.error("Unable to find realName for Yui Module " + name);
            }
            
        }
        
    }

}
