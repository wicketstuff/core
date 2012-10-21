package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.wicket.util.lang.Bytes;
import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.AnalyzingSerializationListener;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.IObjectLabelizer;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.TreeProcessors;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.ITreeFilter;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.Level;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.SortedTreeSizeReport;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.TreeSizeReport;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.TreeTransformator;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.TypeSizeReport;
import org.wicketstuff.pageserializer.kryo2.inspecting.validation.DefaultJavaSerializationValidator;

public class InspectingKryoTest
{

	@Test
	public void writeStringList()
	{
		List<Object> root = new ArrayList<Object>();
		String third = "III";
		List<Integer> numberList = new ArrayList<Integer>();
		numberList.add(1);
		numberList.add(12131);
		numberList.add(12);
		root.addAll(Arrays.asList("One", "2", third, third));
		root.add(numberList);
		root.add(new BiggestDummy());

		IObjectLabelizer labelizer = new IObjectLabelizer()
		{

			@Override
			public String labelFor(Object object)
			{
				return null;
			}
		};
		ISerializedObjectTreeProcessor treeProcessor = TreeProcessors.listOf(new TypeSizeReport(),
			new TreeSizeReport(), new SortedTreeSizeReport());
		ITreeFilter filter=new ITreeFilter()
		{
			@Override
			public boolean accept(ISerializedObjectTree source, Level current)
			{
				return source.type()!=Class.class;
			}
		};
		ISerializedObjectTreeProcessor cleanedTreeProcessor = new TreeTransformator(treeProcessor, TreeTransformator.strip(filter));
		ISerializationListener listener = SerializationListener.listOf(new DefaultJavaSerializationValidator(),
			new LoggingSerializationListener(), new AnalyzingSerializationListener(labelizer,
				treeProcessor), new AnalyzingSerializationListener(labelizer,
					cleanedTreeProcessor));
		InspectingKryoSerializer kryo = new InspectingKryoSerializer(Bytes.bytes(1000), listener);

		byte[] data = kryo.serialize(root);

		Assert.assertNotNull(data);

		List<Object> readBack = (List<Object>)kryo.deserialize(data);

		Assert.assertNotNull(readBack);
		Assert.assertEquals(root, readBack);
	}

	static class Dummy implements Serializable
	{
		String name = "Huauuauauaajjjajajjajj";

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dummy other = (Dummy)obj;
			if (name == null)
			{
				if (other.name != null)
					return false;
			}
			else if (!name.equals(other.name))
				return false;
			return true;
		}


	}

	static class BiggerDummy implements Serializable
	{
		Dummy first = new Dummy();
		Dummy second = new Dummy();
		String city = "lebunistan";

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result + ((second == null) ? 0 : second.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BiggerDummy other = (BiggerDummy)obj;
			if (city == null)
			{
				if (other.city != null)
					return false;
			}
			else if (!city.equals(other.city))
				return false;
			if (first == null)
			{
				if (other.first != null)
					return false;
			}
			else if (!first.equals(other.first))
				return false;
			if (second == null)
			{
				if (other.second != null)
					return false;
			}
			else if (!second.equals(other.second))
				return false;
			return true;
		}
	}

	static class BiggestDummy implements Serializable
	{
		Dummy first = new Dummy();
		BiggerDummy bigger = new BiggerDummy();

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((bigger == null) ? 0 : bigger.hashCode());
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BiggestDummy other = (BiggestDummy)obj;
			if (bigger == null)
			{
				if (other.bigger != null)
					return false;
			}
			else if (!bigger.equals(other.bigger))
				return false;
			if (first == null)
			{
				if (other.first != null)
					return false;
			}
			else if (!first.equals(other.first))
				return false;
			return true;
		}
	}
}
