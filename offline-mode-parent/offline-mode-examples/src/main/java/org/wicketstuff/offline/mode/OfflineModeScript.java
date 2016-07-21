package org.wicketstuff.offline.mode;

import java.util.UUID;

import org.apache.wicket.request.resource.JavaScriptPackageResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class OfflineModeScript extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final OfflineModeScript self = new OfflineModeScript();

	private static String id = UUID.randomUUID().toString();

	private OfflineModeScript()
	{
		super(OfflineModeScript.class, OfflineModeScript.class.getSimpleName() + ".js");
	}

	public static OfflineModeScript getInstance()
	{
		return self;
	}

	@Override
	public JavaScriptPackageResource getResource()
	{
		final JavaScriptPackageResource resource = new JavaScriptPackageResource(getScope(),
			getName(), getLocale(), getStyle(), getVariation())
		{
			private static final long serialVersionUID = 1L;

			protected byte[] processResponse(Attributes attributes, byte[] bytes)
			{
				byte[] processResponse = super.processResponse(attributes, bytes);
				String response = new String(processResponse);
				response = response.replace("$(scriptId)", "\"" + id + "\"");
				return response.getBytes();
			}
		};
		removeCompressFlagIfUnnecessary(resource);
		return resource;
	}

}
