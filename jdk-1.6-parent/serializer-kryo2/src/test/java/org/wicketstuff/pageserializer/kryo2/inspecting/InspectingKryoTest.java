package org.wicketstuff.pageserializer.kryo2.inspecting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.wicket.util.lang.Bytes;
import org.junit.Test;

public class InspectingKryoTest {

	@Test
	public void writeStringList() {
		List<String> stringList=new ArrayList<String>();
		stringList.addAll(Arrays.asList("One","2","III"));
		
		InspectionKryoSerializer kryo=new InspectionKryoSerializer(Bytes.bytes(100),new LoggingSerializationListener());
		
		byte[] data=kryo.serialize(stringList);
		
		Assert.assertNotNull(data);
		
		List<String> readBack=(List<String>) kryo.deserialize(data);
		
		Assert.assertNotNull(readBack);
		Assert.assertEquals(stringList, readBack);
	}
}
