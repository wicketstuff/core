/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.shiro.example.sprhibnative.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wicketstuff.shiro.example.sprhibnative.dao.UserDAO;
import org.wicketstuff.shiro.example.sprhibnative.model.User;

/**
 * Default implementation of the {@link UserService} interface. This service implements operations
 * related to User data.
 */
@Transactional
@Service("userService")
public class DefaultUserService implements UserService
{

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	public User getCurrentUser()
	{
		Long id = (Long)SecurityUtils.getSubject().getPrincipal();
		if (id != null)
		{
			// they are either authenticated or remembered from a previous session,
			// so return the user:
			return getUser(id);
		}
		else
		{
			// not logged in or remembered:
			return null;
		}
	}

	public void createUser(String username, String email, String password)
	{
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(new Sha256Hash(password).toHex());
		userDAO.createUser(user);
	}

	public List<User> getAllUsers()
	{
		return userDAO.getAllUsers();
	}

	public User getUser(Long userId)
	{
		return userDAO.getUser(userId);
	}

	public void deleteUser(Long userId)
	{
		userDAO.deleteUser(userId);
	}

	public void updateUser(User user)
	{
		userDAO.updateUser(user);
	}

}
