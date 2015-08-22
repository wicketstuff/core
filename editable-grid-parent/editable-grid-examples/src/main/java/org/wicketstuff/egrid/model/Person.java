package org.wicketstuff.egrid.model;

import java.io.Serializable;

public class Person implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String address;
	private String age;
	
	public Person()
	{

	}
	public Person(String name) 
	{
		this.name = name;
	}
	public Person(String name, String age, String address)
	{
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}	

	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	@Override
	public String toString()
	{
		return name;
	}
}
