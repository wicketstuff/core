package org.wicketstuff.minis.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.PackageMapper;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.AbstractMapper;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.PackageName;

/**
 * <p>
 * Extended PackageMapper with Named Parameter and HomePage support. Remember to add this mapper
 * before anyone else, so it will have low priority against top-level, more important mappers.
 * </p>
 * 
 * <p>
 * Usage:
 * 
 * <pre>
 * add(new ExtendedPackageMapper("${username}/static/${group}", Profile.class) {
 *          protected boolean validateParameters(PageParameters parameters) { 
 *             String username = parameters.get("username").toString(); 
 *             String group = parameters.get("group").toString();
 *             return validUsername(username)
 *          }
 * });
 * </pre>
 * 
 * </p>
 * <p>
 * Users can then use URLs like:
 * <ul>
 * <li>http://localhost:8080/john/static/admin -> renders Profile page</li>
 * <li>http://localhost:8080/john/static/admin/Profile -> renders Profile page</li>
 * <li>http://localhost:8080/john/static/admin/Groups -> renders Groups page present in same package
 * as <code>Profile</code></li>
 * </ul>
 * Parameters can be accessed as usual (PageParameters in constructor, for example)
 * </p>
 * 
 * @author Bruno Borges
 */
public class ExtendedPackageMapper extends AbstractMapper
{

	private PackageMapper mountedMapper;
	private String mountedPath;
	private String[] mountedSegments;
	private String packageName;
	private String homePageName;

	/**
	 * <p>
	 * Construct an ExtendedPackageMapper for package of <code>homePage</code> class, at
	 * <code>mountPath</code>. <code>homePage</code> is considered the Home Page for this mounted
	 * path, in case the user does not indicate which page to render.
	 * </p>
	 * 
	 * <p>
	 * Named parameters are optional and may be defined as the first mount segment
	 * </p>
	 * 
	 * @param mountPath
	 *            in the form of "${some}/path/with/${named}/parameters"
	 * @param homePage
	 */
	public <P extends Page> ExtendedPackageMapper(String mountPath, Class<P> homePage)
	{
		homePageName = homePage.getSimpleName();
		mountedPath = mountPath;
		mountedSegments = getMountSegments(mountedPath);

		PackageName pkgNameObj = PackageName.forClass(homePage);
		packageName = pkgNameObj.getName();
		mountedMapper = new PackageMapper(packageName, pkgNameObj);
	}

	public Url mapHandler(IRequestHandler requestHandler)
	{
		Url url = mountedMapper.mapHandler(requestHandler);

		if (url != null)
		{
			List<String> newUrlSegments = new ArrayList<String>(mountedSegments.length);
			for (String segment : mountedSegments)
			{
				String newSegment = segment;
				String placeholder = getPlaceholder(segment);
				if (placeholder != null)
				{
					// segment is parameter
					// set it as Url segment and remove it from QueryParameters
					newSegment = url.getQueryParameter(placeholder).getValue();
					url.removeQueryParameters(placeholder);
				}
				newUrlSegments.add(newSegment);
			}
			List<String> urlSegments = url.getSegments();
			urlSegments.remove(0);
			urlSegments.addAll(0, newUrlSegments);

			int lastIndex = urlSegments.size() - 1;
			if (urlSegments.get(lastIndex).equals(homePageName))
			{
				urlSegments.remove(lastIndex);
			}
		}
		return url;
	}

	/**
	 * Override this to validate parameters. Return true if this Mapper should handle the current
	 * request based on your busines slogic
	 * 
	 * @param parameters
	 * @return true if this Mapper will handle the request. default: true
	 */
	protected boolean validateParameters(PageParameters parameters)
	{
		return true;
	}

	public IRequestHandler mapRequest(Request request)
	{
		Url url = request.getUrl();

		List<String> urlSegments = url.getSegments();
		int countUrlSegments = urlSegments.size();
		if (countUrlSegments < mountedSegments.length)
		{
			return null;
		}

		PageParameters mountedParameters = new PageParameters();
		int i = 0;
		Iterator<String> urlSegmentsIterator = urlSegments.iterator();
		for (String mountedSegment : mountedSegments)
		{
			if (i == countUrlSegments)
			{
				break;
			}

			String urlSegmentValue = urlSegmentsIterator.next();
			String name = getPlaceholder(mountedSegment);
			if (name != null)
			{
				mountedParameters.add(name, urlSegmentValue);
				url.setQueryParameter(name, urlSegmentValue);
				urlSegmentsIterator.remove();
			}
			i++;
		}

		if (!validateParameters(mountedParameters))
		{
			return null;
		}

		// replace with packageName segment so PackageMapper can do its magic
		if (urlSegments.size() == 0)
		{
			urlSegments.add(packageName);
			urlSegments.add(homePageName);
		}
		else
		{
			String lastSegment = urlSegments.get(urlSegments.size() - 1);

			urlSegments.clear();
			urlSegments.add(packageName);
			if (isPackageClass(lastSegment))
			{
				urlSegments.add(lastSegment);
			}
			else
			{
				urlSegments.add(homePageName);
			}
		}

		IRequestHandler mapRequest = mountedMapper.mapRequest(request.cloneWithUrl(url));
		if (mapRequest == null)
		{
			// try adding the homePage class
			if (urlSegments.size() == 1)
			{
				urlSegments.add(homePageName);
			}
			else
			{
				urlSegments.set(1, homePageName);
			}
			mapRequest = mountedMapper.mapRequest(request.cloneWithUrl(url));
		}
		return mapRequest;
	}

	private boolean isPackageClass(String lastSegment)
	{
		String fqcn = packageName + '.' + lastSegment;
		Class resolved = null;
		try
		{
			if (Application.exists())
			{
				resolved = Application.get()
					.getApplicationSettings()
					.getClassResolver()
					.resolveClass(fqcn);
			}

			if (resolved == null)
			{
				resolved = Class.forName(fqcn, false, Thread.currentThread()
					.getContextClassLoader());
			}

			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Scores the compatibility of Request with this Mapper.
	 * 
	 * <ul>
	 * <li>+1 for segments count equals mountedSegments+1 (page referenced)</li>
	 * <li>+1 for segments count equals mountedSegments</li>
	 * <li>+2+(score/2) for static segments</li>
	 * </ul>
	 */
	public int getCompatibilityScore(Request request)
	{
		Url url = request.getUrl();
		int score = 0;

		List<String> urlSegs = url.getSegments();
		int segmentsCount = urlSegs.size();

		// path + Page
		if (segmentsCount == mountedSegments.length + 1)
		{
			score++;
		}

		// only path
		if (segmentsCount == mountedSegments.length)
		{
			score++;
		}

		// for each static segment matched, it will get 2 score + (scores / 2)
		for (int i = 0; i < mountedSegments.length; i++)
		{
			if (i == segmentsCount)
			{
				break;
			}

			String mountedSegment = mountedSegments[i];
			String urlSegment = urlSegs.get(i);
			if (getPlaceholder(mountedSegment) == null && mountedSegment.equals(urlSegment))
			{
				// static
				score = 2 + score + (score / 2);
			}
		}

		return score;
	}

}
