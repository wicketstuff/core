package org.wicketstuff.html5.shape;

import org.wicketstuff.html5.BasePage;
import org.wicketstuff.html5.shape.ShapeBuilder.Orientation;

public class ShapeCircleExamplePage extends BasePage
{

	private static final long serialVersionUID = 1L;

	public ShapeCircleExamplePage()
	{
		add(new ShapeBuilder("shapeleft").shape(new Circle("30%"))
			.transition(new Circle("50%"))
			.useWidth("500px")
			.useHeight("500px"));
		add(new ShapeBuilder("shaperight").shape(new Circle("30%"))
			.transition(new Circle("50%"))
			.useWidth("500px")
			.useHeight("500px")
			.orientation(Orientation.right));
	}

}
