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

/**
 * Specifies position of a component for {@link org.wicketstuff.minis.behavior.apanel.GridLayout}
 * and optionally column/row span.
 */
public class GridLayoutConstraint extends ConstraintBehavior implements
	Comparable<GridLayoutConstraint>
{
	private static final long serialVersionUID = 1L;

	private final int col;
	private final int row;
	private int colSpan = 1;
	private int rowSpan = 1;

	public GridLayoutConstraint(final int col, final int row)
	{
		this.col = col;
		this.row = row;
	}

	public int compareTo(final GridLayoutConstraint constraint)
	{
		if (getRow() > constraint.getRow())
			return 1;
		if (getRow() < constraint.getRow())
			return -1;
		if (getCol() > constraint.getCol())
			return 1;
		if (getCol() < constraint.getCol())
			return -1;
		return 0;
	}

	boolean contains(final int col, final int row)
	{
		return col >= getCol() && col < getCol() + getColSpan() && row >= getRow() &&
			row < getRow() + getRowSpan();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final GridLayoutConstraint that = (GridLayoutConstraint)o;

		return col == that.col && row == that.row;
	}

	public int getCol()
	{
		return col;
	}

	public int getColSpan()
	{
		return colSpan;
	}

	public int getRow()
	{
		return row;
	}

	public int getRowSpan()
	{
		return rowSpan;
	}

	@Override
	public int hashCode()
	{
		int result;
		result = col;
		result = 31 * result + row;
		return result;
	}

	boolean intersectsWith(final GridLayoutConstraint constraint)
	{
		for (int col = constraint.getCol(); col < constraint.getCol() + constraint.getColSpan(); col++)
			for (int row = constraint.getRow(); row < constraint.getRow() + constraint.getRowSpan(); row++)
				if (contains(col, row))
					return true;
		return false;
	}

	public GridLayoutConstraint setColSpan(final int colSpan)
	{
		if (colSpan < 1)
			throw new IllegalArgumentException("colspan can't be zero or negative : " + colSpan);

		this.colSpan = colSpan;
		return this;
	}

	public GridLayoutConstraint setRowSpan(final int rowSpan)
	{
		if (rowSpan < 1)
			throw new IllegalArgumentException("rowspan can't be zero or negative : " + rowSpan);

		this.rowSpan = rowSpan;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return String.format("[%s, %s, %s, %s]", col, row, colSpan, rowSpan);
	}
}
