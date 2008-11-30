package org.wicketstuff.simile.timeline;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimelineEventModel implements ITimelineEvent {
	private final DateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
	
	private String title;
	
	private Date start;
	
	private Date end;
	
	private String link;
	
	private String color;

	private boolean isDuration;
	
	private String text;
	
	public TimelineEventModel(String title, Date start, Date end, String link, boolean isDuration)
	{
		if (start == null)
		{
			throw new IllegalStateException("Start should not be null");
		}
		this.title = title;
		this.start = start;
		this.end = end;
		this.link = link;
		this.isDuration = isDuration;
	}
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#getLink()
	 */
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#getStartFormatted()
	 */
	public String getStartFormatted()
	{
		return dateFormat.format(start);
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#getEndFormatted()
	 */
	public String getEndFormatted()
	{
		return dateFormat.format(end);
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#isDuration()
	 */
	public boolean isDuration() {
		return isDuration;
	}

	public void setDuration(boolean isDuration) {
		this.isDuration = isDuration;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#getColor()
	 */
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.simile.timeline.ITimelineEvent#getText()
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
