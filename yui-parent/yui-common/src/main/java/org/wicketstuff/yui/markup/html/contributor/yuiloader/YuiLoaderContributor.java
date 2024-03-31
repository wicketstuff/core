package org.wicketstuff.yui.markup.html.contributor.yuiloader;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.IHeaderContributor;

import java.util.ArrayList;
import java.util.List;

/**
 * a contributor that handles YuiLoaderModules
 * 
 * @author josh
 * 
 */
public class YuiLoaderContributor extends Behavior implements IHeaderContributor
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

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        this.renderHead(response);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        for (IHeaderContributor iHeaderContributor : getHeaderContributors()) {
            iHeaderContributor.renderHead(response);
        }
    }

    public static Behavior addModule(IYuiLoaderModule yuiLoaderModule)
	{
		return addModule(yuiLoaderModule, false);
	}

	/**
	 * 
	 * @param module
	 * @param useSandbox
	 * @return
	 */
	public static Behavior addModule(IYuiLoaderModule module, boolean useSandbox)
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

	public void detach(Component component)
	{
		threadLocalLoader.remove();
	}

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
