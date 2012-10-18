package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class InspectingKryoTest {

	@Test
	public void writeStringList() {
		List<String> stringList=new ArrayList<String>();
		stringList.addAll(Arrays.asList("One","2","III"));
		
		InspectingKryo kryo=new InspectingKryo(new LoggingSerializationListener());
		
		Output buffer = new Output(100);
		kryo.writeClassAndObject(buffer, stringList);
		byte[] data=buffer.toBytes();
		buffer.clear();
		System.runFinalization();
		
		Assert.assertNotNull(data);
		
		List<String> readBack=(List<String>) kryo.readClassAndObject(new Input(data));
		
		Assert.assertNotNull(readBack);
		Assert.assertEquals(stringList, readBack);
	}
}
