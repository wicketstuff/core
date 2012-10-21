package org.wicketstuff.pageserializer.kryo2.inspecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingSerializationListener implements ISerializationListener
{

	private final static Logger LOG = LoggerFactory.getLogger(LoggingSerializationListener.class);

	@Override
	public void begin(Object object)
	{
		LOG.error("Start for object: '{}'", object.getClass());
	}

	@Override
	public void before(int position, Object object)
	{
		LOG.error("Start at '{}' byte for object:  '{}'", position,
			object != null ? object.getClass() : "NULL");
	}

	@Override
	public void after(int position, Object object)
	{
		LOG.error("End at   '{}' bytes for object: '{}'", position,
			object != null ? object.getClass() : "NULL");
	}

	@Override
	public void end(Object object)
	{
		LOG.error("End for object:   '{}'", object.getClass());
	}

}
