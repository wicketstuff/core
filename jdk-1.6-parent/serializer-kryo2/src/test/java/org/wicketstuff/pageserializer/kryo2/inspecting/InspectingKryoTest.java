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
		String third = "III";
		stringList.addAll(Arrays.asList("One","2",third,third));
		
		IObjectLabelizer labelizer=new IObjectLabelizer() {
			
			@Override
			public String labelFor(Object object) {
				return null;
			}
		};
		ISerializedObjectTreeProcessor treeProcessor=TreeProcessors.listOf(new TypeSizeReport(),new TreeSizeReport());
		ISerializationListener listener = SerializationListener.listOf(new LoggingSerializationListener(),new AnalyzingSerializationListener(labelizer, treeProcessor));
		InspectingKryoSerializer kryo=new InspectingKryoSerializer(Bytes.bytes(100),listener);
		
		byte[] data=kryo.serialize(stringList);
		
		Assert.assertNotNull(data);
		
		List<String> readBack=(List<String>) kryo.deserialize(data);
		
		Assert.assertNotNull(readBack);
		Assert.assertEquals(stringList, readBack);
	}
}
