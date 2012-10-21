package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ImmutableTree;

public class Trees
{
	private Trees()
	{
		// no instance
	}

	public static boolean equals(ISerializedObjectTree a, ISerializedObjectTree b)
	{
		if (a == b)
			return true;
		if ((a == null) || (b == null))
			return false;
		if (!a.type().equals(b.type()))
			return false;
		if (!equals(a.label(), b.label()))
			return false;
		if (a.size() != b.size())
			return false;
		return equals(a.children(), b.children());
	}

	public static boolean equals(String a, String b)
	{
		if (a == b)
			return true;
		if ((a == null) || (b == null))
			return false;
		return a.equals(b);
	}

	public static boolean equals(List<? extends ISerializedObjectTree> a,
		List<? extends ISerializedObjectTree> b)
	{
		if (a == b)
			return true;
		if ((a == null) || (b == null))
			return false;
		if (a.size() != b.size())
			return false;
		for (int i = 0; i < a.size(); i++)
		{
			if (!equals(a.get(i), b.get(i)))
				return false;
		}
		return true;
	}

	public static Builder build(Class<?> type, int size)
	{
		return build(type, null, size);
	}

	public static Builder build(Class<?> type, String label, int size)
	{
		return new Builder(type, label, size);
	}

	public static class Builder
	{

		private final Class<?> type;
		private final String label;
		private final int size;
		private final Builder parent;
		private final List<Builder> children = new ArrayList<Trees.Builder>();

		private Builder(Class<?> type, String label, int size)
		{
			this(null, type, label, size);
		}

		private Builder(Builder parent, Class<?> type, String label, int size)
		{
			this.type = type;
			this.label = label;
			this.size = size;
			this.parent = parent;
		}

		public Builder withChild(Class<?> type, int size)
		{
			return withChild(type, null, size);
		}

		public Builder withChild(Class<?> type, String label, int size)
		{
			Builder child = new Builder(this, type, label, size);
			children.add(child);
			return child;
		}

		private Builder withCopy(Builder s)
		{
			Builder child = new Builder(this, s.type, s.label, s.size);
			for (Builder c : s.children)
			{
				child.withCopy(c);
			}
			children.add(child);
			return child;
		}

		public Builder parent()
		{
			return parent;
		}

		public Builder root()
		{
			if (parent != null)
				return parent.root();
			return this;
		}

		public ISerializedObjectTree asTree()
		{
			return root().buildTree();
		}

		private ISerializedObjectTree buildTree()
		{
			List<ISerializedObjectTree> items = new ArrayList<ISerializedObjectTree>();
			for (Builder b : children)
			{
				items.add(b.buildTree());
			}
			return new ImmutableTree(type, label, size, items);
		}

		public Builder multiply(int count)
		{
			for (int i = 0; i < (count - 1); i++)
			{
				parent.withCopy(this);
			}
			return this;
		}

	}
}
