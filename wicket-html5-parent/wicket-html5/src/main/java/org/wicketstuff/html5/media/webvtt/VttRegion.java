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
package org.wicketstuff.html5.media.webvtt;

/**
 * The region to be applied to a cue.<br>
 * <br>
 * 
 * Further information:<br>
 * https://dvcs.w3.org/hg/text-tracks/raw-file/default/608toVTT/region.html
 * 
 * @author Tobias Soloschenko
 * 
 */
public class VttRegion
{

	public enum Scroll
	{
		up, none
	}

	private String id;

	private String width;

	private int lines;

	private String regionAnchor;

	private String viewportAnchor;

	private Scroll scroll;

	/**
	 * Creates a region based on the required arguments
	 * 
	 * @param id
	 *            the id of the region
	 * @param width
	 *            A number giving the width of the box within which the text of each line of the
	 *            containing cues is to be rendered, to be interpreted as a percentage of the video
	 *            width. Defaults to 100.
	 * @param lines
	 *            A number giving the number of lines of the box within which the text of each line
	 *            of the containing cues is to be rendered. Defaults to 3.
	 * @param regionAnchor
	 *            Two numbers giving the x and y coordinates within the region which is anchored to
	 *            the video viewport and does not change location even when the region does, e.g.
	 *            because of font size changes. Defaults to (0,100), i.e. the bottom left corner of
	 *            the region.
	 * @param viewportAnchor
	 *            Two numbers giving the x and y coordinates within the video viewport to which the
	 *            region anchor point is anchored. Defaults to (0,100), i.e. the bottom left corner
	 *            of the viewport.
	 * @param scroll
	 *            <b>none</b>: Indicates that the cues in the region are not to scroll and instead
	 *            stay fixed at the location they were first painted in. <b>up</b> Indicates that
	 *            the cues in the region will be added at the bottom of the region and push any
	 *            already displayed cues in the region up until all lines of the new cue are visible
	 *            in the region.
	 */
	public VttRegion(String id, String width, int lines, String regionAnchor,
		String viewportAnchor, Scroll scroll)
	{
		this.id = id;
		this.width = width;
		this.lines = lines;
		this.regionAnchor = regionAnchor;
		this.viewportAnchor = viewportAnchor;
		this.scroll = scroll;
	}

	public String getId()
	{
		return id;
	}

	public String getWidth()
	{
		return width;
	}

	public int getLines()
	{
		return lines;
	}

	public String getRegionAnchor()
	{
		return regionAnchor;
	}

	public String getViewportAnchor()
	{
		return viewportAnchor;
	}

	public Scroll getScroll()
	{
		return scroll;
	}

	/**
	 * Gets the representation as StringBuilder
	 * 
	 * @return the representation as StringBuilder
	 */
	public StringBuilder getRepresentation()
	{
		StringBuilder builder = new StringBuilder(500);
		builder.append("Region: ");
		builder.append("id=");
		builder.append(getId());
		builder.append(" width=");
		builder.append(getWidth());
		builder.append(" lines=");
		builder.append(getLines());
		builder.append(" regionanchor=");
		builder.append(getRegionAnchor());
		builder.append(" viewportanchor=");
		builder.append(getViewportAnchor());
		builder.append(" scroll=");
		builder.append(getScroll().name());
		return builder;
	}
}
