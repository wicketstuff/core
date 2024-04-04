package org.wicketstuff.yui.markup.html.contributor.yuiloader;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.yui.helper.JSArray;
import org.wicketstuff.yui.helper.JSObject;

/**
 * this is a Custom YUI Module that works with YuiLoaderContributor
 *
 * @author josh
 */
public class YuiLoaderModule implements IYuiLoaderModule {

    private static final long serialVersionUID = 1L;

    public enum ModuleType {
        js, css
    }

    private String name;

    private ModuleType type;

    private String fullpath;

    private String[] requires;

    public YuiLoaderModule(String name, ModuleType type, String fullpath, String[] requires) {
        this.name = name;
        this.type = type;
        this.fullpath = fullpath;
        this.requires = requires;
    }

    public YuiLoaderModule(String name, ModuleType type, ResourceReference ref, String[] requires) {
        this(name, type, RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(RequestCycle.get().urlFor(ref, null))), requires);
    }

    /**
     * returns the object is JS
     *
     * @return
     */
    public String getModuleJS() {
        JSObject<String> js = new JSObject<String>();
        js.add("name", name);
        js.add("type", type.toString());
        js.add("fullpath", fullpath);
        js.add("requires", new JSArray(requires));
        return js.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + name.hashCode();
        hash = 31 * hash + type.hashCode();
        hash = 31 * hash + fullpath.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof YuiLoaderModule))
            return false;
        YuiLoaderModule castOther = (YuiLoaderModule) other;
        return this.name.equals(castOther.name) && this.type.equals(castOther.type)
                && this.fullpath.equals(castOther.fullpath);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModuleType getType() {
        return type;
    }

    public void setType(ModuleType type) {
        this.type = type;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public String[] getRequires() {
        return requires;
    }

    public void setRequires(String[] requires) {
        this.requires = requires;
    }

    public String onSuccessJS() {
        return "";
    }
}