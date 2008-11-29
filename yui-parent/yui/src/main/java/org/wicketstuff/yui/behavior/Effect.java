package org.wicketstuff.yui.behavior;

/**
 * a Dave Galss Effect - Do i want to restrict the effects here?
 *  
 * @author josh
 *
 */
public class Effect extends AnimEffect
{
	private static final long serialVersionUID = 1L;
	
	public Effect(Type type, Attributes attributes)
	{
		super(type, attributes);
	}

	public Effect(Type type)
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
