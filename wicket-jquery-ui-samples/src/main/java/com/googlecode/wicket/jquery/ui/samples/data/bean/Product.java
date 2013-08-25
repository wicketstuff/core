package com.googlecode.wicket.jquery.ui.samples.data.bean;

import org.apache.wicket.util.io.IClusterable;

public class Product implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String desc;
	private double price;

	private Vendor vendor;

	public Product(final int id, final String name, final String description, final double price)
	{
		this.id = id;
		this.name = name;
		this.desc = description;
		this.price = price;
		this.vendor = new Vendor();
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

	public Vendor getVendor()
	{
		return this.vendor;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public class Vendor implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private String name;

		public Vendor()
		{
			this("noname");
		}

		public Vendor(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}
	}
}
