package org.wicketstuff.yui.markup.html.contributor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.yui.inc.YUI;

/**
 * 
 * @author josh
 * 
 */
public class YuiLoaderContributor extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private static String BUILD = "2.6.0";

	private static String DEBUG = "'debug'";

	private Set<YuiLoaderModule> moduleCache = Collections
			.synchronizedSet(new HashSet<YuiLoaderModule>());

	private StringBuffer initJS = new StringBuffer();

	private static final ResourceReference YUILOADER_DOM_EVENT = new ResourceReference(YUI.class,
			BUILD + "/yuiloader-dom-event/yuiloader-dom-event.js");

	private YuiLoaderContributor()
	{
	}

	private static ThreadLocal<YuiLoaderContributor> loader = new ThreadLocal<YuiLoaderContributor>()
	{
		@Override
		protected synchronized YuiLoaderContributor initialValue()
		{
			return new YuiLoaderContributor();
		}
	};

	public static IBehavior addModule(YuiLoaderModule yuiLoaderModule)
	{
		loader.get().moduleCache.add(yuiLoaderModule);
		loader.get().initJS.append("\n" + yuiLoaderModule.getInitJS());
		return loader.get();
	}

	@Override
	public void detach(Component component)
	{
		loader.remove();
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(YUILOADER_DOM_EVENT);

		StringBuffer bufy = new StringBuffer();
		bufy.append("( function() { var loader = new YAHOO.util.YUILoader(); ");
		bufy.append(getAddCustomYuiModuleJS());

		if (Application.DEVELOPMENT.equals(Application.get().getConfigurationType()))
		{
			bufy.append("loader.filter=" + DEBUG + ";");
		}

		bufy.append("loader.combine=false;");
		bufy.append("loader.onSuccess = " + getModuleInitJS());
		// bufy.append("loader.onFailure = " + getModuleFailJS());
		// bufy.append("loader.onProgress = " + getModuleProgressJS());
		bufy.append("loader.insert();");
		bufy.append("})();");

		String id = null;
		response.renderJavascript(bufy.toString(), id);
	}

	protected String getModuleProgressJS()
	{
		return "function(m) { alert(YAHOO.lang.dump(m)); };";
	}

	protected String getModuleFailJS()
	{
		StringBuffer bufy = new StringBuffer();
		bufy.append("function(msg, xhrobj) { ");
		bufy.append("var m = msg;");
		bufy.append("if (xhrobj) { m += \", \" + YAHOO.lang.dump(xhrobj); }");
		bufy.append("alert(msg);");
		bufy.append("};");
		return bufy.toString();
	}

	private String getModuleInitJS()
	{
		StringBuffer bufy = new StringBuffer();

		bufy.append("function() { ");
		bufy.append(initJS.toString());
		bufy.append("};");
		return bufy.toString();
	}

	private String getAddCustomYuiModuleJS()
	{
		StringBuffer bufy = new StringBuffer();

		for (YuiLoaderModule m : moduleCache)
		{
			bufy.append("loader.addModule(" + m.getModuleDataJS() + ");");
			bufy.append("loader.require(\"" + m.getName() + "\");");
		}
		return bufy.toString();
	}

}
