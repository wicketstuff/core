package org.wicketstuff.simile.timeline.model;

import java.util.Date;

import org.apache.wicket.ajax.json.JSONFunction;

public class BandInfoParameters
{
	public enum DateTime
	{
		SECOND, MINUTE, HOUR, WEEK, DAY, MONTH, YEAR
	}

	private String width = "100px";
	private DateTime intervalUnit = DateTime.MONTH;
	private Integer intervalPixels = 70;
	private Date date = new Date();
	private boolean showEventText = false;
	private JSONFunction theme = new JSONFunction("theme");
	private JSONFunction eventSource = new JSONFunction("eventSource");
	private Integer syncWith = 0;
	private boolean highlight = true;
	private boolean overview = false;

	public Integer getIntervalPixels()
	{
		return intervalPixels;
	}

	public void setIntervalPixels(Integer intervalPixels)
	{
		this.intervalPixels = intervalPixels;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public DateTime getIntervalUnit()
	{
		return intervalUnit;
	}

	public void setIntervalUnit(DateTime intervalUnit)
	{
		this.intervalUnit = intervalUnit;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public boolean isShowEventText()
	{
		return showEventText;
	}

	public void setShowEventText(boolean showEventText)
	{
		this.showEventText = showEventText;
	}

	public JSONFunction getTheme()
	{
		return theme;
	}

	public void setTheme(JSONFunction theme)
	{
		this.theme = theme;
	}

	public JSONFunction getEventSource()
	{
		return eventSource;
	}

	public void setEventSource(JSONFunction eventSource)
	{
		this.eventSource = eventSource;
	}

	public boolean isHighlight()
	{
		return highlight;
	}

	public void setHighlight(boolean highlight)
	{
		this.highlight = highlight;
	}

	public void setSyncWith(Integer syncWith)
	{
		this.syncWith = syncWith;
	}

	public Integer getSyncWith()
	{
		return syncWith;
	}

	public void setOverview(boolean overview)
	{
		this.overview = overview;
	}

	public boolean isOverview()
	{
		return overview;
	}
}
