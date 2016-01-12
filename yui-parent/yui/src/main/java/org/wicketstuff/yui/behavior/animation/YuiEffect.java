package org.wicketstuff.yui.behavior.animation;

import org.wicketstuff.yui.helper.Attributes;

/**
 * a Dave Galss Effect - Do i want to restrict the effects here?
 *  
 * @author josh
 *
 */
public class YuiEffect extends YuiAnimEffect
{
	private static final long serialVersionUID = 1L;
	
	public YuiEffect(Type type, Attributes attributes)
	{
		super(type, attributes);
	}

	public YuiEffect(Type type)
	{
		super(type);
	}

	public String onCompleteJS()
	{
		return "onEffectComplete";
	}
	
	@Override
	public String getOpts()
	{
		return super.getOpts();
	}

}
