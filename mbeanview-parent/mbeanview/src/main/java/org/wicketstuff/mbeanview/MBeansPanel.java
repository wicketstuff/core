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

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
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

	public MBeansPanel(String id)
	{
		super(id);
		try
		{
			MbeanServerLocator reachMbeanServer = new MbeanServerLocator()
			{
				private static final long serialVersionUID = 1L;

				public MBeanServer get()
				{
					return ManagementFactory.getPlatformMBeanServer();
				}
			};
			MBeanTree mBeansTree = new MBeanTree("mBeansTree", getTreeModel(reachMbeanServer));
			add(mBeansTree);
			add(new EmptyPanel(VIEW_PANEL_ID).setOutputMarkupId(true));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private class MBeanTree extends Tree
	{
		private static final long serialVersionUID = 1L;

		public MBeanTree(String id, TreeModel model)
		{
			super(id, model);
			getTreeState().expandNode(getModelObject().getRoot());
		}

		@Override
		protected ResourceReference getCSS()
		{
			return CSS;
		}

		@Override
		protected void onNodeLinkClicked(AjaxRequestTarget target, TreeNode node)
		{
			if (node instanceof MbeanNode)
			{
				Component newView = ((MbeanNode)node).getView(VIEW_PANEL_ID);
				newView.setOutputMarkupId(true);
				MBeansPanel.this.replace(newView);
				target.add(newView);
			}
		}

		@Override
		protected Component newNodeIcon(MarkupContainer parent, String id, TreeNode node)
		{
			if (node instanceof DefaultMutableTreeNode)
			{
				DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode)node;
				if (mutableNode.getChildCount() > 0 &&
					(mutableNode.getChildAt(0) instanceof AttributeNode ||
						mutableNode.getChildAt(0) instanceof OperationNode || mutableNode.getChildAt(0) instanceof NotificationNode))
				{
					return new EmptyPanel(id).add(AttributeModifier.replace("style", "width:0;"));
				}
			}
			return super.newNodeIcon(parent, id, node);
		}
	}

	private TreeModel getTreeModel(MbeanServerLocator reachMbeanServer)
		throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException,
		IntrospectionException, ReflectionException
	{
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
		TreeModel model = new DefaultTreeModel(rootNode);
		String[] domains = reachMbeanServer.get().getDomains();
		for (String domain : domains)
		{
			MbeanNode domainNode = new MbeanNode(domain);
			rootNode.add(domainNode);

			// expand the domain by querying its names
			Set<ObjectName> domainNames = reachMbeanServer.get().queryNames(null,
				new ObjectName(domain + ":*"));
			addDomainsCildrens(domainNode, DataUtil.parseToPropsSet(domainNames));

			// iterating domain names and to set their related objects instance
			Enumeration<DefaultMutableTreeNode> enumeration = domainNode.postorderEnumeration();
			while (enumeration.hasMoreElements())
			{
				DefaultMutableTreeNode node = enumeration.nextElement();
				StringBuilder query = new StringBuilder(domain).append(':');
				TreeNode[] path = node.getPath();
				if (path.length > 2)
				{
					for (int j = 2; j < path.length; j++)
					{
						if (path[j] instanceof MbeanNode)
						{
							query.append(((MbeanNode)path[j]).getKeyValue());
						}
						if (j < path.length - 1)
						{
							query.append(',');
						}
					}
					Set<ObjectInstance> mBeans = reachMbeanServer.get().queryMBeans(null,
						new ObjectName(query.toString()));
					for (ObjectInstance objectInstance : mBeans)
					{
						((MbeanNode)node).setObjectInstance(objectInstance, reachMbeanServer);
					}
				}
			}
		}
		return model;
	}

	/**
	 * @param rootNode
	 * @param domainNames
	 */
	private void addDomainsCildrens(DefaultMutableTreeNode rootNode, Set<Set<String>> domainNames)
	{
		Map<String, Set<Set<String>>> parentProps = new HashMap<String, Set<Set<String>>>();
		for (Set<String> names : domainNames)
		{
			List<String> namesList = new ArrayList<String>(names);
			Collections.sort(namesList, new MBeansTreeNameComparator());
			if (namesList.size() > 0)
			{
				if (parentProps.get(namesList.get(0)) == null)
				{
					parentProps.put(namesList.get(0), new HashSet<Set<String>>());
				}
				names.remove(namesList.get(0));
				parentProps.get(namesList.get(0)).add(names);
			}
		}

		for (Map.Entry<String, Set<Set<String>>> entry : parentProps.entrySet())
		{
			MbeanNode newNode = new MbeanNode(null, entry.getKey());
			rootNode.add(newNode);
			addDomainsCildrens(newNode, entry.getValue());
		}
	}

	private class MBeansTreeNameComparator implements Comparator<String>
	{
		public int compare(String o1, String o2)
		{
			String p1 = o1.split("=")[0];
			String p2 = o2.split("=")[0];
			if ("name".equals(p1))
			{
				return 1;
			}
			else if ("name".equals(p2))
			{
				return -1;
			}
			if ("application".equals(p1) || "type".equals(p1))
			{
				return 1;
			}
			else if ("application".equals(p2) || "type".equals(p2))
			{
				return -1;
			}
			return p1.compareTo(p2);
		}
	}

	private class MbeanNode extends DefaultMutableTreeNode
	{
		private static final long serialVersionUID = 1L;
		protected ObjectInstance objectInstance;
		protected MbeanServerLocator mBeanServerLocator;
		protected String name;
		protected String keyValue;

		public MbeanNode(String domainName)
		{
			super(domainName);
		}

		public MbeanNode(ObjectInstance objectInstance, String keyValue)
		{
			this.objectInstance = objectInstance;
			this.keyValue = keyValue;
			name = keyValue.split("=")[1];
		}

		public MbeanNode(MbeanNode parent)
		{
			objectInstance = parent.objectInstance;
			mBeanServerLocator = parent.mBeanServerLocator;
			name = parent.name;
			keyValue = parent.keyValue;
		}

		public void setObjectInstance(ObjectInstance objectInstance,
			MbeanServerLocator reachMbeanServer) throws InstanceNotFoundException,
			IntrospectionException, ReflectionException
		{
			this.objectInstance = objectInstance;
			mBeanServerLocator = reachMbeanServer;
			MBeanInfo info = reachMbeanServer.get().getMBeanInfo(objectInstance.getObjectName());
			MBeanAttributeInfo[] beanAttributeInfos = info.getAttributes();
			MBeanOperationInfo[] beanOperationInfos = info.getOperations();
			MBeanNotificationInfo[] beanNotificationInfos = info.getNotifications();
			if (beanAttributeInfos.length > 0)
			{
				add(new AttributesNode(this, beanAttributeInfos));
			}
			if (beanOperationInfos.length > 0)
			{
				add(new OperationsNode(this, beanOperationInfos));
			}
			if (beanNotificationInfos.length > 0)
			{
				DefaultMutableTreeNode notificationsNode = new DefaultMutableTreeNode(
					"Notification");
				add(notificationsNode);
				for (MBeanNotificationInfo beanNotificationInfo : beanNotificationInfos)
				{
					notificationsNode.add(new NotificationNode(this, beanNotificationInfo));
				}
			}
		}

		@Override
		public String toString()
		{
			return name != null && !"".equals(name.trim()) ? name : super.toString();
		}

		public String getKeyValue()
		{
			return keyValue;
		}

		public Component getView(String wicketId)
		{
			return new MBeanTree(wicketId, new DefaultTreeModel(this));
		}
	}

	private class AttributesNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;
		private final MBeanAttributeInfo[] beanAttributeInfos;

		public AttributesNode(MbeanNode parent, MBeanAttributeInfo[] beanAttributeInfos)
		{
			super(parent);
			this.beanAttributeInfos = beanAttributeInfos;
			for (MBeanAttributeInfo beanAttributeInfo : beanAttributeInfos)
			{
				add(new AttributeNode(this, beanAttributeInfo));
			}
		}

		@Override
		public Component getView(String id)
		{
			return new AttributeValuesPanel(id, objectInstance.getObjectName(), beanAttributeInfos,
				mBeanServerLocator);
		}

		@Override
		public String toString()
		{
			return "Attributes";
		}
	}

	private class AttributeNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;
		private final MBeanAttributeInfo attributeInfo;

		public AttributeNode(MbeanNode parent, MBeanAttributeInfo mBeanAttributeInfo)
		{
			super(parent);
			attributeInfo = mBeanAttributeInfo;
		}

		@Override
		public Component getView(String wicketId)
		{
			return new AttributeValuesPanel(wicketId, objectInstance.getObjectName(),
				new MBeanAttributeInfo[] { attributeInfo }, mBeanServerLocator);
		}

		@Override
		public String toString()
		{
			return attributeInfo.getName();
		}
	}

	private class OperationsNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;
		private final MBeanOperationInfo[] beanOperationInfos;

		public OperationsNode(MbeanNode parent, MBeanOperationInfo[] beanOperationInfos)
		{
			super(parent);
			this.beanOperationInfos = beanOperationInfos;
			for (MBeanOperationInfo beanOperationInfo : beanOperationInfos)
			{
				add(new OperationNode(this, beanOperationInfo));
			}
		}

		@Override
		public Component getView(String id)
		{
			return new OperationsPanel(id, objectInstance.getObjectName(), beanOperationInfos,
				mBeanServerLocator);
		}

		@Override
		public String toString()
		{
			return "Operations";
		}
	}

	private class OperationNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;
		private final MBeanOperationInfo beanOperationInfo;

		public OperationNode(OperationsNode parent, MBeanOperationInfo mBeanOperationInfo)
		{
			super(parent);
			beanOperationInfo = mBeanOperationInfo;
		}

		@Override
		public Component getView(String wicketId)
		{
			return new OperationsPanel(wicketId, objectInstance.getObjectName(),
				new MBeanOperationInfo[] { beanOperationInfo }, mBeanServerLocator);
		}

		@Override
		public String toString()
		{
			return beanOperationInfo.getName();
		}
	}

	private class NotificationNode extends MbeanNode
	{
		private static final long serialVersionUID = 1L;
		private final MBeanNotificationInfo beanNotificationInfo;

		public NotificationNode(MbeanNode parent, MBeanNotificationInfo mBeanNotificationInfo)
		{
			super(parent);
			beanNotificationInfo = mBeanNotificationInfo;
		}

		@Override
		public String toString()
		{
			return beanNotificationInfo.getName();
		}
	}

	@Override
	public void renderHead(IHeaderResponse res)
	{
		res.render(CssHeaderItem.forReference(CSS));
	}

}
