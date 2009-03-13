package org.wicketstuff.yui.helper;

import java.util.Collections;
import java.util.List;

public class CollectionsHelper
{

	/**
	 * rotates the item formPos into the toPos in list nudging (rotating) the
	 * rest of the elements
	 * 
	 * @param list
	 * @param fromPos
	 * @param toPos
	 */
	public static void rotateInto(List<?> list, int fromPos, int toPos)
	{
		boolean moveRight = toPos > fromPos;

		int fromIndex = moveRight ? fromPos : toPos;
		int toIndex = (moveRight ? toPos : fromPos) + 1;
		int distance = moveRight ? -1 : 1; // negative == backwards

		Collections.rotate(list.subList(fromIndex, toIndex), distance);

	}
}
