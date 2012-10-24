package org.wicketstuff.pageserializer.kryo2.inspecting.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * writes basic infos to log(Level=DEBUG)
 * 
 * @author mosmann
 * 
 */
public class LoggingSerializationListener implements ISerializationListener
{

	private final static Logger LOG = LoggerFactory.getLogger(LoggingSerializationListener.class);

	@Override
	public void begin(Object object)
	{
		LOG.debug("Start for object: '{}'", object.getClass());
	}

	@Override
	public void before(int position, Object object)
	{
		LOG.debug("Start at '{}' byte for object:  '{}'", position,
			object != null ? object.getClass() : "NULL");
	}

	@Override
	public void after(int position, Object object)
	{
		LOG.debug("End at   '{}' bytes for object: '{}'", position,
			object != null ? object.getClass() : "NULL");
	}

	@Override
	public void end(Object object, RuntimeException exception)
	{
		LOG.debug("End for object:   '{}'", object.getClass());
	}

}
