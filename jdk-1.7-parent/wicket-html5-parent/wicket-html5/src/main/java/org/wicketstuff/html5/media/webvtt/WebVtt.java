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

import java.util.ArrayList;
import java.util.List;

/**
 * A WebVTT document<br>
 * <br>
 * Further information:<br>
 * https://developer.mozilla.org/en-US/docs/Web/API/Web_Video_Text_Tracks_Format#Cue_Settings<br>
 * http://dev.w3.org/html5/webvtt/<br>
 * http://www.jwplayer.com/html5/webvtt/
 * 
 * @author Tobias Soloschenko
 */
public class WebVtt
{

	private List<VttCue> vttCues = new ArrayList<VttCue>();

	private List<VttRegion> vttRegions = new ArrayList<VttRegion>();

	private String description;

	private String note;

	/**
	 * Gets the description shown beside WEBVTT<br>
	 * <br>
	 * Note: A " - " is prefixed to the description and all \n's are replaced with " "
	 * 
	 * @return the description the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Gets the description shown beside WEBVTT<br>
	 * <br>
	 * Note: A " - " is prefixed to the description and all \n's are replaced with " "
	 * 
	 * @param description
	 *            the description to be shown
	 * 
	 * @return the vtt to perform further operations
	 */
	public WebVtt setDescription(String description)
	{
		this.description = description;
		return this;
	}

	/**
	 * Gets the document note
	 * 
	 * @return the document note
	 */
	public String getNote()
	{
		return note;
	}

	/**
	 * Sets the document note
	 * 
	 * @param note
	 *            the note to be shown in the document
	 * @return the vtt to perform further operations
	 */
	public WebVtt setNote(String note)
	{
		this.note = note;
		return this;
	}

	/**
	 * Adds a cue
	 * 
	 * @param VttCue
	 *            the cue to be added
	 */
	public void addCue(VttCue vttCue)
	{
		vttCues.add(vttCue);
	}

	/**
	 * Removes a cue
	 * 
	 * @param vttCue
	 *            the cue to be removed
	 */
	public void removeCue(VttCue vttCue)
	{
		vttCues.remove(vttCue);
	}

	/**
	 * Removes a cue based on its index
	 * 
	 * @param index
	 *            the index of the cue to be removed
	 */
	public void removeCue(int index)
	{
		vttCues.remove(index);
	}

	/**
	 * Adds a region
	 * 
	 * @param vttRegion
	 *            the region to be added
	 */
	public void addRegion(VttRegion vttRegion)
	{
		vttRegions.add(vttRegion);
	}

	/**
	 * Removes a region
	 * 
	 * @param vttRegion
	 *            the region to be removed
	 */
	public void removeRegion(VttRegion vttRegion)
	{
		vttRegions.remove(vttRegion);
	}

	/**
	 * Removes a region based on the given index
	 * 
	 * @param index
	 *            the index of the region to be removed
	 */
	public void removeRegion(int index)
	{
		vttRegions.remove(index);
	}

	/**
	 * Creates the WebVTT document
	 * 
	 * @return the WebVTT document as string
	 */
	public String create()
	{
		StringBuilder builder = new StringBuilder(1000);
		builder.append("WEBVTT");
		if (getDescription() != null)
		{
			builder.append(" - ");
			builder.append(getDescription().replace("\n", " "));
		}
		if (getNote() != null)
		{
			builder.append("\n\n");
			builder.append("NOTE \n");
			builder.append(getNote());
			builder.append("\n");
		}

		for (VttRegion vttRegion : vttRegions)
		{
			builder.append("\n");
			builder.append(vttRegion.getRepresentation());
		}

		for (VttCue vttCue : vttCues)
		{
			builder.append("\n\n");
			// This has to be excluded from getRepresentation,
			// because it has to be shown before the actual item
			String note = vttCue.getNote();
			if (note != null)
			{
				builder.append("NOTE \n");
				builder.append(note);
				builder.append("\n\n");
			}
			builder.append(vttCue.getRepresentation());
		}
		return builder.toString();
	}
}
