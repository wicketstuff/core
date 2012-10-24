package org.wicketstuff.pageserializer.kryo2.inspecting.listener;


/**
 * serialization listener utility class
 * 
 * @author mosmann
 * 
 */
public final class SerializationListeners
{
	private SerializationListeners()
	{
		// no instance
	}

	/**
	 * a list of serialization listener as serialization listener
	 * @param listener list of listeners
	 * @return wrapper around the list
	 */
	public static ISerializationListener listOf(final ISerializationListener... listener)
	{
		return new ISerializationListener()
		{

			@Override
			public void begin(Object object)
			{
				for (ISerializationListener l : listener)
				{
					l.begin(object);
				}
			}

			@Override
			public void before(int position, Object object)
			{
				for (ISerializationListener l : listener)
				{
					l.before(position, object);
				}
			}

			@Override
			public void after(int position, Object object)
			{
				for (ISerializationListener l : listener)
				{
					l.before(position, object);
				}
			}

			@Override
			public void end(Object object, RuntimeException exceptionIfAny)
			{
				for (ISerializationListener l : listener)
				{
					l.end(object, exceptionIfAny);
				}

			}

		};
	}
}
