package org.wicketstuff.jquery.resize;

import org.wicketstuff.jquery.Options;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class ResizeOptions extends Options
{
	private static final long serialVersionUID = 1L;
	public static final String ASPECTRATIO_PRESERVE = "preserve";

	public ResizeOptions aspectRatio(String r)
	{
		set("aspectRatio", r);
		return this;
	}
}
