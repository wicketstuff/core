package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTreeTracker {

	int lastPosition = 0;
	private final IObjectLabelizer labelizer;
	private Item currentItem;

	public ObjectTreeTracker(IObjectLabelizer labelizer, Object root) {
		this.labelizer = labelizer;
		this.currentItem = new Item(new ItemKey(root.getClass(), labelizer.labelFor(root)));
	}

	public void newItem(int position, Object object) {
		int diff = updatePositionAndCalculateDiff(position);
		currentItem = currentItem.newItem(diff, new ItemKey(object.getClass(),
				labelizer.labelFor(object)));
	}

	public void closeItem(int position, Object object) {
		int diff = updatePositionAndCalculateDiff(position);
		currentItem=currentItem.closeItem(diff, new ItemKey(object.getClass(),
				labelizer.labelFor(object)));
	}

	private int updatePositionAndCalculateDiff(int position) {
		int diff = position - lastPosition;
		lastPosition = position;
		return diff;
	}
	
	public ISerializedObjectTree end(Object object) {
		return new ImmutableTree(currentItem);
	}
	

	static class ImmutableTree implements ISerializedObjectTree {
		
		final Class<?> type;
		final String label;
		final int size;
		final int childSize;
		
		final List<ImmutableTree> children;
		
		public ImmutableTree(Item item) {
			this.type=item.type();
			this.label=item.label();
			this.size=item.size();
			List<ImmutableTree> lchildren=new ArrayList<ObjectTreeTracker.ImmutableTree>();
			int childSize=0;
			for (Item child : item.children()) {
				ImmutableTree toAdd = new ImmutableTree(child);
				childSize=childSize+toAdd.childSize();
				lchildren.add(toAdd);
			}
			this.children=Collections.unmodifiableList(lchildren);
			this.childSize=childSize;
		}

		@Override
		public int size() {
			return size;
		}
		
		@Override
		public int childSize() {
			return childSize;
		}

		@Override
		public Class<? extends Object> type() {
			return type;
		}

		@Override
		public String label() {
			return label;
		}

		@Override
		public List<? extends ISerializedObjectTree> children() {
			return children;
		}
	}
	
	static class Item {

		private final ItemKey key;
		Map<ItemKey, Item> children=new HashMap<ItemKey, ObjectTreeTracker.Item>();
		
		int size=0;
		
		Item parent = null;

		public Item(ItemKey key) {
			this.key = key;
		}

		public Item newItem(int diff, ItemKey key) {
			size=size+diff;
			Item item = getOrCreateItem(key);
			return item;
		}

		private Item getOrCreateItem(ItemKey key) {
			Item item=children.get(key);
			if (item==null) {
				item = new Item(this, key);
				children.put(key, item);
			}
			return item;
		}

		public Item closeItem(int diff, ItemKey key) {
			if (!this.key.equals(key)) throw new IllegalArgumentException("key does not match "+this.key+"!="+key);
			size=size+diff;
			return parent;
		}

		private Item(Item parent, ItemKey key) {
			this(key);
			this.parent = parent;
		}
		
		public int size() {
			return size;
		}
		
		public Class<? extends Object> type() {
			return key.type();
		}
		
		public String label() {
			return key.label();
		}
		
		public Collection<Item> children() {
			return children.values();
		}
	}
	
	static class ItemKey {
		final Class<?> type;
		final String label;
		
		public ItemKey(Class<?> type,String label) {
			this.type = type;
			this.label = label;
		}
		
		public Class<?> type() {
			return type;
		}
		
		public String label() {
			return label;
		}
		
		@Override
		public String toString() {
			return "Key("+type+","+label+")";
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ItemKey other = (ItemKey) obj;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

	}
}
