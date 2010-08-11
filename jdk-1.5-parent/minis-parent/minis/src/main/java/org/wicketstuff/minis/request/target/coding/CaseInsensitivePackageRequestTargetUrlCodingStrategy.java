package org.wicketstuff.minis.request.target.coding;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.PageParameters;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.coding.PackageRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkableListenerInterfaceRequestTarget;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.util.lang.PackageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mhanlon
 *
 */
public class CaseInsensitivePackageRequestTargetUrlCodingStrategy extends
		PackageRequestTargetUrlCodingStrategy {

	private static final Logger log = LoggerFactory.getLogger(CaseInsensitivePackageRequestTargetUrlCodingStrategy.class);
	
	public CaseInsensitivePackageRequestTargetUrlCodingStrategy(final String path, PackageName packageName) {
		super(path, packageName);
		this.packageName = packageName;
	}

	/** package for this mount. */
	private final PackageName packageName;
	
	private IClassResolver resolver = new CaseInsensitiveClassResolver();
	
	/**
	 * @see org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy#decode(org.apache.wicket.request.RequestParameters)
	 */
	public IRequestTarget decode(RequestParameters requestParameters)
	{
		String remainder = requestParameters.getPath().substring(getMountPath().length());
		final String parametersFragment;
		int ix = remainder.indexOf('/', 1);
		if (ix == -1)
		{
			ix = remainder.length();
			parametersFragment = "";
		}
		else
		{
			parametersFragment = remainder.substring(ix);
		}

		if (remainder.startsWith("/"))
		{
			remainder = remainder.substring(1);
			ix--;
		}
		else
		{
			// There is nothing after the mount path!
			return null;
		}

		final String bookmarkablePageClassName = packageName + "." + remainder.substring(0, ix);
		Class bookmarkablePageClass;
		try
		{
			bookmarkablePageClass = resolver.resolveClass(bookmarkablePageClassName);
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			return null;
		}
		PageParameters parameters = new PageParameters(decodeParameters(parametersFragment,
				requestParameters.getParameters()));

		String pageMapName = (String)parameters.remove(WebRequestCodingStrategy.PAGEMAP);
		pageMapName = WebRequestCodingStrategy.decodePageMapName(pageMapName);
		requestParameters.setPageMapName(pageMapName);

		// do some extra work for checking whether this is a normal request to a
		// bookmarkable page, or a request to a stateless page (in which case a
		// wicket:interface parameter should be available
		final String interfaceParameter = (String)parameters
				.remove(WebRequestCodingStrategy.INTERFACE_PARAMETER_NAME);

		if (interfaceParameter != null)
		{
			WebRequestCodingStrategy.addInterfaceParameters(interfaceParameter, requestParameters);
			return new BookmarkableListenerInterfaceRequestTarget(pageMapName,
					bookmarkablePageClass, parameters, requestParameters.getComponentPath(),
					requestParameters.getInterfaceName(), requestParameters.getVersionNumber());
		}
		else
		{
			return new BookmarkablePageRequestTarget(pageMapName, bookmarkablePageClass, parameters);
		}
	}
}
