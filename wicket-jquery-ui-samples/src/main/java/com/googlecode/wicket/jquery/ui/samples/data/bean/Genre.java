package com.googlecode.wicket.jquery.ui.samples.data.bean;

import java.util.List;

import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.utils.ListUtils;

public class Genre implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private static final String EMPTY_COVER = "cover-empty.png";

	private final int id;
	private final String name;
	private final String cover;

	public Genre(final String name)
	{
		this(0, name, EMPTY_COVER);
	}

	public Genre(final int id, final String name)
	{
		this(id, name, EMPTY_COVER);
	}

	public Genre(final int id, final String name, final String cover)
	{
		this.id = id;
		this.name = name;
		this.cover = cover;
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getFullName()
	{
		return String.format("%02d - %s", this.id, this.name);
	}

	public String getCover()
	{
		return "images/" + this.cover;
	}

	public String getCoverUrl()
	{
		return UrlUtils.rewriteToContextRelative("images/" + this.cover, RequestCycle.get());
	}

	/**
	 * #toString() needs to be overridden if no renderer is provided.
	 * #toString() is also used by {@link ListUtils#contains(String, List)} method.
	 */
	@Override
	public String toString()
	{
		return this.name;
	}
}
