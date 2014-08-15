package com.googlecode.wicket.jquery.ui.samples.data.model;

import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicket.jquery.ui.samples.data.bean.User;

public class UserModel extends CompoundPropertyModel<User>
{
	private static final long serialVersionUID = 1L;

	public UserModel()
	{
		super(new User());
	}

	public UserModel(User user)
	{
		super(user);
	}
}
