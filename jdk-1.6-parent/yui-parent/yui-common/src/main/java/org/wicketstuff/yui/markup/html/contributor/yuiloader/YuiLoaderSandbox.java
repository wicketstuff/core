package org.wicketstuff.yui.markup.html.contributor.yuiloader;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Using yuiloader.sandbox to load dependecies
 * 
 * @author josh
 * 
 */
public class YuiLoaderSandbox extends YuiLoader
{

	private static final long serialVersionUID = 1L;

	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		StringBuffer bufy = new StringBuffer();
		bufy.append("\n").append("( function() { ");
		bufy.append("\n").append("var loader = new YAHOO.util.YUILoader();");
		bufy.append("\n").append(getAddModuleJS("loader"));

		if (Application.get().usesDevelopmentConfig())
		{
			bufy.append("\n").append("loader.filter=" + DEBUG + ";");
		}
		bufy.append("loader.base = '" + (String) RequestCycle.get().urlFor(BASE, null) + "';");
		bufy.append("loader.combine = false;");
		bufy.append("\n").append("loader.sandbox({ " + getSandboxConfig() + " });");
		bufy.append("\n").append("})();");

		String id = null;
		response.render(JavaScriptHeaderItem.forScript(bufy.toString(), id));
		rendered = true;
	}

	private String getSandboxConfig()
	{
		StringBuffer bufy = new StringBuffer();

		bufy.append("\n").append("require:").append(getRequireModule()).append(",");
		bufy.append("\n").append("base:").append("\"" + getBase() + "\"").append(",");
		bufy.append("\n").append("loadOptional:").append("true").append(",");
		bufy.append("\n").append("onSuccess:").append(getOnSuccessJS()).append(",");
		return bufy.toString();
	}

	private Object getOnSuccessJS()
	{
		StringBuffer bufy = new StringBuffer();
		bufy.append("\n").append("function(o) { ");
		bufy.append("\n").append("var YAHOO = o.reference;");
		bufy.append("\n").append(allOnSuccessJS());
		bufy.append("\n").append("}");
		return bufy.toString();
	}

	@Override
	protected YuiLoader newLoader()
	{
		return new YuiLoaderSandbox();
	}
}
