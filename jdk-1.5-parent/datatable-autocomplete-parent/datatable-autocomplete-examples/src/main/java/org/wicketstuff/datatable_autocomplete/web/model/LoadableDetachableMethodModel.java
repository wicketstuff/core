/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.wicketstuff.datatable_autocomplete.web.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;

import org.apache.wicket.model.IModel;

/**
 * @author mocleirie so this model is used to load it back from
 *         the class loader as needed.
 * 
 */
public class LoadableDetachableMethodModel implements IModel<Method> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4988788420618189675L;

	private String className = null;
	private String methodName = null;
	private LinkedList<String> methodParameterTypeList = new LinkedList<String>();

	private transient Method transientMethod = null;

	private transient boolean attached = false;

	/**
	 * @param object
	 */
	public LoadableDetachableMethodModel(Method object) {

		setObject(object);
		
	}

	
	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IModel#getObject()
	 */
	public Method getObject() {
		
		if (attached)
			return transientMethod;
		
		else {
			transientMethod = load();
			attached = true;
			
			return transientMethod;
		}
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
	 */
	public void setObject(Method object) {
		
		this.attached = true;
		this.transientMethod = object;
		
		if (object != null) {
			this.className = object.getDeclaringClass().getName();
			this.methodName = object.getName();


		}
		
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IDetachable#detach()
	 */
	public void detach() {
		
		this.attached = false;
		this.transientMethod = null;
		
	}


	private Method load() {

		if (this.className == null)
			return null;

		Method m = null;

		try {

			Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(
					this.className);


			for (Method method : clazz.getDeclaredMethods()) {
				
				if (method.getName().trim().equals(this.methodName.trim())) {
					m = method;
					break;
				}
			}


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// fall through

		} catch (SecurityException e) {
			e.printStackTrace();
			// fall through
		}
		return m;
	}

}
