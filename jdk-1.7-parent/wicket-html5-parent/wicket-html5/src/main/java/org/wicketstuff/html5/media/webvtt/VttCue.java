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
 * A cue block containing the translations<br>
 * <br>
 * Base on the browser cues can be styled with CSS. So you only have to include a CSS file into the
 * HTML page the video is embedded.<br>
 * <br>
 * Further information:<br>
 * http://tiffanybbrown.com/2013/12/14/subtitles-and-captions-with-webvtt/
 * 
 * 
 * @author Tobias Soloschenko
 * 
 */
public class VttCue
{

	public enum Vertical
	{
		lr("lr"), rl("rl");

		private String verticalName;

		Vertical(String verticalName)
		{
			this.verticalName = verticalName;
		}

		public String getVerticalName()
		{
			return verticalName;
		}

		public void setVerticalName(String verticalName)
		{
			this.verticalName = verticalName;
		}

	}

	public enum Alignment
	{
		start, middle, end
	}

	private String cueId;

	private String line;

	private String position;

	private String size;

	private Alignment alignment;

	private Vertical vertical;

	private VttTime startTime;

	private VttTime stopTime;

	private String text;

	private String voiceSpan;

	private VttRegion vttRegion;

	private String note;

	/**
	 * Creates a cue with the required arguments
	 * 
	 * @param cueId
	 *            the id of the cue
	 * @param startTime
	 *            the start time of the cue
	 * @param stopTime
	 *            the end time of the cue
	 * @param text
	 *            the text of the cue
	 */
	public VttCue(String cueId, VttTime startTime, VttTime stopTime, String text)
	{
		this.cueId = cueId;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.text = text;
	}

	/**
	 * Gets the position the text will appear vertically
	 * 
	 * @return the position the text will appear vertically
	 */
	public String getLine()
	{
		return line;
	}

	/**
	 * Sets the position the text will appear vertically (number, percentage e.g. 20% or 10)
	 * 
	 * @param line
	 *            the position the text will appear vertically
	 * @return the cue to perform further operations
	 */
	public VttCue setLine(String line)
	{
		this.line = line;
		return this;
	}

	/**
	 * Gets the position of the text
	 * 
	 * @return the position of the text
	 */
	public String getPosition()
	{
		return position;
	}

	/**
	 * Sets the position the text will appear horizontally (percentage e.g. 20%)
	 * 
	 * @param position
	 *            the position of the text
	 * @return the cue to perform further operations
	 */
	public VttCue setPosition(String position)
	{
		this.position = position;
		return this;
	}

	/**
	 * Gets the size (width of the text area)
	 * 
	 * @return the size (width of the text area)
	 */
	public String getSize()
	{
		return size;
	}

	/**
	 * Sets the size (width of the text area)
	 * 
	 * @param size
	 *            the size of the text area (percentage e.g. 40%)
	 * @return the cue to perform further operations
	 */
	public VttCue setSize(String size)
	{
		this.size = size;
		return this;
	}

	/**
	 * Gets the alignment of the text
	 * 
	 * @return the alignment of the text
	 */
	public Alignment getAlignment()
	{
		return alignment;
	}

	/**
	 * Sets the alignment of the text
	 * 
	 * @param alignment
	 *            the alignment of the text
	 * @return the cue to perform further operations
	 */
	public VttCue setAlignment(Alignment alignment)
	{
		this.alignment = alignment;
		return this;
	}

	/**
	 * Gets the vertical behavior
	 * 
	 * @return the vertical behavior
	 */
	public Vertical getVertical()
	{
		return vertical;
	}

	/**
	 * Sets the text to be displayed vertically rather than horizontally, such as in some Asian
	 * languages
	 * 
	 * @param vertical
	 *            the vertical behavior
	 * @return the cue to perform further operations
	 */
	public VttCue setVertical(Vertical vertical)
	{
		this.vertical = vertical;
		return this;
	}

	/**
	 * Gets the voice span applied to the cue
	 * 
	 * @return the voice span applied to the cue
	 */
	public String getVoiceSpan()
	{
		return voiceSpan;
	}

	/**
	 * Sets a voice span applied to the cue
	 * 
	 * @param voiceSpan
	 *            the voice span applied to the cue
	 * @return the cue to perform further operations
	 */
	public VttCue setVoiceSpan(String voiceSpan)
	{
		this.voiceSpan = voiceSpan;
		return this;
	}

	/**
	 * Gets the region applied to the cue
	 * 
	 * @return the region applied to the cue
	 */
	public VttRegion getRegion()
	{
		return vttRegion;
	}

	/**
	 * Sets the region applied to the cue
	 * 
	 * @param vttRegion
	 *            the region applied to the cue
	 * @return the cue to perform further operations
	 */
	public VttCue setRegion(VttRegion vttRegion)
	{
		this.vttRegion = vttRegion;
		return this;
	}

	/**
	 * Gets the note of the cue
	 * 
	 * @return the note of the cue
	 */
	public String getNote()
	{
		return note;
	}

	/**
	 * Sets the note of the cue
	 * 
	 * @param note
	 *            the note to be set to the cue
	 * @return the cue to perform further operations
	 */
	public VttCue setNote(String note)
	{
		this.note = note;
		return this;
	}

	/**
	 * Gets the cue id
	 * 
	 * @return the cue id
	 */
	public String getCueId()
	{
		return cueId;
	}

	/**
	 * Gets the start time
	 * 
	 * @return the start time
	 */
	public VttTime getStartTime()
	{
		return startTime;
	}

	/**
	 * Gets the stop time
	 * 
	 * @return the stop time
	 */
	public VttTime getStopTime()
	{
		return stopTime;
	}

	/**
	 * Gets the text content
	 * 
	 * @return the content as String
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Gets the representation as builder
	 * 
	 * @return thre representation as builder
	 */
	public StringBuilder getRepresentation()
	{
		StringBuilder builder = new StringBuilder(1000);
		builder.append(getCueId().replaceAll("\n", " "));
		builder.append("\n");

		builder.append(getStartTime().getRepresentation());
		builder.append(" --> ");
		builder.append(getStopTime().getRepresentation());

		if (getRegion() != null)
		{
			builder.append(" region:");
			builder.append(getRegion().getId());
		}

		if (getAlignment() != null)
		{
			builder.append(" align:");
			builder.append(getAlignment().name());
		}
		if (getVertical() != null)
		{
			builder.append(" vertical:");
			builder.append(getVertical().getVerticalName());
		}
		if (getSize() != null)
		{
			builder.append(" size:");
			builder.append(getSize());
		}
		if (getLine() != null)
		{
			builder.append(" line:");
			builder.append(getLine());
		}
		if (getPosition() != null)
		{
			builder.append(" position:");
			builder.append(getPosition());
		}
		builder.append("\n");

		if (getVoiceSpan() != null || getRegion() != null)
		{
			builder.append("<v");
			if (getVoiceSpan() != null)
			{
				builder.append(getVoiceSpan().startsWith(".") ? "" : " ");
				builder.append(getVoiceSpan());
			}
			if (getRegion() != null)
			{
				builder.append(" ");
				builder.append(getRegion().getId());
			}
			builder.append(">");
		}
		builder.append(getText());
		return builder;
	}
}
