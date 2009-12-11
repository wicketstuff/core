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

package org.wicketstuff.datatable_autocomplete.data;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import junit.framework.TestCase;

/**
 * @author mocleiri
 */

public class TestClassLoaderExtractor extends TestCase {

	/**
	 * 
	 */
	public TestClassLoaderExtractor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public TestClassLoaderExtractor(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void testGetDetailsFromClassLoader() {

		System.out.print("in method");

		Properties p = System.getProperties();

		Enumeration<Object> e = p.keys();

		while (e.hasMoreElements()) {
			Object key = e.nextElement();

			Object value = p.get(key);

			System.out.println(key + " = " + value);

		}

	}

	public void testLoadAllClassNamesFromBootClass() throws IOException {

		String path = System.getProperty("sun.boot.class.path");

		String[] parts = path.split(":");

		String targetPart = null;

		for (String p : parts) {

			if (p.endsWith("rt.jar")) {
				targetPart = p;
				break;
			}
		}

		JarFile jar = new JarFile(targetPart);
		
		Enumeration<JarEntry> entries = jar.entries();

		while (entries.hasMoreElements()) {
			
			JarEntry jarEntry = (JarEntry) entries.nextElement();

			if (!jarEntry.getName().contains(".class"))
				continue;
			
			Class clazz;

			try {

				String className = jarEntry.getName().replace("/", ".")
						.replace(".class", "");

				System.out.println(className);

				getClass().getClassLoader().loadClass(className);

				clazz = ClassLoader.getSystemClassLoader().loadClass(className);

				Method[] m = clazz.getDeclaredMethods();

				for (Method method : m) {

					String parameterList = getParameters(method);

					System.out.println(clazz.getCanonicalName() + " "
							+ method.getName() + " ( " + parameterList + " )");
				}

			} catch (ClassNotFoundException e) {
				// continue
			}

		}
	}

	private String getParameters(Method method) {

		StringBuffer buffer = new StringBuffer();

		Class<?>[] types = method.getParameterTypes();

		if (types.length == 0)
			return " ";
		
		int number = 1;

		for (int i = 0; i < types.length - 1; i++) {
			Class<?> class1 = types[i];

			buffer.append(class1.getName());
			buffer.append(" var");
			buffer.append(number);
			buffer.append(", ");
			number++;

		}
		buffer.append(types[types.length - 1].getName());
		buffer.append(" var");
		buffer.append(number);

		return buffer.toString();
	}
}
