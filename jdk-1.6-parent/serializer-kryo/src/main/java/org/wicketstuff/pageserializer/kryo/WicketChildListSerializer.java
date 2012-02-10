/*
 * Copyright 2010 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.wicketstuff.pageserializer.kryo;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.serialize.ISerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serialize.FieldSerializer;

/**
 * A reworked WicketChildListSerializerFactory from <a
 * href="https://github.com/magro/memcached-session-manager">memcached-session-manager</a> project
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
public class WicketChildListSerializer extends FieldSerializer
{


	private static final String SERIALIZED_CLASS_NAME = MarkupContainer.class.getName() +
		"$ChildList";
	public static Class<?> CLASS = null;
	static
	{
		try
		{
			CLASS = Class.forName(SERIALIZED_CLASS_NAME, false, ISerializer.class.getClassLoader());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new instances.
	 * 
	 * @param kryo
	 *            the kryo instance that must be provided.
	 */
	public WicketChildListSerializer(final Kryo kryo)
	{
		super(kryo, CLASS);
	}

}