package org.wicketstuff.flot.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.flot.Color;
import org.wicketstuff.flot.DataSet;
import org.wicketstuff.flot.FlotPanel;
import org.wicketstuff.flot.LineGraphType;
import org.wicketstuff.flot.Series;

/**
 * Homepage
 */
public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{
		FlotPanel panel = new FlotPanel("flotPanel", new PropertyModel<List<Series>>(this, "data"));
		panel.setAxisMinX(-5.0);
		panel.setAxisMaxX(5.0);
		panel.setAxisMinY(-1.0);
		panel.setAxisMaxY(1.0);
		add(panel);
	}

	public List<Series> getData()
	{
		List<Series> result = new ArrayList<Series>();

		List<DataSet> sine = new ArrayList<DataSet>();
		for (double x = -5.0; x < 5.0; x += 0.1)
			sine.add(new DataSet(x, Math.sin(x)));

		result.add(new Series(sine, "sin(x)", new Color(0x00, 0x89, 0xBB), new LineGraphType(null,
			false, null)));

		return result;
	}
}
