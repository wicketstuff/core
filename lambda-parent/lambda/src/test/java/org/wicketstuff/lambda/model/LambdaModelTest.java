package org.wicketstuff.lambda.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;
import org.wicketstuff.lambda.OptionalFunction;

public class LambdaModelTest {
	
	@Test
	public void testGetter() {
		Person person = new Person();
		person.setName("A");
		IModel<String> nameModel = new LambdaModel<>(Model.of(person), Person::getName);
		assertEquals("A", nameModel.getObject());
	}

	@Test(expected = NullPointerException.class)
	public void testUnhandledNullModelObject() {
		IModel<String> nameModel = new LambdaModel<>(Model.of((Person)null), Person::getName);
		nameModel.getObject();
	}
	
	@Test
	public void testHandledNullModelObject() {
		IModel<String> nameModel = new LambdaModel<>(Model.of((Person)null), new OptionalFunction<>(Person.NAME));
		assertNull(nameModel.getObject());
	}
	
	@Test
	public void testNullDefaultValueModelObject() {
		IModel<String> nameModel = new LambdaModel<>(Model.of((Person)null), new OptionalFunction<>(Person.NAME, () -> "unknown"));
		assertEquals("unknown", nameModel.getObject());
	}
	
	@Test
	public void testNestedGetter() {
		Person person = new Person();
		person.setName("A");
		Person boss = new Person();
		boss.setName("B");
		person.setBoss(boss);
		IModel<String> bossNameModel = new LambdaModel<>(Model.of(person), new OptionalFunction<>(Person.BOSS_NAME));
		assertEquals("B", bossNameModel.getObject());
	}
	
	@Test
	public void testNullableParentGetter() {
		Person person = new Person();
		IModel<String> bossNameModel = new LambdaModel<>(Model.of(person), new OptionalFunction<>(Person.BOSS_NAME));
		assertNull(bossNameModel.getObject());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testNoSetter() {
		Person person = new Person();
		IModel<String> nameModel = new LambdaModel<>(Model.of(person), new OptionalFunction<>(Person.BOSS_NAME));
		nameModel.setObject("A");
	}

	@Test
	public void testWithSetter() {
		Person person = new Person();
		IModel<String> nameModel = new LambdaModel<>(Model.of(person), new OptionalFunction<>(Person.BOSS_NAME), Person::setName);
		nameModel.setObject("A");
		assertEquals("A", person.getName());
	}

	@Test(expected = NullPointerException.class)
	public void testUnhandledNullParentSetter() {
		Person person = new Person();
		IModel<String> bossNameModel = new LambdaModel<>(Model.of(person), new OptionalFunction<>(Person.BOSS_NAME), (p, n) -> p.getBoss().setName(n));
		bossNameModel.setObject("B");
	}

	@Test
	public void testHandledNullParentSetter() {
		Person person = new Person();
		IModel<String> bossNameModel = new LambdaModel<>(Model.of(person), new OptionalFunction<>(Person.BOSS_NAME), Person.BOSS_NAME_SETTER);
		bossNameModel.setObject("B");
		assertEquals("B", person.getBoss().getName());
	}
	
}
