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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ObjectViewPanel extends Panel {
    public static class PropValue implements Serializable {
	String property;
	Object value;
    }

    public ObjectViewPanel(String id, Object object) {
	super(id);
	add(new Label("toString", object.toString()));
	ArrayList properties = new ArrayList();
	Method[] methods = object.getClass().getMethods();
	for (int i = 0; i < methods.length; i++) {
	    if (methods[i].getName().startsWith("get")
		    && methods[i].getParameterTypes().length == 0) {
		PropValue prop = new PropValue();
		try {
		    prop.property = methods[i].getName();
		    prop.value = methods[i].invoke(object, (Object[]) null);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		properties.add(prop);
	    }
	}
	add(new ListView<PropValue>("properties", properties) {
	    @Override
	    protected void populateItem(ListItem<PropValue> item) {
		item.add(new Label("property", item.getModelObject().property));
		item.add(new Label("value", item.getModelObject().value == null ? null : item
			.getModelObject().value.toString()));
	    }
	});
    }
}
