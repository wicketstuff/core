package org.wicketstuff.flot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Jason Novotny
 * @version $Id$
 */
public class TickCollection implements Serializable
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	private List<TickDataSet> tickDataSet = new ArrayList<TickDataSet>();

	public TickCollection()
	{
	}

	public TickCollection(TickDataSet[] tickDataSet)
	{
		this.tickDataSet = Arrays.asList(tickDataSet);
	}

	public void add(TickDataSet tick)
	{
		tickDataSet.add(tick);
	}

	public List<TickDataSet> getTickDataSet()
	{
		return Collections.unmodifiableList(tickDataSet);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (TickDataSet t : tickDataSet)
		{
			sb.append(t.toString());
			sb.append(",");
		}
		// Remove last ", "
		if (tickDataSet.size() > 0)
			sb.setLength(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

}
