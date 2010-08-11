package org.wicketstuff.yui.markup.html.contributor.yuiloader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.yui.helper.JSArray;
import org.wicketstuff.yui.inc.YUI;

public abstract class YuiLoader implements IHeaderContributor
{

	private static final long serialVersionUID = 1L;

	private static String BUILD = "2.7.0b";

	protected static String DEBUG = "'debug'";

	private static final ResourceReference YUILOADER = new ResourceReference(YUI.class, BUILD
			+ "/yuiloader/yuiloader.js");

	protected static final ResourceReference BASE = new ResourceReference(YUI.class, BUILD + "/");

	private List<IYuiLoaderModule> modules = new ArrayList<IYuiLoaderModule>();

	protected boolean rendered = false;

	/**
	 * loads the loader
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(YUILOADER);
	}

	/**
	 * ['module1', 'module2', 'yahoo']
	 * 
	 * @return
	 */
	protected String getRequireModule()
	{
		JSArray requires = new JSArray();
		for (IYuiLoaderModule m : uniqueModuleNameIter())
		{
			requires.add("\'" + m.getName() + "\'");
		}
		return requires.toString();
	}

	/**
	 * get all the modules taking care of duplicates
	 * 
	 * @param loader
	 * @return
	 */
	protected String getAddModuleJS(String loader)
	{
		StringBuffer bufy = new StringBuffer();
		for (IYuiLoaderModule m : uniqueModuleNameIter())
		{
			bufy.append("\n").append(loader + ".addModule(" + m.getModuleJS() + ");");
		}
		return bufy.toString();
	}

	/**
	 * go through all modules and then add their initJS scripts
	 * 
	 * @return
	 */
	protected String allOnSuccessJS()
	{
		StringBuffer bufy = new StringBuffer();
		for (IYuiLoaderModule m : getModules())
		{
			bufy.append("\n").append(m.onSuccessJS());
		}
		return bufy.toString();
	}

	/**
	 * @return
	 */
	private Iterable<IYuiLoaderModule> uniqueModuleNameIter()
	{
		Set<IYuiLoaderModule> set = new HashSet<IYuiLoaderModule>();

		for (IYuiLoaderModule m : getModules())
		{
			set.add(m);
		}

		return set;
	}

	protected static String getBase()
	{
		return RequestCycle.get().urlFor(BASE).toString();
	}

	public List<IYuiLoaderModule> getModules()
	{
		return modules;
	}

	public void setModules(List<IYuiLoaderModule> modules)
	{
		this.modules = modules;
	}

	public void add(IYuiLoaderModule module)
	{
		getModules().add(module);
	}

	protected abstract YuiLoader newLoader();
};