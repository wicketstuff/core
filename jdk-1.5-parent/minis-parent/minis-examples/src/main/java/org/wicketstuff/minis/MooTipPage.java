package org.wicketstuff.minis;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.minis.behavior.mootip.MootipBehaviour;
import org.wicketstuff.minis.behavior.mootip.MootipPanel;
import org.wicketstuff.minis.behavior.mootip.MootipSettings;

/**
 * Homepage
 */
public class MooTipPage extends HomePage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public MooTipPage(final PageParameters parameters)
	{
		super(parameters);
		// Add the simplest type of label
		add(new Label("tooltip01", "this is tool tip 01").add(new MootipBehaviour(
			"This is my tool tip",
			"I can be very long and even have formatting like <br /> and be <strong>strong</strong>")));

		// add mootippanelajaxplaceholder:

		add(new MootipPanel());


		MootipSettings mooSettings = new MootipSettings();
		mooSettings.setEvalAlways(true);
		MootipBehaviour behaviour = new MootipBehaviour(new MooPanel());
		behaviour.setMootipSettings(mooSettings);

		add(new Label("tooltip02", "this is tool tip 02").add(behaviour));

	}
}
