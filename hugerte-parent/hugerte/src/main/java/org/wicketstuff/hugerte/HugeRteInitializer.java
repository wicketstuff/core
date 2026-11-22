package org.wicketstuff.hugerte;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

public class HugeRteInitializer implements IInitializer
{

	@Override
	public void init(Application application)
	{
		application.getRootRequestMapperAsCompound().add(new HugeRteRequestMapper());
	}

	@Override
	public void destroy(Application application)
	{
	}
}
