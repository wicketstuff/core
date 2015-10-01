package org.wicketstuff.lambda.model;

import static java.util.Optional.ofNullable;

import java.io.Serializable;
import java.util.Optional;

import org.wicketstuff.lambda.SerializableBiConsumer;
import org.wicketstuff.lambda.SerializableFunction;

public class Person implements Serializable {

	public static final SerializableFunction<Person, Optional<String>> NAME = 
			p -> ofNullable(p)
			.flatMap(x -> ofNullable(x.getName()));
			
	public static final SerializableFunction<Person, Optional<String>> BOSS_NAME = 
			p -> ofNullable(p)
				.flatMap(x -> ofNullable(x.getBoss()))
				.flatMap(x -> ofNullable(x.getName()));
			
	public static final SerializableBiConsumer<Person, String> BOSS_NAME_SETTER =
			(p, n) -> { 
				Person boss = p.getBoss();
				if (boss == null) {
					boss = new Person();
					p.setBoss(boss);
				}
				boss.setName(n);
			};
	
	private String name;
	private Person boss;
	
	public Person() {
		this(null);
	}
	
	public Person(String name) {
		this(name, null);
	}
	
	public Person(String name, Person boss) {
		super();
		this.name = name;
		this.boss = boss;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Person getBoss() {
		return boss;
	}
	
	public void setBoss(Person boss) {
		this.boss = boss;
	}
	
}
