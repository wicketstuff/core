/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.minis.behavior.apanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.parser.XmlTag;

/**
 * GridLayout puts markup of components in &lt;table&gt;&lt;table/&gt; according to
 * {@link org.wicketstuff.minis.behavior.apanel.GridLayoutConstraint}s. Component without a
 * constraint will be added to the first empty cell. If there is no empty cells for the component
 * WicketRuntimeException will be thrown.
 */
public class GridLayout implements ILayout
{
	private static class GridConstraintIterator implements
		Iterator<Map.Entry<GridLayoutConstraint, Component>>
	{
		private final Iterator<Map.Entry<GridLayoutConstraint, Component>> iterator;
		private int currentRow = 0;
		private boolean isNewRow;
		private int currentIndex = 0;

		public GridConstraintIterator(final Set<Map.Entry<GridLayoutConstraint, Component>> set)
		{
			iterator = set.iterator();
		}

		public boolean hasNext()
		{
			return iterator.hasNext();
		}

		public boolean isAtFirstConstraint()
		{
			return currentIndex == 1;
		}

		public boolean isNewRow()
		{
			return isNewRow;
		}

		public Map.Entry<GridLayoutConstraint, Component> next()
		{
			final Map.Entry<GridLayoutConstraint, Component> entry = iterator.next();

			currentIndex++;

			if (currentRow != entry.getKey().getRow())
			{
				currentRow = entry.getKey().getRow();
				isNewRow = true;
			}
			else
				isNewRow = false;

			return entry;
		}

		public void remove()
		{
			iterator.remove();
		}
	}

	private static final long serialVersionUID = 1L;

	private static final Component EMPTY_CELL_COMPONENT = null;

	private static XmlTag createXmlTag(final String name, final XmlTag.TagType type)
	{
		final XmlTag xmlTag = new XmlTag();
		xmlTag.setType(type);
		xmlTag.setName(name);
		return xmlTag;
	}

	private final RenderersList renderersList;
	private final int width;
	private final int height;

	/**
	 * Holds constraints for components. It is sorted to write markup output in the right order.
	 * 
	 * @see org.wicketstuff.minis.behavior.apanel.GridLayoutConstraint#compareTo(GridLayoutConstraint)
	 */
	private final SortedMap<GridLayoutConstraint, Component> constraintsMap = new TreeMap<GridLayoutConstraint, Component>();

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            width of grid
	 * @param height
	 *            height of grid
	 */
	public GridLayout(final int width, final int height)
	{
		this(width, height, RenderersList.getDefaultRenderers());
	}

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            width of grid
	 * @param height
	 *            height of grid
	 * @param renderers
	 *            list of renderers to customize component rendering in this layout
	 */
	public GridLayout(final int width, final int height, final List<IComponentRenderer<?>> renderers)
	{
		this.height = height;
		this.width = width;
		renderersList = new RenderersList(renderers);
	}

	private void checkConstraints(final List<? extends Component> components)
	{
		for (int i = 0; i < components.size(); i++)
		{
			final GridLayoutConstraint constraint = getGridConstraint(components.get(i));

			if (constraint == null)
				continue;

			// skip current component and all components before because
			// their constraints have already been checked
			for (int j = i + 1; j < components.size(); j++)
			{
				final GridLayoutConstraint anotherConstraint = getGridConstraint(components.get(j));

				if (anotherConstraint == null)
					continue;

				if (constraint.intersectsWith(anotherConstraint))
					throw new WicketRuntimeException("Component " + components.get(i) +
						" and component " + components.get(j) + " has intersecting constraints");
			}
		}
	}

	private void fillConstraintsMap(final List<? extends Component> components)
	{
		final List<Component> componentsWithNoConstraint = new ArrayList<Component>();

		for (final Component component : components)
		{
			final GridLayoutConstraint constraint = getGridConstraint(component);

			if (constraint != null)
				constraintsMap.put(constraint, component);
			else
				componentsWithNoConstraint.add(component);
		}

		for (final Component component : componentsWithNoConstraint)
		{
			final GridLayoutConstraint constraint = findEmptyCellConstraint();
			if (constraint == null)
				throw new WicketRuntimeException(
					"There is no free cells in grid for the component " + component);
			constraintsMap.put(constraint, component);
		}

		assert constraintsMap.size() == components.size();

		// filling cells that has no components so that they can be rendered
		for (GridLayoutConstraint constraint = findEmptyCellConstraint(); constraint != null; constraint = findEmptyCellConstraint())
			constraintsMap.put(constraint, EMPTY_CELL_COMPONENT);
	}

	private GridLayoutConstraint findEmptyCellConstraint()
	{
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++)
				if (!isWithinAnyConstraint(col, row))
					return new GridLayoutConstraint(col, row);
		return null;
	}

	private GridLayoutConstraint getGridConstraint(final Component component)
	{
		for (final Behavior behavior : component.getBehaviors())
			if (behavior instanceof GridLayoutConstraint)
				return (GridLayoutConstraint)behavior;
		return null;
	}

	private boolean isWithinAnyConstraint(final int col, final int row)
	{
		for (final GridLayoutConstraint constraint : constraintsMap.keySet())
			if (constraint.contains(col, row))
				return true;
		return false;
	}

	/**
	 * May be overriden to modify &lt;td&gt; tag
	 * 
	 * @param component
	 *            component
	 * @param xmlTag
	 *            &lt;td&gt; tag
	 */
	protected void onGridCell(final Component component, final XmlTag xmlTag)
	{
	}

	/**
	 * May be overriden to modify &lt;tr&gt; tag
	 * 
	 * @param xmlTag
	 *            &lt;tr&gt; tag
	 */
	protected void onGridRow(final XmlTag xmlTag)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	public CharSequence renderComponents(final List<? extends Component> components)
	{
		constraintsMap.clear();

		checkConstraints(components);
		fillConstraintsMap(components);

		final StringBuilder stringBuilder = new StringBuilder();
		writeOutput(stringBuilder);
		return stringBuilder;
	}

	private void writeOutput(final StringBuilder stringBuilder)
	{
		stringBuilder.append("<table>");

		final GridConstraintIterator iterator = new GridConstraintIterator(
			constraintsMap.entrySet());
		while (iterator.hasNext())
		{
			final Map.Entry<GridLayoutConstraint, Component> entry = iterator.next();
			final GridLayoutConstraint constraint = entry.getKey();
			final Component component = entry.getValue();

			CharSequence markup = "";
			if (component != EMPTY_CELL_COMPONENT)
			{
				final IComponentRenderer<Component> componentRenderer = renderersList.findRendererForClass(component.getClass());
				markup = componentRenderer.getMarkup(component);
			}

			if (iterator.isNewRow())
				stringBuilder.append("</tr>");
			if (iterator.isNewRow() || iterator.isAtFirstConstraint())
			{
				final XmlTag xmlTag = createXmlTag("tr", XmlTag.TagType.OPEN);
				onGridRow(xmlTag);
				stringBuilder.append(xmlTag.toCharSequence());
			}

			final XmlTag xmlTag = createXmlTag("td", XmlTag.TagType.OPEN);
			onGridCell(component, xmlTag);
			if (constraint.getColSpan() > 1)
				xmlTag.put("colspan", constraint.getColSpan());
			if (constraint.getRowSpan() > 1)
				xmlTag.put("rowspan", constraint.getRowSpan());
			stringBuilder.append(xmlTag.toCharSequence());

			stringBuilder.append(markup);

			stringBuilder.append("</td>");
		}

		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
	}
}
