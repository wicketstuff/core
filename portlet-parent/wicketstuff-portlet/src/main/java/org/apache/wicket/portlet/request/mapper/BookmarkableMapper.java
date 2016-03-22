package org.apache.wicket.portlet.request.mapper;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * BookmarkableMapper delegates the {@link #processHybrid(PageInfo, Class, PageParameters, Integer)}
 * and {@link #encodePageParameters(Url, PageParameters, IPageParametersEncoder)} to its {@link MapperDelegate}
 * instance
 * 
 * @author Konstantinos Karavitis
 */
public class BookmarkableMapper extends org.apache.wicket.core.request.mapper.BookmarkableMapper
{

	private MapperDelegate mapperDelegate;

	/**
	 * Construct.
	 */
	public BookmarkableMapper()
	{
		super();
		mapperDelegate = new MapperDelegate();
	}

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

		return mapperDelegate.processHybrid(pageInfo, pageClass, pageParameters,
			renderCount);
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

		return mapperDelegate.encodePageParameters(url, pageParameters, encoder);
	}
}
