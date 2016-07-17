package org.apache.wicket.portlet.request.mapper;

import java.util.Iterator;
import java.util.function.Supplier;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.SystemMapper;
import org.apache.wicket.core.request.mapper.BookmarkableMapper;
import org.apache.wicket.core.request.mapper.HomePageMapper;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.CompoundRequestMapper;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * <p>
 * PortletSystemMapper modifies the behavior of its encapsulated {@link BookmarkableMapper} and
 * {@link HomePageMapper} in order to make wicket functional in portlet environment.
 * </p>
 * 
 * @author Konstantinos Karavitis
 */
public class PortletSystemMapper extends SystemMapper
{

	private MapperDelegate delegate = new MapperDelegate();

	
	/**
	 * Mounts a page class of the application to the given path.
	 * @param <T>
	 * 
	 * @param <T>
	 *            type of page
	 * 
	 * @param application
	 * 			
	 * @param path
	 *            the path to mount the page class on
	 * @param pageClass
	 *            the page class to be mounted
	 */
	public static <T extends Page> MountedMapper mountPage(final WebApplication application, final String path, final Class<T> pageClass)
	{
		MountedMapper mapper = new MountedMapper(path, pageClass) {
			private MapperDelegate delegate = new MapperDelegate();
			
			@Override
			protected IRequestHandler processHybrid(PageInfo pageInfo,
					Class<? extends IRequestablePage> pageClass,
					PageParameters pageParameters, Integer renderCount) {
				
				return delegate.processHybrid(pageInfo, pageClass, pageParameters, renderCount);
			}
		};
		application.mount(mapper);
		
		return mapper;
	}
	
	/**
	 * @param application
	 */
	public PortletSystemMapper(Application application)
	{
		super(application);
		addMountedMappers(application);
	}
	
	protected void addMountedMappers(Application application) {
		IRequestMapper rootMapper = application.getRootRequestMapper();
		if (rootMapper instanceof CompoundRequestMapper) {
			Iterator<IRequestMapper> iterator = ((CompoundRequestMapper)rootMapper).iterator();
			while (iterator.hasNext()) {
				IRequestMapper requestMapper = iterator.next();
				if (requestMapper instanceof MountedMapper && !(requestMapper instanceof HomePageMapper)) {
					add(requestMapper);
				}
			}
		}
	}

	/**
	 * @see org.apache.wicket.SystemMapper#newBookmarkableMapper()
	 */
	@Override
	protected IRequestMapper newBookmarkableMapper()
	{
		return new BookmarkableMapper()
		{
			@Override
			protected IRequestHandler processHybrid(PageInfo pageInfo,
				Class<? extends IRequestablePage> pageClass, PageParameters pageParameters,
				Integer renderCount)
			{
				return delegate.processHybrid(pageInfo, pageClass, pageParameters, renderCount);
			}

			@Override
			protected Url encodePageParameters(Url url, PageParameters pageParameters,
				IPageParametersEncoder encoder)
			{
				return delegate.encodePageParameters(url, pageParameters, encoder);
			}
		};
	}

	/**
	 * @see org.apache.wicket.SystemMapper#newHomePageMapper(Supplier)
	 */
	@Override
	protected IRequestMapper newHomePageMapper(Supplier<Class<? extends IRequestablePage>> homePageProvider)
	{
		return new HomePageMapper(homePageProvider)
		{
			@Override
			protected IRequestHandler processHybrid(PageInfo pageInfo,
				Class<? extends IRequestablePage> pageClass, PageParameters pageParameters,
				Integer renderCount)
			{
				return delegate.processHybrid(pageInfo, pageClass, pageParameters, renderCount);
			}

			@Override
			protected Url encodePageParameters(Url url, PageParameters pageParameters,
				IPageParametersEncoder encoder)
			{
				return delegate.encodePageParameters(url, pageParameters, encoder);
			}
		};
	}

}
