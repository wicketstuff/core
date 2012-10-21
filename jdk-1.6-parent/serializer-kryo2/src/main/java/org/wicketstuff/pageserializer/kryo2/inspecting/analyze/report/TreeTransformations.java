package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ImmutableTree;

public class TreeTransformations
{
	static final Level TOP=new Level();

	private TreeTransformations()
	{
		// no instance
	}

	public static ISerializedObjectTree compact(ISerializedObjectTree source, ITreeFilter filter)
	{
		return compact(source, filter, TOP);
	}

	private static ISerializedObjectTree compact(ISerializedObjectTree source, ITreeFilter filter, Level level)
	{
		if (filter.accept(source, level))
		{
			if (!source.children().isEmpty())
			{
				boolean changed = false;

				List<ISerializedObjectTree> filteredList = new ArrayList<ISerializedObjectTree>();
				for (ISerializedObjectTree child : source.children())
				{
					ISerializedObjectTree filtered = compact(child, filter, level.down());
					filteredList.add(filtered);
					if (filtered != child)
					{
						changed = true;
					}
				}

				if (changed)
				{
					return new ImmutableTree(source.type(), source.label(), source.size(),
						filteredList);
				}
			}
			return source;
		}
		else
		{
			return new ImmutableTree(source.type(), source.label(), source.size() +
				source.childSize(), new ArrayList<ISerializedObjectTree>());
		}
	}

	public static ISerializedObjectTree strip(ISerializedObjectTree source, ITreeFilter filter)
	{
		Level level = TOP;

		if (!filter.accept(source, level))
			throw new IllegalArgumentException("can not strip top level element");

		return strip(source, filter, level);
	}

	private static ISerializedObjectTree strip(ISerializedObjectTree source, ITreeFilter filter,
		Level level)
	{

		boolean changed = false;
		int localSize = 0;

		List<ISerializedObjectTree> filteredList = new ArrayList<ISerializedObjectTree>();
		
		Level levelOneDown = level.down();
		
		for (ISerializedObjectTree child : source.children())
		{
			if (filter.accept(child, levelOneDown))
			{
				ISerializedObjectTree filtered = strip(child, filter, levelOneDown);
				filteredList.add(filtered);
				if (filtered != child)
				{
					changed = true;
				}
			}
			else
			{
				changed = true;
				localSize = localSize + child.size();
				for (ISerializedObjectTree childOfChild : child.children())
				{
					ISerializedObjectTree filtered = strip(childOfChild, filter, levelOneDown);
					filteredList.add(filtered);
				}
			}
		}

		if (changed)
		{
			return new ImmutableTree(source.type(), source.label(), source.size() + localSize,
				filteredList);
		}
		return source;
	}

}
