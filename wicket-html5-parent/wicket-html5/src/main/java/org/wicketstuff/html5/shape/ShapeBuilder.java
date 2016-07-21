/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.html5.shape;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * Creates a shape. The initial with / height is set to 100%, margin is set to 0
 * 
 * @author Tobias Soloschenko
 * 
 */
public class ShapeBuilder extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private String width = "100%";

	private String height = "100%";

	private String margin = "0";

	private Shape shape = null;

	private Shape transition = null;

	private Orientation orientation = Orientation.left;

	/**
	 * Creates a shape at the position of the given id
	 * 
	 * @param id
	 *            the it to position the shape
	 */
	public ShapeBuilder(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
	}

	/**
	 * Creates a shape at the position of the given id
	 * 
	 * @param id
	 *            the it to position the shape
	 * @param model
	 *            the model this shape contains
	 */
	public ShapeBuilder(String id, IModel<?> model)
	{
		super(id, model);
		this.setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("class", this.getMarkupId());
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		String shapeDefinition = "shape-outside: " + this.shape.getName() + "(" +
			this.shape.getValues() + ");";
		String css = String.format("." + this.getMarkupId() + "{ float: %s %s %s %s %s %s",
			this.getOrientation().name() + ";", this.getDimension(), "-webkit-" + shapeDefinition,
			shapeDefinition, "-webkit-shape-margin:" + this.getMargin() + ";", "shape-margin:" +
				this.getMargin() + ";");
		String transitionCss = "";
		if (this.transition != null)
		{
			css += " transition: " +
				(this.transition.getTransitionTime() != null ? this.transition.getTransitionTime() +
					";" : "1s;");
			String shapeTransitionDefinition = "shape-outside: " + this.transition.getName() + "(" +
				this.transition.getValues() + ");";
			transitionCss = String.format("." + this.getMarkupId() + ":hover{ %s %s }", "-webkit-" +
				shapeTransitionDefinition, shapeTransitionDefinition);
		}
		css += "}";
		response.render(CssHeaderItem.forCSS(css + " " + transitionCss, this.getMarkupId() + "css"));

	}

	/**
	 * Gets the dimension of the shape as string - height and width
	 * 
	 * @return the dimension settings as string
	 */
	protected String getDimension()
	{
		return "min-height: " + this.getHeight() + "; height:" + this.getHeight() + "; width: " +
			this.getWidth() + ";";
	}

	/**
	 * Gets the with of the shape
	 * 
	 * @return the with of the shape
	 */
	public String getWidth()
	{
		return this.width;
	}

	/**
	 * Uses the given width for the shape
	 * 
	 * @param width
	 *            the width to use
	 * @return the shape
	 */
	public ShapeBuilder useWidth(String width)
	{
		this.width = width;
		return this;
	}

	/**
	 * Gets the height of the shape
	 * 
	 * @return the height of the shape
	 */
	public String getHeight()
	{
		return this.height;
	}

	/**
	 * Uses the given height for the shape
	 * 
	 * @param height
	 *            the height to use
	 * @return the shape
	 */
	public ShapeBuilder useHeight(String height)
	{
		this.height = height;
		return this;
	}

	/**
	 * Gets the maring of the shape
	 * 
	 * @return the margin of the shape
	 */
	public String getMargin()
	{
		return this.margin;
	}

	/**
	 * Uses the given margin for the shape
	 * 
	 * @param margin
	 *            the margin to use
	 * @return the shape
	 */
	public ShapeBuilder withMargin(String margin)
	{
		this.margin = margin;
		return this;
	}

	/**
	 * Gets the shape type the shape is using
	 * 
	 * @return the shape type
	 */
	public Shape getShape()
	{
		return this.shape;
	}

	/**
	 * Uses the given shape
	 * 
	 * @param shape
	 *            the shape
	 * @return the shape
	 */
	public ShapeBuilder shape(Shape shape)
	{
		this.shape = shape;
		return this;
	}

	/**
	 * Gets the transition shape
	 * 
	 * @return the transition shape
	 */
	public Shape getTransition()
	{
		return this.transition;
	}

	/**
	 * Uses the given transition shape
	 * 
	 * @param transition
	 *            the transition shape
	 * @return the shape
	 */
	public ShapeBuilder transition(Shape transition)
	{
		this.transition = transition;
		return this;
	}

	/**
	 * The orientation the shape is positioned
	 * 
	 * @return the orientation
	 */
	public Orientation getOrientation()
	{
		return this.orientation;
	}

	/**
	 * Uses the given orientation
	 * 
	 * @param orientation
	 *            the orientation to use
	 * @return the shape
	 */
	public ShapeBuilder orientation(Orientation orientation)
	{
		this.orientation = orientation;
		return this;
	}

	public enum Orientation
	{
		left, right;
	}
}
