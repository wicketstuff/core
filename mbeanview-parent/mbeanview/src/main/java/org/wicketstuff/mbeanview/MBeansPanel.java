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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class MBeansPanel extends Panel implements IHeaderContributor {
    public static final String VIEW_PANEL_ID = "view";
    private static final ResourceReference CSS = new ResourceReference(MBeansPanel.class,
	    "css/MBeansPanel.css");

    public MBeansPanel(String id) {
	super(id);
	try {
	    MbeanServerLocator reachMbeanServer = new MbeanServerLocator() {
		public MBeanServer get() {
		    return ManagementFactory.getPlatformMBeanServer();
		}
	    };
	    MBeanTree mBeansTree = new MBeanTree("mBeansTree", getTreeModel(reachMbeanServer));
	    add(mBeansTree);
	    add(new EmptyPanel("view"));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private class MBeanTree extends Tree {
	public MBeanTree(String id, TreeModel model) {
	    super(id, model);
	    getTreeState().expandNode(getModelObject().getRoot());
	}

	@Override
	protected ResourceReference getCSS() {
	    return CSS;
	}

	@Override
	protected void onNodeLinkClicked(AjaxRequestTarget target, TreeNode node) {
	    if (node instanceof MbeanNode) {
		MBeansPanel.this.replace(((MbeanNode) node).getView(VIEW_PANEL_ID));
		setResponsePage(MBeansPanel.this.getPage());
	    }
	}

	@Override
	protected Component newNodeIcon(MarkupContainer parent, String id, TreeNode node) {
	    if (node instanceof DefaultMutableTreeNode) {
		DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode) node;
		if (mutableNode.getChildCount() > 0
			&& ((mutableNode.getChildAt(0) instanceof AttributeNode)
				|| (mutableNode.getChildAt(0) instanceof OperationNode) || (mutableNode
				.getChildAt(0) instanceof NotificationNode))) {
		    return new EmptyPanel(id).add(new SimpleAttributeModifier("style", "width:0;"));
		}
	    }
	    return super.newNodeIcon(parent, id, node);
	}
    }

    private TreeModel getTreeModel(MbeanServerLocator reachMbeanServer)
	    throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException,
	    IntrospectionException, ReflectionException {
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
	TreeModel model = new DefaultTreeModel(rootNode);
	String[] domains = reachMbeanServer.get().getDomains();
	for (int i = 0; i < domains.length; i++) {
	    MbeanNode domainNode = new MbeanNode(domains[i]);
	    rootNode.add(domainNode);
	    Set<ObjectName> domainNames = reachMbeanServer.get().queryNames(null,
		    new ObjectName(domains[i] + ":*"));
	    addDomainsCildrens(domainNode, parseToPropsSet(domainNames));
	    Enumeration<DefaultMutableTreeNode> enumeration = domainNode.postorderEnumeration();
	    Set<DefaultMutableTreeNode> nodes = new HashSet<DefaultMutableTreeNode>();
	    while (enumeration.hasMoreElements()) {
		nodes.add(enumeration.nextElement());
	    }
	    for (DefaultMutableTreeNode node : nodes) {
		String query = domains[i] + ":";
		TreeNode[] path = node.getPath();
		if (path.length > 2) {
		    for (int j = 2; j < path.length; j++) {
			if (path[j] instanceof MbeanNode) {
			    query += ((MbeanNode) path[j]).getKeyValue();
			}
			if (j < path.length - 1) {
			    query += ",";
			}
		    }
		    Set<ObjectInstance> mBeans = reachMbeanServer.get().queryMBeans(null,
			    new ObjectName(query));
		    if (mBeans.size() > 0) {
			for (Iterator it = mBeans.iterator(); it.hasNext();) {
			    ObjectInstance objectInstance = (ObjectInstance) it.next();
			    ((MbeanNode) node).setObjectInstance(objectInstance, reachMbeanServer);
			}
		    }
		}
	    }
	}
	return model;
    }

    private void addDomainsCildrens(DefaultMutableTreeNode rootNode, Set<Set<String>> domainNames) {
	Map<String, Set<Set<String>>> parentProps = new HashMap<String, Set<Set<String>>>();
	for (Set<String> names : domainNames) {
	    List<String> namesList = new ArrayList(names);
	    Collections.sort(namesList, new Comparator<String>() {
		public int compare(String o1, String o2) {
		    String p1 = o1.split("=")[0];
		    String p2 = o2.split("=")[0];
		    if ("name".equals(p1)) {
			return 1;
		    } else if ("name".equals(p2)) {
			return -1;
		    }
		    if ("application".equals(p1) || "type".equals(p1)) {
			return 1;
		    } else if ("application".equals(p2) || "type".equals(p2)) {
			return -1;
		    }
		    return p1.compareTo(p2);
		}
	    });
	    if (namesList.size() > 0) {
		if (parentProps.get(namesList.get(0)) == null) {
		    parentProps.put(namesList.get(0), new HashSet<Set<String>>());
		}
		names.remove(namesList.get(0));
		parentProps.get(namesList.get(0)).add(names);
	    }
	}

	for (Iterator i = parentProps.keySet().iterator(); i.hasNext();) {
	    String parentProp = (String) i.next();
	    MbeanNode newNode = new MbeanNode(null, parentProp);
	    rootNode.add(newNode);
	    addDomainsCildrens(newNode, parentProps.get(parentProp));
	}

    }

    private class MbeanNode extends DefaultMutableTreeNode {
	protected ObjectInstance objectInstance;
	protected MbeanServerLocator mBeanServerLocator;
	protected String name;
	protected String keyValue;

	public MbeanNode() {
	}

	public MbeanNode(String domainName) {
	    super(domainName);
	}

	public MbeanNode(ObjectInstance objectInstance, String keyValue) {
	    this.objectInstance = objectInstance;
	    this.keyValue = keyValue;
	    name = keyValue.split("=")[1];
	}

	public MbeanNode(ObjectInstance objectInstance, MbeanServerLocator mbeanServerLocator) {
	    this.objectInstance = objectInstance;
	    this.mBeanServerLocator = mbeanServerLocator;
	}

	public void setObjectInstance(ObjectInstance objectInstance,
		MbeanServerLocator reachMbeanServer) throws InstanceNotFoundException,
		IntrospectionException, ReflectionException {
	    this.objectInstance = objectInstance;
	    MBeanInfo info = reachMbeanServer.get().getMBeanInfo(objectInstance.getObjectName());
	    MBeanAttributeInfo[] beanAttributeInfos = info.getAttributes();
	    MBeanOperationInfo[] beanOperationInfos = info.getOperations();
	    MBeanNotificationInfo[] beanNotificationInfos = info.getNotifications();
	    if (beanAttributeInfos.length > 0) {
		add(new AttributesNode(beanAttributeInfos, objectInstance, reachMbeanServer));
	    }
	    if (beanOperationInfos.length > 0) {
		add(new OperationsNode(beanOperationInfos, objectInstance, reachMbeanServer));
	    }
	    if (beanNotificationInfos.length > 0) {
		DefaultMutableTreeNode notificationsNode = new DefaultMutableTreeNode(
			"Notification");
		add(notificationsNode);
		for (int i = 0; i < beanNotificationInfos.length; i++) {
		    notificationsNode.add(new NotificationNode(beanNotificationInfos[i],
			    reachMbeanServer));
		}
	    }
	}

	@Override
	public String toString() {
	    return name != null && !"".equals(name.trim()) ? name : super.toString();
	}

	public String getKeyValue() {
	    return keyValue;
	}

	public Component getView(String wicketId) {
	    return new MBeanTree(wicketId, new DefaultTreeModel(this));
	}
    }

    private class AttributesNode extends MbeanNode {
	private MBeanAttributeInfo[] beanAttributeInfos;

	public AttributesNode(MBeanAttributeInfo[] beanAttributeInfos,
		ObjectInstance objectInstance, MbeanServerLocator mbeanServerLocator) {
	    super(objectInstance, mbeanServerLocator);
	    this.beanAttributeInfos = beanAttributeInfos;
	    for (int i = 0; i < beanAttributeInfos.length; i++) {
		add(new AttributeNode(beanAttributeInfos[i], mBeanServerLocator));
	    }
	}

	@Override
	public Component getView(String id) {
	    return new AttributeValuesPanel(id, objectInstance.getObjectName(), beanAttributeInfos,
		    mBeanServerLocator);
	}

	@Override
	public String toString() {
	    return "Attributes";
	}
    }

    private class AttributeNode extends MbeanNode {
	private MBeanAttributeInfo attributeInfo;

	public AttributeNode(MBeanAttributeInfo attributeInfo, MbeanServerLocator reachMbeanServer) {
	    this.attributeInfo = attributeInfo;
	    this.mBeanServerLocator = reachMbeanServer;
	}

	@Override
	public String toString() {
	    return attributeInfo.getName();
	}
    }

    private class OperationsNode extends MbeanNode {
	private MBeanOperationInfo[] beanOperationInfos;

	public OperationsNode(MBeanOperationInfo[] beanOperationInfos,
		ObjectInstance objectInstance, MbeanServerLocator mbeanServerLocator) {
	    super(objectInstance, mbeanServerLocator);
	    this.beanOperationInfos = beanOperationInfos;
	    for (int i = 0; i < beanOperationInfos.length; i++) {
		add(new OperationNode(beanOperationInfos[i], mBeanServerLocator));
	    }
	}

	@Override
	public Component getView(String id) {
	    return new OperationsPanel(id, objectInstance.getObjectName(), beanOperationInfos,
		    mBeanServerLocator);
	}

	@Override
	public String toString() {
	    return "Operations";
	}
    }

    private class OperationNode extends MbeanNode {
	private MBeanOperationInfo beanOperationInfo;

	public OperationNode(MBeanOperationInfo beanOperationInfo,
		MbeanServerLocator reachMbeanServer) {
	    this.beanOperationInfo = beanOperationInfo;
	    this.mBeanServerLocator = reachMbeanServer;
	}

	@Override
	public String toString() {
	    return beanOperationInfo.getName();
	}
    }

    private class NotificationNode extends MbeanNode {
	private MBeanNotificationInfo beanNotificationInfo;

	public NotificationNode(MBeanNotificationInfo beanNotificationInfo,
		MbeanServerLocator reachMbeanServer) {
	    this.beanNotificationInfo = beanNotificationInfo;
	    this.mBeanServerLocator = reachMbeanServer;
	}

	@Override
	public String toString() {
	    return beanNotificationInfo.getName();
	}
    }

    private static Set<Set<String>> parseToPropsSet(Set<ObjectName> domainNames) {
	Set result = new HashSet();
	for (Iterator i = domainNames.iterator(); i.hasNext();) {
	    ObjectName names = (ObjectName) i.next();
	    Set<String> propValue = new HashSet<String>();
	    for (Iterator it = names.getKeyPropertyList().entrySet().iterator(); it.hasNext();) {
		Entry<String, String> entry = (Entry<String, String>) it.next();
		propValue.add(entry.getKey() + "=" + entry.getValue());
	    }
	    result.add(propValue);
	}
	return result;
    }

    public void renderHead(IHeaderResponse res) {
	res.renderCSSReference(CSS);
    }

}
