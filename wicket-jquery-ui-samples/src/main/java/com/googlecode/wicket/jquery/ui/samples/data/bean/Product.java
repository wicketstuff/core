package com.googlecode.wicket.jquery.ui.samples.data.bean;

import java.text.ParseException;
import java.util.Date;

import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.utils.DateUtils;

public class Product implements IClusterable
{
	private static final long serialVersionUID = 1L;

	public static Product of(JSONObject object)
	{
		Product product = new Product(object.optInt("id"), object.optString("name"), object.optString("description"), object.optDouble("price"));

		try
		{
			product.setDate(DateUtils.parse(object.optString("date")));
		}
		catch (ParseException e)
		{
			// not handled
		}

		return product;
	}

	private int id;
	private String name;
	private String desc;
	private long date;
	private double price;

	private Vendor vendor;

	public Product(final int id, final String name, final String description, final double price)
	{
		this(id, name, description, price, new Vendor());
	}

	public Product(final int id, final String name, final String description, final double price, Vendor vendor)
	{
		this.id = id;
		this.name = name;
		this.desc = description;
		this.date = DateUtils.utc();
		this.price = price;
		this.vendor = vendor;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.desc;
	}

	public void setDescription(String desc)
	{
		this.desc = desc;
	}

	public Date getDate()
	{
		return new Date(this.date);
	}

	public void setDate(Date date)
	{
		this.date = date.getTime();
	}

	public double getPrice()
	{
		return this.price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public Vendor getVendor()
	{
		return this.vendor;
	}

	public void setVendor(Vendor vendor)
	{
		this.vendor = vendor;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public static class Vendor implements IClusterable
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
