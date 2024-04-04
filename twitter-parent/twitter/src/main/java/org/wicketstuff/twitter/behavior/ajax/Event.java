package org.wicketstuff.twitter.behavior.ajax;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://dev.twitter.com/docs/intents/events#event-object
 * 
 * @author Till Freier
 * 
 */
public class Event implements Serializable
{
	public enum EventRegion
	{
		CLICK, COUNT, FOLLOW, SCREEN_NAME
	}

	public enum EventType
	{
		CLICK, FAVOITE, FOLLOW, INTENT, RETWEET, TWEET
	}

	private static final Logger log = LoggerFactory.getLogger(Event.class);

	public static Event createEvent(final String type, final String region,
		final String dataTweetId, final String dataSourceTweetId, final String dataScreenName,
		final String dataUserId)
	{
		final EventType eventType = parseEnumValue(type, EventType.class);
		final EventRegion eventRegion = parseEnumValue(region, EventRegion.class);

		return new Event(eventType, eventRegion, dataTweetId, dataSourceTweetId, dataScreenName,
			dataUserId);
	}


	private static <T extends Enum<T>> T parseEnumValue(final String value, final Class<T> enumClass)
	{
		try
		{
			return Enum.valueOf(enumClass, value.toUpperCase());
		}
		catch (final Exception e)
		{
			log.error("parseEnumValue", e);
		}

		return null;
	}

	private final String dataScreenName;
	private final String dataSourceTweetId;
	private final String dataTweetId;
	private final String dataUserId;
	private final EventRegion region;

	private final EventType type;


	private Event(final EventType type, final EventRegion region, final String dataTweetId,
		final String dataSourceTweetId, final String dataScreenName, final String dataUserId)
	{
		super();
		this.type = type;
		this.region = region;
		this.dataTweetId = dataTweetId;
		this.dataSourceTweetId = dataSourceTweetId;
		this.dataScreenName = dataScreenName;
		this.dataUserId = dataUserId;
	}


	/**
	 * @return the dataScreenName
	 */
	public String getDataScreenName()
	{
		return dataScreenName;
	}


	/**
	 * @return the dataSourceTweetId
	 */
	public String getDataSourceTweetId()
	{
		return dataSourceTweetId;
	}


	/**
	 * @return the dataTweetId
	 */
	public String getDataTweetId()
	{
		return dataTweetId;
	}


	/**
	 * @return the dataUserId
	 */
	public String getDataUserId()
	{
		return dataUserId;
	}


	/**
	 * @return the region that indicates whether the user clicked a Tweet Button, Follow Button,
	 *         Tweet Count, or Screen Name on Tweet Button or Follow Button integrations.
	 */
	public EventRegion getRegion()
	{
		return region;
	}


	/**
	 * @return the type of event that occurred
	 */
	public EventType getType()
	{
		return type;
	}


}
