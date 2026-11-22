package org.wicketstuff.hugerte;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.mapper.AbstractMapper;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * A request mapper to enable HugeRTE JS to load at runtime other JS resources
 *
 * @author Pedro Santos, JavaLuigi
 */
public class HugeRteRequestMapper extends AbstractMapper
{

	private static final List<String> KNOWN_SEGMENTS = Arrays.asList("themes", "skins", "models",
		"skins", "icons", "langs", "plugins", "utils", "hugerte.min.js");

	@Override
	public IRequestHandler mapRequest(Request request)
	{
		String url = request.getUrl().toString();
		List<String> segments = request.getUrl().getSegments();

		for (String prefix : KNOWN_SEGMENTS)
		{
			if (segments.contains(prefix))
			{
				String path = "hugerte/" + url.substring(url.indexOf(prefix));
				if (HugeRteRequestMapper.class.getResource(path) != null)
				{
					PackageResourceReference resourceReference = new PackageResourceReference(
						HugeRteRequestMapper.class, path);
					return new ResourceReferenceRequestHandler(resourceReference, null);
				}
			}
		}
		return null;
	}

	@Override
	public int getCompatibilityScore(Request request)
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public Url mapHandler(IRequestHandler requestHandler)
	{
		return null;
	}
}
