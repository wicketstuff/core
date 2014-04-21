package org.wicketstuff.rest.resource;

import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.ResourcePath;
import org.wicketstuff.rest.contenthandling.serialdeserial.TestJsonDesSer;

@ResourcePath("/mountedpath")
public class MountedResource extends AbstractRestResource<TestJsonDesSer>
{

	public MountedResource()
	{
		super(new TestJsonDesSer());
	}

	public MountedResource(TestJsonDesSer serialDeserial)
	{
		super(serialDeserial);
	}

	@MethodMapping("/")
	public String dummyMethod()
	{
		return "I'm dummy!";
	}
}
