package org.wicketstuff.simile.timeline;

public interface ITimelineEvent {

	String getTitle();

	String getLink();

	String getStartFormatted();

	String getEndFormatted();

	boolean isDuration();

	String getColor();

	String getText();

}