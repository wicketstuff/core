/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.kendo.ui.widget.treeview;

import org.apache.wicket.Page;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.io.IClusterable;

/**
 * Provides a node for the {@link AjaxTreeView}<br/>
 * The wrapped object can be of any type
 * 
 * @param <T> the model object
 * @author Sebastien Briquet - sebfz1
 */
public class TreeNode<T> implements IClusterable
{
	private static final long serialVersionUID = 1L;

	public static final int ROOT = 0;

	private static int sequence = 1;

	private final int uid;
	private final int pid;
	private final T wrapped;
	private CharSequence url = null;

	/**
	 * Constructor for root node
	 * 
	 * @param id the node-id
	 * @param object the wrapped object
	 */
	public TreeNode(int id, T object)
	{
		this(id, ROOT, object);
	}

	/**
	 * Constructor for root node
	 * 
	 * @param id the node-id
	 * @param object the wrapped object
	 * @param url the node url
	 */
	public TreeNode(int id, T object, CharSequence url)
	{
		this(id, ROOT, object, url);
	}

	/**
	 * Constructor for root node
	 * 
	 * @param id the node-id
	 * @param object the wrapped object
	 * @param pageClass the node {@link Page} to redirect to
	 */
	public TreeNode(int id, T object, Class<? extends Page> pageClass)
	{
		this(id, ROOT, object, pageClass, new PageParameters());
	}

	/**
	 * Constructor for root node
	 * 
	 * @param id the node-id
	 * @param object the wrapped object
	 * @param pageClass {@link Page} to redirect to
	 * @param parameters the page parameters
	 */
	public TreeNode(int id, T object, Class<? extends Page> pageClass, PageParameters parameters)
	{
		this(id, ROOT, object, RequestCycle.get().urlFor(pageClass, parameters));
	}

	/**
	 * Constructor
	 * 
	 * @param id the node-id
	 * @param parentId the parent node-id
	 * @param object the wrapped object
	 */
	public TreeNode(int id, int parentId, T object)
	{
		this.uid = id;
		this.pid = parentId;
		this.wrapped = object;
	}

	/**
	 * Constructor
	 * 
	 * @param id the node-id
	 * @param parentId the parent node-id
	 * @param object the wrapped object
	 * @param url the node url
	 */
	public TreeNode(int id, int parentId, T object, CharSequence url)
	{
		this(id, parentId, object);

		this.url = url;
	}

	/**
	 * Constructor
	 * 
	 * @param id the node-id
	 * @param parentId the parent node-id
	 * @param object the wrapped object
	 * @param pageClass {@link Page} to redirect to
	 */
	public TreeNode(int id, int parentId, T object, Class<? extends Page> pageClass)
	{
		this(id, parentId, object, pageClass, new PageParameters());
	}

	/**
	 * Constructor
	 * 
	 * @param id the node-id
	 * @param parentId the parent node-id
	 * @param object the wrapped object
	 * @param pageClass {@link Page} to redirect to
	 * @param parameters the page parameters
	 */
	public TreeNode(int id, int parentId, T object, Class<? extends Page> pageClass, PageParameters parameters)
	{
		this(id, parentId, object, RequestCycle.get().urlFor(pageClass, parameters));
	}

	/**
	 * Gets the node-id
	 * 
	 * @return the node-id
	 */
	public int getId()
	{
		return this.uid;
	}

	/**
	 * Gets the parent node-id
	 * 
	 * @return the parent node-id
	 */
	public int getParentId()
	{
		return this.pid;
	}

	/**
	 * Gets the text of the object
	 * 
	 * @return {@link String#valueOf(Object)} by default
	 */
	public String getText()
	{
		return String.valueOf(this.wrapped);
	}

	/**
	 * Gets the wrapped object
	 * 
	 * @return the wrapped object
	 */
	public T getObject()
	{
		return this.wrapped;
	}

	/**
	 * Gets the node url
	 * 
	 * @return the node url if specified, else {@code null}
	 */
	public CharSequence getUrl()
	{
		return this.url;
	}

	/**
	 * Indicates whether the node has children
	 * 
	 * @return {@code true} by default
	 */
	public boolean hasChildren()
	{
		return true;
	}

	/**
	 * Indicates whether the node has a specified url
	 * 
	 * @return {@code true} or {@code false}
	 */
	public boolean hasUrl()
	{
		return this.url != null;
	}

	/**
	 * Gets the next id-sequence
	 *
	 * @return 0x00000000 to 0x7FFFFFFF
	 */
	public static synchronized int nextSequence()
	{
		return TreeNode.sequence++ % Integer.MAX_VALUE;
	}

	/**
	 * Helper method that construct a new root {@link TreeNode}
	 * 
	 * @param object the wrapped object
	 * @return a new {@code TreeNode}
	 */
	public static <T> TreeNode<T> of(T object)
	{
		return new TreeNode<T>(TreeNode.nextSequence(), object);
	}

	/**
	 * Helper method that construct a new child {@link TreeNode}
	 * 
	 * @param object the wrapped object
	 * @param parentId the node parent id
	 * @return a new {@code TreeNode}
	 */
	public static <T> TreeNode<T> of(int parentId, T object)
	{
		return new TreeNode<T>(TreeNode.nextSequence(), parentId, object);
	}
}
