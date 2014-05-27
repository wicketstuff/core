package org.wicketstuff.rest.resource;

import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.ResourcePath;
import org.wicketstuff.rest.contenthandling.webserialdeserial.JsonTestWebSerialDeserial;

@ResourcePath("/mountedpath")
public class MountedResource extends AbstractRestResource<JsonTestWebSerialDeserial>
{

	public MountedResource()
	{
		super(new JsonTestWebSerialDeserial());
	}

	public MountedResource(JsonTestWebSerialDeserial serialDeserial)
	{
		super(serialDeserial);
	}

	@MethodMapping("/")
	public String dummyMethod()
	{
		return "I'm dummy!";
	}
}
