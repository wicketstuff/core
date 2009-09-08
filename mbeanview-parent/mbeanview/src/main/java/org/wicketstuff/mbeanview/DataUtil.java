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

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanParameterInfo;

/**
 * @author Pedro Henrique Oliveira dos Santos
 *
 */
public class DataUtil {

    public static Object tryParseToType(Object object, Class clazz) {
	try {
	    return clazz.getConstructor(new Class[] { String.class })
		    .newInstance(object.toString());
	} catch (Exception e) {
	    return object;
	}
    }

    public static Class getClassFromInfo(MBeanAttributeInfo attributeInfo)
	    throws ClassNotFoundException {
	return getClassFromInfo(attributeInfo.getType());
    }

    public static Class getClassFromInfo(String jmxType) throws ClassNotFoundException {
	Class clazz = null;
	if ("boolean".equals(jmxType)) {
	    clazz = Boolean.class;
	} else if ("int".equals(jmxType)) {
	    clazz = Integer.class;
	} else if ("double".equals(jmxType)) {
	    clazz = Double.class;
	} else if ("long".equals(jmxType)) {
	    clazz = Long.class;
	} else {
	    clazz = Class.forName(jmxType);
	}
	return clazz;
    }

    public static Object getCompatibleData(Object object, MBeanParameterInfo beanParameterInfo)
	    throws ClassNotFoundException {
	return tryParseToType(object, getClassFromInfo(beanParameterInfo.getType()));
    }
}
