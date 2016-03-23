package org.apache.wicket.portlet.request.mapper;

import org.apache.wicket.Application;
import org.apache.wicket.SystemMapper;
import org.apache.wicket.core.request.mapper.BookmarkableMapper;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Url;
import org.apache.wicket.core.request.mapper.HomePageMapper;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.IProvider;

/**
 * see {@link org.apache.wicket.SystemMapper}.
 * 
 * PortletSystemMapper encapsulates the {@link HomePageMapper} instead of the
 * {@link org.apache.wicket.core.request.mapper.HomePageMapper} and the {@link BookmarkableMapper}
 * instead of the {@link org.apache.wicket.core.request.mapper.BookmarkableMapper}
 * 
 * @author Konstantinos Karavitis
 */
public class PortletSystemMapper extends SystemMapper
{

	private MapperDelegate delegate = new MapperDelegate();

	/**
	 * @param application
	 */
	public PortletSystemMapper(Application application)
	{
		// TODO Implement this method.
		super(application);
	}

	/**
	 * @see org.apache.wicket.SystemMapper#newBookmarkableMapper()
	 */
	@Override
	protected IRequestMapper newBookmarkableMapper()
	{
		return new BookmarkableMapper()
		{
			/**
			 * @see org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper#processHybrid(org.apache.wicket.request.mapper.info.PageInfo,
			 *      java.lang.Class, org.apache.wicket.request.mapper.parameter.PageParameters,
			 *      java.lang.Integer)
			 */
			@Override
			protected IRequestHandler processHybrid(PageInfo pageInfo,
				Class<? extends IRequestablePage> pageClass, PageParameters pageParameters,
				Integer renderCount)
			{
				return delegate.processHybrid(pageInfo, pageClass, pageParameters, renderCount);
			}

			/**
			 * @see org.apache.wicket.request.mapper.AbstractMapper#encodePageParameters(org.apache.wicket.request.Url,
			 *      org.apache.wicket.request.mapper.parameter.PageParameters,
			 *      org.apache.wicket.request.mapper.parameter.IPageParametersEncoder)
			 */
			@Override
			protected Url encodePageParameters(Url url, PageParameters pageParameters,
				IPageParametersEncoder encoder)
			{
				return delegate.encodePageParameters(url, pageParameters, encoder);
			}
		};
	}

	/**
	 * @see org.apache.wicket.SystemMapper#newHomePageMapper(org.apache.wicket.util.IProvider)
	 */
	@Override
	protected IRequestMapper newHomePageMapper(
		IProvider<Class<? extends IRequestablePage>> homePageProvider)
	{

		return new HomePageMapper(homePageProvider)
		{
			/**
			 * @see org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper#processHybrid(org.apache.wicket.request.mapper.info.PageInfo,
			 *      java.lang.Class, org.apache.wicket.request.mapper.parameter.PageParameters,
			 *      java.lang.Integer)
			 */
			@Override
			protected IRequestHandler processHybrid(PageInfo pageInfo,
				Class<? extends IRequestablePage> pageClass, PageParameters pageParameters,
				Integer renderCount)
			{
				return delegate.processHybrid(pageInfo, pageClass, pageParameters, renderCount);
			}

			/**
			 * @see org.apache.wicket.request.mapper.AbstractMapper#encodePageParameters(org.apache.wicket.request.Url,
			 *      org.apache.wicket.request.mapper.parameter.PageParameters,
			 *      org.apache.wicket.request.mapper.parameter.IPageParametersEncoder)
			 */
			@Override
			protected Url encodePageParameters(Url url, PageParameters pageParameters,
				IPageParametersEncoder encoder)
			{
				return delegate.encodePageParameters(url, pageParameters, encoder);
			}
		};
	}

}