package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.io.Serializable;

/**
 * 
 * @author mosmann
 *
 */
public class Level implements Serializable
{
	final Level parent;
	
	/**
	 * Level Start
	 */
	public Level()
	{
		this(null);
	}
	
	private Level(Level parent) {
		this.parent=parent;
	}
	
	public Level down() {
		return new Level(this);
	}
	
	public Level up() {
		if (parent==null) throw new IllegalArgumentException("could not go up, parent is NULL");
		return parent;
	}
	
	public int distanceFromTop() {
		return parent!=null ? 1 + parent.distanceFromTop() : 0;
	}
}
