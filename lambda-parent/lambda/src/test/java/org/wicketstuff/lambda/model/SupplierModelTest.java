package org.wicketstuff.lambda.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Test;


public class SupplierModelTest {

	@Test
	public void testNotNullBoolean() {
		assertEquals(true, new SupplierModel<>(() -> true).getObject());
	}
	
	@Test
	public void testNotNullString() {
		assertEquals("test", new SupplierModel<>(() -> "test").getObject());
	}

	@Test
	public void testNotNullInteger() {
		assertEquals((Integer)3, new SupplierModel<>(() -> 3).getObject());
	}
	
	@Test
	public void testNullObject() {
		assertNull(new SupplierModel<>(() -> null).getObject());
	}
	
	@Test
	public void testCache() {
		Person p = new Person();
		p.setName("A");
		IModel<String> m = new SupplierModel<>(() -> p.getName());
		Assert.assertEquals("A", m.getObject());
		p.setName("B");
		Assert.assertEquals("A", m.getObject());
		m.detach();
		Assert.assertEquals("B", m.getObject());
	}
	
}
