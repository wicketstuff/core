package com.googlecode.wicket.jquery.ui.samples.data.bean;

import org.apache.wicket.util.io.IClusterable;

public class User implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String mail;
	private String role;
	private Avatar avatar;

	public User()
	{
		this.name = "";
		this.mail = "";
		this.role = "";
	}

	public User(String name, String mail, String role)
	{
		this.name = name;
		this.mail = mail;
		this.role = role;
	}

	public String getName()
	{
		return this.name;
	}

	public String getMail()
	{
		return this.mail;
	}

	public String getRole()
	{
		return this.role;
	}

	public Avatar getAvatar()
	{
		return avatar;
	}

	public void setAvatar(Avatar avatar)
	{
		this.avatar = avatar;
	}

	@Override
	public String toString()
	{
		return String.format("%s [%s] - %s", this.name, this.mail, this.role);
	}

	// Bean //
	public static class Avatar implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private final int id;
		private final String path;

		public Avatar(final int id, final String path)
		{
			this.id = id;
			this.path = path;
		}

		public int getId()
		{
			return this.id;
		}

		public String getImagePath()
		{
			return "images/avatars/" + this.path;
		}

		@Override
		public String toString()
		{
			return "Avatar #" + this.id;
		}
	}
}
