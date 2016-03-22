package org.apache.wicket.portlet;

import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageExceptionMapper;
import org.apache.wicket.UrlResourceReferenceMapper;

import org.apache.wicket.portlet.request.mapper.BookmarkableMapper;
import org.apache.wicket.portlet.request.mapper.HomePageMapper;

import org.apache.wicket.core.request.mapper.BufferedResponseMapper;
import org.apache.wicket.core.request.mapper.PageInstanceMapper;
import org.apache.wicket.core.request.mapper.ResourceReferenceMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.CompoundRequestMapper;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;
import org.apache.wicket.request.resource.caching.IResourceCachingStrategy;
import org.apache.wicket.util.IProvider;

/**
 * see {@link org.apache.wicket.SystemMapper}.
 * 
 * PortletSystemMapper encapsulates the {@link HomePageMapper} instead of the {@link org.apache.wicket.core.request.mapper.HomePageMapper}
 * and the {@link BookmarkableMapper} instead of the {@link org.apache.wicket.core.request.mapper.BookmarkableMapper} 
 * 
 * @author Konstantinos Karavitis
 */
public class PortletSystemMapper extends CompoundRequestMapper
{
	private final Application application;

	/**
	 * Constructor
	 * 
	 * @param application
	 */
	public PortletSystemMapper(final Application application)
	{
		this.application = application;
		add(new PageInstanceMapper());
		add(new BookmarkableMapper());
		add(new HomePageMapper(new HomePageProvider(application)));
		add(new ResourceReferenceMapper(new PageParametersEncoder(),
			new ParentFolderPlaceholderProvider(application), getResourceCachingStrategy()));
		add(new UrlResourceReferenceMapper());
		add(RestartResponseAtInterceptPageExceptionMapper.getMapper());
		add(new BufferedResponseMapper());
	}

	private IProvider<IResourceCachingStrategy> getResourceCachingStrategy()
	{
		return new IProvider<IResourceCachingStrategy>()
		{
			@Override
			public IResourceCachingStrategy get()
			{
				return application.getResourceSettings().getCachingStrategy();
			}
		};
	}

	private static class ParentFolderPlaceholderProvider implements IProvider<String>
	{
		private final Application application;

		public ParentFolderPlaceholderProvider(Application application)
		{
			this.application = application;
		}

		@Override
		public String get()
		{
			return application.getResourceSettings().getParentFolderPlaceholder();
		}
	}

	private static class HomePageProvider<C extends IRequestablePage>
		implements
			IProvider<Class<C>>
	{
		private final Application application;

		private HomePageProvider(final Application application)
		{
			this.application = application;
		}

		@Override
		public Class<C> get()
		{
			return (Class<C>)application.getHomePage();
		}
	}
}