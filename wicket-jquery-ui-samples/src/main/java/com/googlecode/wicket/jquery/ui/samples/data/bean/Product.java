package com.googlecode.wicket.jquery.ui.samples.data.bean;

import org.apache.wicket.util.io.IClusterable;

public class Product implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String desc;
	private double price;

	public Product(final int id, final String name, final String description, final double price)
	{
		this.id = id;
		this.name = name;
		this.desc = description;
		this.price = price;
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getDescription()
	{
		return this.desc;
	}

	public double getPrice()
	{
		return this.price;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
