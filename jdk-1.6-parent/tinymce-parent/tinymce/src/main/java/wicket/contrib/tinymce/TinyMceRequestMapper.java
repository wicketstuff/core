package wicket.contrib.tinymce;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.handler.resource.ResourceRequestHandler;
import org.apache.wicket.request.mapper.AbstractMapper;
import org.apache.wicket.request.resource.JavaScriptPackageResource;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * A request mapper to enable TinyMCE JS to load at runtime other JS resources
 * 
 * @author Pedro Santos
 */
public class TinyMceRequestMapper extends AbstractMapper
{
	private static final List<String> KNOWN_SEGMENTS = Arrays.asList(new String[] { "themes",
			"langs", "plugins", "utils", "tiny_mce_popup.js", "tiny_mce_src.js", "tiny_mce.js" });

	public IRequestHandler mapRequest(Request request)
	{
		String url = request.getUrl().toString();
		List<String> segments = request.getUrl().getSegments();
		String path = "tiny_mce/";
		for (String prefix : KNOWN_SEGMENTS)
		{
			if (segments.contains(prefix))
			{
				path += url.substring(url.indexOf(prefix));
				if (TinyMceRequestMapper.class.getResource(path) != null)
				{
					PackageResourceReference resourceReference = new PackageResourceReference(
							TinyMceRequestMapper.class, path);
					return new ResourceReferenceRequestHandler(resourceReference, null);
				}
			}
		}
		return null;
	}

	public int getCompatibilityScore(Request request)
	{
		return Integer.MAX_VALUE;
	}

	public Url mapHandler(IRequestHandler requestHandler)
	{
		return null;
	}

}
