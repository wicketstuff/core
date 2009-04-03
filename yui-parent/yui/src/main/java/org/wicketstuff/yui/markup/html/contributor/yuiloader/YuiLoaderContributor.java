package org.wicketstuff.yui.markup.html.contributor.yuiloader;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractHeaderContributor;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;

/**
 * a contributor that handles YuiLoaderModules
 * 
 * @author josh
 * 
 */
public class YuiLoaderContributor extends AbstractHeaderContributor
{

	private static final long serialVersionUID = 1L;

	/**
	 * ThreadLocal contributor
	 * 
	 * @author josh
	 * 
	 */
	private static class ThreadLocalYuiLoaderContributor extends ThreadLocal<YuiLoaderContributor>
	{
		@Override
		protected YuiLoaderContributor initialValue()
		{
			return new YuiLoaderContributor();
		}
	}

	private static ThreadLocalYuiLoaderContributor threadLocalLoader = new ThreadLocalYuiLoaderContributor();

	private YuiLoader yuiLoader = new YuiLoaderInsert();

	private YuiLoader yuiLoaderSandbox = new YuiLoaderSandbox();

	private YuiLoaderContributor()
	{
	}

	public static IBehavior addModule(IYuiLoaderModule yuiLoaderModule)
	{
		return addModule(yuiLoaderModule, false);
	}

	/**
	 * 
	 * @param module
	 * @param useSandbox
	 * @return
	 */
	public static IBehavior addModule(IYuiLoaderModule module, boolean useSandbox)
	{
		YuiLoaderContributor loader = getCurrentLoader();
		if (useSandbox)
		{
			loader.yuiLoaderSandbox = addModule(loader.yuiLoaderSandbox, module);
		}
		else
		{
			loader.yuiLoader = addModule(loader.yuiLoader, module);
		}
		return loader;
	}

	private static YuiLoader addModule(YuiLoader loader, IYuiLoaderModule module)
	{
		if (loader.rendered)
		{
			loader = loader.newLoader();
		}
		loader.add(module);
		return loader;
	}


	private static YuiLoaderContributor getCurrentLoader()
	{
		YuiLoaderContributor loader = threadLocalLoader.get();
		return loader;
	}

	@Override
	public void detach(Component component)
	{
		threadLocalLoader.remove();
	}

	@Override
	public IHeaderContributor[] getHeaderContributors()
	{
		List<IHeaderContributor> contributors = new ArrayList<IHeaderContributor>();

		if ((yuiLoader != null) && !(yuiLoader.getModules().isEmpty()))
		{
			contributors.add(yuiLoader);
		}

		if ((yuiLoaderSandbox != null) && !(yuiLoaderSandbox.getModules().isEmpty()))
		{
			contributors.add(yuiLoaderSandbox);
		}

		return contributors.toArray(new IHeaderContributor[contributors.size()]);
	}
}
