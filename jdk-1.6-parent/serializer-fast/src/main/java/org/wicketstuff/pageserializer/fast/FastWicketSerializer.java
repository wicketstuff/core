/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.fast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;

import de.ruedigermoeller.serialization.FSTConfiguration;
import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;


public class FastWicketSerializer implements ISerializer{

	/**
	 * The size of the {@link ByteBuffer} that is used to hold the serialized page
	 */
	private static final Bytes DEFAULT_BUFFER_SIZE = Bytes.megabytes(10L);

	private final Bytes bufferSize;
	
	static final FSTConfiguration fastSerialConfig = FSTConfiguration.createDefaultConfiguration();
	
	public FastWicketSerializer(Bytes bufferSize) {
		this.bufferSize = Args.notNull(bufferSize, "bufferSize");
	}


	
	@Override
	public byte[] serialize(Object object) {
		
    try {
  		ByteArrayOutputStream buffer = new ByteArrayOutputStream((int) this.bufferSize.bytes());
    	FSTObjectOutput out = fastSerialConfig.getObjectOutput(buffer);
			out.writeObject(object);
	    // DON'T out.close() when using factory method;
	    out.flush();
	    buffer.close();
			return buffer.toByteArray();
    } catch (RuntimeException e) {
    	throw new FastWicketSerialException("serialize",e);
		} catch (IOException e) {
			throw new FastWicketSerialException("serialize",e);
		}
	}

	@Override
	public Object deserialize(byte[] data) {
		try {
			ByteArrayInputStream buffer=new ByteArrayInputStream(data);
			
	    FSTObjectInput in = fastSerialConfig.getObjectInput(buffer);
	    Object result = in.readObject();
	    // DON'T: in.close(); here prevents reuse and will result in an exception      
	    buffer.close();
	    return result;
	    
		} catch (IOException e) {
			throw new FastWicketSerialException("deserialize", e);
		} catch (ClassNotFoundException e) {
			throw new FastWicketSerialException("deserialize", e);
		}
	}

}
