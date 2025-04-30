/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.effect;

import org.wicketstuff.jquery.ui.JQueryUIBehavior;
import org.wicketstuff.jquery.ui.effect.EffectAdapter;
import org.wicketstuff.jquery.ui.effect.JQueryEffectBehavior;

public class DefaultEffectPage extends AbstractEffectPage
{
	private static final long serialVersionUID = 1L;

	public DefaultEffectPage()
	{
		this.add(new JQueryUIBehavior("#tabs", "tabs"));
		this.add(new JQueryEffectBehavior("#tabs", "bounce", 1000, new EffectAdapter()));
	}
}
