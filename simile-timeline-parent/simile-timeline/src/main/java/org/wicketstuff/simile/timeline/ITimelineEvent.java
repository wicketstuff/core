package org.wicketstuff.simile.timeline;

import java.io.Serializable;

public interface ITimelineEvent extends Serializable
{

	String getTitle();

	String getLink();

	String getStartFormatted();

	String getEndFormatted();

	boolean isDuration();

	String getColor();

	String getText();

}