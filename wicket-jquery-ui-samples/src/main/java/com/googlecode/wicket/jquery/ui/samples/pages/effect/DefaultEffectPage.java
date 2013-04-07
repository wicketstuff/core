package com.googlecode.wicket.jquery.ui.samples.pages.effect;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.effect.JQueryEffectBehavior;

public class DefaultEffectPage extends AbstractEffectPage
{
	private static final long serialVersionUID = 1L;

	public DefaultEffectPage()
	{
		this.add(new JQueryBehavior("#tabs", "tabs"));
		this.add(new JQueryEffectBehavior("#tabs", "bounce", 1000));
	}
}
