package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;

public class TreeTransformator implements ISerializedObjectTreeProcessor
{
	private final ISerializedObjectTreeProcessor destination;
	private final IFilter[] filter;

	public TreeTransformator(ISerializedObjectTreeProcessor destination, IFilter... filter)
	{
		this.destination = destination;
		this.filter = filter;
	}

	@Override
	public void process(ISerializedObjectTree tree)
	{
		ISerializedObjectTree current = tree;
		for (IFilter f : filter)
		{
			switch (f.filterType())
			{
				case COMPACT :
					current = TreeTransformations.compact(current, f);
					break;
				case STRIP :
					current = TreeTransformations.strip(current, f);
					break;
			}
		}
		destination.process(current);
	}

	public enum FilterType {
		COMPACT, STRIP;
	}

	public interface IFilter extends ITreeFilter
	{
		FilterType filterType();
	}
	
	static class Filter implements IFilter {

		private final ITreeFilter filter;
		private final FilterType type;
		
		public Filter(FilterType type, ITreeFilter filter)
		{
			this.type = type;
			this.filter = filter;
		}
		
		@Override
		public boolean accept(ISerializedObjectTree source, Level current)
		{
			return filter.accept(source, current);
		}

		@Override
		public FilterType filterType()
		{
			return type;
		}
		
	}

	public static IFilter strip(ITreeFilter f)
	{
		return new Filter(FilterType.STRIP,f);
	}
	
	public static IFilter compact(ITreeFilter f)
	{
		return new Filter(FilterType.COMPACT,f);
	}
}
