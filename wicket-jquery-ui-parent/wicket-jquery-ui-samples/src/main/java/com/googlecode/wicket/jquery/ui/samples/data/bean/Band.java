/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package com.googlecode.wicket.jquery.ui.samples.data.bean;

import org.apache.wicket.util.io.IClusterable;

public class Band implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private final String name;
	private final String desc;

	public Band(String name, String desc)
	{
		this.name = name;
		this.desc = desc;
	}

	public String getName()
	{
		return this.name;
	}

	public String getDesc()
	{
		return this.desc;
	}
}
