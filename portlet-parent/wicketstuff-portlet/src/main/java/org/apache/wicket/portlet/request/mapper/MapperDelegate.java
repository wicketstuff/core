package org.apache.wicket.portlet.request.mapper;

import org.apache.wicket.Application;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.Url.QueryParameter;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;

/**
 * <p>
 * Performs the processHybrid and encodePageParameters tasks for the {@link org.apache.wicket.core.request.mapper.BookmarkableMapper}
 * and {@link org.apache.wicket.core.request.mapper.HomePageMapper} instances which are encapsulated in {@link PortletSystemMapper}
 * </p>
 * <p>
 * See also {@link PortletSystemMapper#newBookmarkableMapper()} and
 * {@link PortletSystemMapper#newHomePageMapper(java.util.function.Supplier)}
 * </p>
 *
 * @author Konstantinos Karavitis
 */
public class MapperDelegate
{

	/**
	 * Creates a {@code IRequestHandler} that processes a hybrid request. When the page identified
	 * by {@code pageInfo} was not available, the request should be treated as a bookmarkable
	 * request.
	 *
	 * @param pageInfo
	 * @param pageClass
	 * @param pageParameters
	 * @param renderCount
	 * @return a {@code IRequestHandler} capable of processing the hybrid request.
	 */
	protected IRequestHandler processHybrid(PageInfo pageInfo,
		Class<? extends IRequestablePage> pageClass, PageParameters pageParameters,
		Integer renderCount)
	{
		PageProvider provider = new PageProvider(pageInfo.getPageId(), pageClass, pageParameters,
			renderCount);
		Application application = Application.get();
		provider.setPageSource(application.getMapperContext());
		if (!provider.hasPageInstance() &&
			!application.getPageSettings().getRecreateBookmarkablePagesAfterExpiry())
		{
			throw new PageExpiredException(String.format(
				"Bookmarkable page id '%d' class '%s' has expired.", pageInfo.getPageId(),
				pageClass.getName()));
		}
		return new RenderPageRequestHandler(provider);
	}

	/**
	 * Encodes the given {@link PageParameters} to the URL using the given
	 * {@link IPageParametersEncoder}. The original URL object is unchanged.
	 *
	 * @param url
	 * @param pageParameters
	 * @param encoder
	 * @return URL with encoded parameters
	 */
	protected Url encodePageParameters(Url url, PageParameters pageParameters,
		final IPageParametersEncoder encoder)
	{
		Args.notNull(url, "url");
		Args.notNull(encoder, "encoder");

		if (pageParameters == null)
		{
			pageParameters = new PageParameters();
		}

		Url parametersUrl = encoder.encodePageParameters(pageParameters);
		if (parametersUrl != null)
		{
			// copy the url
			url = new Url(url);

			for (String s : parametersUrl.getSegments())
			{
				url.getSegments().add(s);
			}
			for (QueryParameter p : parametersUrl.getQueryParameters())
			{
				// avoid duplicate parameter names.
				if (!url.getQueryParameters().contains(p))
				{
					url.getQueryParameters().add(p);
				}
			}
		}

		return url;
	}
}
