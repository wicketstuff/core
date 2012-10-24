package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

/**
 * tree processor utility class
 * @author mosmann
 *
 */
public final class TreeProcessors
{
	private TreeProcessors()
	{
		// no instance
	}

	/**
	 * a list of tree processors listener as tree processor 
	 * @param processors list of processors
	 * @return wrap around the list
	 */
	public static ISerializedObjectTreeProcessor listOf(
		final ISerializedObjectTreeProcessor... processors)
	{
		return new ISerializedObjectTreeProcessor()
		{
			@Override
			public void process(ISerializedObjectTree tree)
			{
				for (ISerializedObjectTreeProcessor p : processors)
				{
					p.process(tree);
				}
			}
		};
	}
}
