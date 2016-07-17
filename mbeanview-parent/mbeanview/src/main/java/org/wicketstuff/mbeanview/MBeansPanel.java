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
package org.wicketstuff.mbeanview;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeSet;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Jmx panel, to view and operate the applications mbeans
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class MBeansPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	public static final String VIEW_PANEL_ID = "view";

	private static final ResourceReference CSS = new PackageResourceReference(MBeansPanel.class,
		"css/MBeansPanel.css");

	private MbeanNode selected;
	
	public MBeansPanel(String id, IModel<MBeanServer> server)
	{
		super(id, server);

		MBeanTree mBeansTree = new MBeanTree("mBeansTree", new MbeanTreeProvider(server));
		add(mBeansTree);
		add(new EmptyPanel(VIEW_PANEL_ID).setOutputMarkupId(true));
	}

	@SuppressWarnings("unchecked")
	public IModel<MBeanServer> getModel()
	{
		return (IModel<MBeanServer>)getDefaultModel();
	}

	private class MBeanTree extends DefaultNestedTree<MbeanNode>
	{
		private static final long serialVersionUID = 1L;

		public MBeanTree(String id, ITreeProvider<MbeanNode> provider)
		{
			super(id, provider);
		}

		@Override
		protected Component newContentComponent(String id, IModel<MbeanNode> node)
		{
			return new Folder<MbeanNode>(id, this, node)
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected boolean isSelected()
				{
					return getModelObject() == selected;
				}
				
				@Override
				protected boolean isClickable()
				{
					return true;
				}
				
				@Override
				protected String getOpenStyleClass()
				{
					String styleClass = getModelObject().getStyleClass();
					if (styleClass == null) {
						return super.getOpenStyleClass();
					} else {
						return styleClass;
					}
				}

				@Override
				protected String getClosedStyleClass()
				{
					String styleClass = getModelObject().getStyleClass();
					if (styleClass == null) {
						return super.getClosedStyleClass();
					} else {
						return styleClass;
					}
				}

				@Override
				protected void onClick(Optional<AjaxRequestTarget> target)
				{
					if (selected != null) {
						updateNode(selected, target);
					}
					selected = getModelObject();
					updateNode(selected, target);
						
					Component newView = getModelObject().newView(VIEW_PANEL_ID);
					newView.setOutputMarkupId(true);
					MBeansPanel.this.replace(newView);
					target.ifPresent(t -> t.add(newView));
				}
			};
		}
	}

	private abstract class MbeanNode implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private List<MbeanNode> children;

		public Component newView(String wicketId)
		{
			return new EmptyPanel(wicketId);
		}

		public String getStyleClass()
		{
			return null;
		}

		public List<MbeanNode> getChildren()
		{
			if (children == null)
			{
				try
				{
					setChildren(createChildren());
				}
				catch (Exception e)
				{
					setChildren(Collections.<MbeanNode> singletonList(new FailureNode(e)));
				}
			}

			return children;
		}

		protected void setChildren(List<MbeanNode> children)
		{
			this.children = children;
		}

		protected List<MbeanNode> createChildren() throws Exception
		{
			return new ArrayList<>();
		}

		public boolean hasChildren()
		{
			return getChildren().isEmpty() == false;
		}
	}

	private class FailureNode extends MbeanNode
	{

		private Exception ex;

		public FailureNode(Exception ex)
		{
			this.ex = ex;
		}

		@Override
		public String toString()
		{
			return ex.getMessage();
		}

		@Override
		public String getStyleClass()
		{
			return "failure";
		}
		
		@Override
		public Component newView(String wicketId)
		{
			return new Label(wicketId, new AbstractReadOnlyModel<String>()
			{
				@Override
				public String getObject()
				{
					StringWriter stringWriter = new StringWriter();

					PrintWriter writer = new PrintWriter(stringWriter);
					ex.printStackTrace(writer);

					return stringWriter.toString();
				}
			});
		}
	}

	private class DomainNode extends MbeanNode
	{

		private String name;

		public DomainNode(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return name;
		}

		public Component getView(String wicketId, IModel<MBeanServer> server)
		{
			return new MBeanTree(wicketId, new MbeanTreeProvider(server,
				Collections.<MbeanNode> singletonList(this)));
		}

		@Override
		public boolean hasChildren()
		{
			return true;
		}

		@Override
		protected List<MbeanNode> createChildren() throws Exception
		{
			List<KeyPath> paths = new ArrayList<>();

			for (ObjectName objectName : getModel().getObject().queryNames(null,
				new ObjectName(this.name + ":*")))
			{
				KeyPath path = new KeyPath();
				for (Entry<String, String> entry : objectName.getKeyPropertyList().entrySet())
				{
					path.add(new Key(entry.getKey(), entry.getValue()));
				}
				paths.add(path);
			}

			return createKeyNodes(name + ":", paths);
		}

		/**
		 * @see < a href="https://blogs.oracle.com/lmalventosa/entry/jconsole_mbeans_tab_mbean_tree">JConsole MBean tree</>
		 */
		private List<MbeanNode> createKeyNodes(String query, List<KeyPath> paths) throws Exception
		{
			Map<Key, List<KeyPath>> head2Tails = new HashMap<>();

			for (KeyPath path : paths)
			{
				Key head = path.head();

				List<KeyPath> tails = head2Tails.get(head);
				if (tails == null)
				{
					tails = new ArrayList<>();
					head2Tails.put(head, tails);
				}

				KeyPath tail = path.tail();
				if (tail.isEmpty() == false)
				{
					tails.add(tail);
				}
			}

			List<MbeanNode> children = new ArrayList<>();

			for (Key head : new TreeSet<>(head2Tails.keySet()))
			{
				KeyNode child = new KeyNode(head);

				children.add(child);

				String temp = query + (query.endsWith(":") ? "" : ",") + head;
				for (ObjectInstance instance : getModel().getObject().queryMBeans(null,
					new ObjectName(temp)))
				{
					child.setInstance(instance);
				}

				child.getChildren().addAll(createKeyNodes(temp, head2Tails.get(head)));
			}

			return children;
		}
	}

	private class KeyPath extends TreeSet<Key>
	{

		public Key head()
		{
			Iterator<Key> iterator = iterator();
			return iterator.next();
		}

		public KeyPath append(Key key)
		{
			KeyPath copy = new KeyPath();
			copy.addAll(this);
			copy.add(key);
			return copy;
		}

		public KeyPath tail()
		{
			KeyPath tail = new KeyPath();

			Iterator<Key> iterator = iterator();
			// skip head
			iterator.next();

			while (iterator.hasNext())
			{
				tail.add(iterator.next());
			}

			return tail;
		}

		@Override
		public String toString()
		{
			StringBuilder string = new StringBuilder();

			for (Key key : this)
			{
				if (string.length() > 0)
				{
					string.append(',');
				}
				string.append(key);
			}

			return string.toString();
		}
	}

	private class Key implements Comparable<Key>, Serializable
	{
		public final String name;
		public final String value;

		public Key(String name, String value)
		{
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString()
		{
			return name + "=" + value;
		}

		@Override
		public int hashCode()
		{
			return name.hashCode() + value.hashCode();
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof Key)
			{
				Key key = (Key)obj;

				return this.name.equals(key.name) && this.value.equals(key.value);
			}
			return false;
		}

		@Override
		public int compareTo(Key key)
		{
			if (equals(key))
			{
				return 0;
			}

			if ("type".equals(this.name) || "name".equals(key.name))
			{
				return -1;
			}

			if ("name".equals(this.name) || "type".equals(key.name))
			{
				return 1;
			}

			int byName = this.name.compareTo(key.name);
			if (byName == 0) {
				return this.value.compareTo(key.value);
			} else {
				return byName;
			}
		}
	}

	private class KeyNode extends MbeanNode
	{

		private Key key;

		// optional instance for this key
		private ObjectInstance instance;

		public KeyNode(Key key)
		{
			this.key = key;
		}

		public void setInstance(ObjectInstance instance) throws Exception
		{
			this.instance = instance;

			MBeanInfo info = getModel().getObject().getMBeanInfo(instance.getObjectName());

			MBeanAttributeInfo[] attributes = info.getAttributes();
			if (attributes.length > 0)
			{
				getChildren().add(new AttributesNode(instance, attributes));
			}

			MBeanOperationInfo[] operations = info.getOperations();
			if (operations.length > 0)
			{
				getChildren().add(new OperationsNode(instance, operations));
			}

			MBeanNotificationInfo[] notifications = info.getNotifications();
			if (notifications.length > 0)
			{
				getChildren().add(new NotificationsNode(instance, notifications));
			}
		}

		@Override
		public String getStyleClass()
		{
			if (instance == null) {
				return null;
			} else {
				return "tree-folder-instance";
			}
		}
		
		@Override
		public String toString()
		{
			return key.value;
		}
		
		@Override
		public Component newView(String wicketId)
		{
			if (instance != null) {
				return new InstancePanel(wicketId, getModel(), instance);
			}
			return super.newView(wicketId);
		}
	}

	private class AttributesNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;

		private ObjectInstance instance;

		private final MBeanAttributeInfo[] attributes;

		public AttributesNode(ObjectInstance instance, MBeanAttributeInfo[] attributes)
		{
			this.instance = instance;

			this.attributes = attributes;
		}

		@Override
		public Component newView(String id)
		{
			return new AttributeValuesPanel(id, getModel(), instance.getObjectName(), attributes);
		}

		@Override
		public String toString()
		{
			return "Attributes";
		}

		@Override
		public boolean hasChildren()
		{
			return true;
		}

		@Override
		protected List<MbeanNode> createChildren() throws Exception
		{
			List<MbeanNode> children = new ArrayList<>();

			for (MBeanAttributeInfo attribute : attributes)
			{
				children.add(new AttributeNode(instance, attribute));
			}

			return children;
		}
	}

	private class AttributeNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;

		private ObjectInstance instance;

		private final MBeanAttributeInfo attribute;

		public AttributeNode(ObjectInstance instance, MBeanAttributeInfo attribute)
		{
			this.instance = instance;
			this.attribute = attribute;
		}

		@Override
		public Component newView(String wicketId)
		{
			return new AttributeValuesPanel(wicketId, getModel(), instance.getObjectName(),
				new MBeanAttributeInfo[] { attribute });
		}

		@Override
		public String toString()
		{
			return attribute.getName();
		}
	}

	private class OperationsNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;

		private ObjectInstance instance;

		private final MBeanOperationInfo[] operations;

		public OperationsNode(ObjectInstance instance, MBeanOperationInfo[] operations)
		{
			this.instance = instance;
			this.operations = operations;
		}

		@Override
		public Component newView(String id)
		{
			return new OperationsPanel(id, getModel(), instance.getObjectName(), operations);
		}

		@Override
		public String toString()
		{
			return "Operations";
		}

		@Override
		protected List<MbeanNode> createChildren() throws Exception
		{
			List<MbeanNode> children = new ArrayList<>();

			for (MBeanOperationInfo operation : operations)
			{
				children.add(new OperationNode(instance, operation));
			}

			return children;
		}
	}

	private class OperationNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;

		private final MBeanOperationInfo operation;

		private ObjectInstance instance;

		public OperationNode(ObjectInstance instance, MBeanOperationInfo operation)
		{
			this.instance = instance;

			this.operation = operation;
		}

		@Override
		public Component newView(String wicketId)
		{
			return new OperationsPanel(wicketId, getModel(), instance.getObjectName(),
				new MBeanOperationInfo[] { operation });
		}

		@Override
		public String toString()
		{
			return operation.getName();
		}
	}

	private class NotificationsNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;

		private ObjectInstance instance;

		private final MBeanNotificationInfo[] notifications;

		public NotificationsNode(ObjectInstance instance, MBeanNotificationInfo[] notifications)
		{
			this.instance = instance;
			this.notifications = notifications;
		}

		@Override
		public String toString()
		{
			return "Notifications";
		}

		@Override
		protected List<MbeanNode> createChildren() throws Exception
		{
			List<MbeanNode> children = new ArrayList<>();

			for (MBeanNotificationInfo notification : notifications)
			{
				children.add(new NotificationNode(instance, notification));
			}

			return children;
		}
	}

	private class NotificationNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;

		private ObjectInstance instance;

		private final MBeanNotificationInfo notification;

		public NotificationNode(ObjectInstance instance, MBeanNotificationInfo notification)
		{
			this.instance = instance;

			this.notification = notification;
		}

		@Override
		public String toString()
		{
			return notification.getName();
		}
	}

	@Override
	public void renderHead(IHeaderResponse res)
	{
		res.render(CssHeaderItem.forReference(CSS));
	}

	private class MbeanTreeProvider implements ITreeProvider<MbeanNode>
	{

		private IModel<MBeanServer> server;

		private List<MbeanNode> roots;

		public MbeanTreeProvider(IModel<MBeanServer> server)
		{
			this.server = server;

			roots = new ArrayList<>();

			String[] domains = server.getObject().getDomains();
			for (String domain : domains)
			{
				roots.add(new DomainNode(domain));
			}
		}

		public MbeanTreeProvider(IModel<MBeanServer> server, List<MbeanNode> roots)
		{
			this.server = server;

			this.roots = roots;
		}

		@Override
		public void detach()
		{
			server.detach();
		}

		@Override
		public Iterator<? extends MbeanNode> getRoots()
		{
			return roots.iterator();
		}

		@Override
		public boolean hasChildren(MbeanNode node)
		{
			return node.hasChildren();
		}

		@Override
		public Iterator<? extends MbeanNode> getChildren(MbeanNode node)
		{
			return node.getChildren().iterator();
		}

		@Override
		public IModel<MbeanNode> model(MbeanNode object)
		{
			return Model.of(object);
		}
	}
}
