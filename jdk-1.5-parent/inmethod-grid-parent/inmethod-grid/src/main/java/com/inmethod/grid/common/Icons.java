package com.inmethod.grid.common;

import com.inmethod.icon.Icon;
import com.inmethod.icon.PackageIcon;

/**
 * Contains default tree icons.
 * 
 * @author Matej Knopp
 */
public class Icons
{
	/**
	 * Default icon for a closed folder.
	 */
	public static final Icon FOLDER_CLOSED = new PackageIcon(Icons.class,
		                                                       "res/icons/folder-closed.gif");

	/**
	 * Default icon for an opened folder.
	 */
	public static final Icon FOLDER_OPEN = new PackageIcon(Icons.class, "res/icons/folder-open.gif");

	/**
	 * Default icon for a non-folder node.
	 */
	public static final Icon ITEM = new PackageIcon(Icons.class, "res/icons/item.gif");

	public static final Icon OK = new PackageIcon(Icons.class, "res/icons/16-em-check.png");

	public static final Icon CANCEL = new PackageIcon(Icons.class, "res/icons/16-em-cross.png");

  public static final Icon DELETE = new PackageIcon(Icons.class, "res/icons/trashcan-delete.png");
	
	private Icons() 
  {		
	}
}
