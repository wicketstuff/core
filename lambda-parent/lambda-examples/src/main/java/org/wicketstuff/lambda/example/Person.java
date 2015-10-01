package org.wicketstuff.lambda.example;

import static java.util.Optional.ofNullable;
import static org.wicketstuff.lambda.example.Address.GETTER_CITY;
import static org.wicketstuff.lambda.example.Address.GETTER_STREET;

import java.io.Serializable;
import java.util.Optional;

import org.wicketstuff.lambda.SerializableBiConsumer;
import org.wicketstuff.lambda.SerializableFunction;

/**
 * Basic information about a person: a name and an address.
 */
public class Person implements Serializable {
	
	/**
	 * {@link Function} that returns a {@link Person}'s {@link Optional} {@code street} {@link Address}.
	 */
	public static final SerializableFunction<Person, Optional<String>> GETTER_OP_ADDRESS_STREET = person -> 
		ofNullable(person).flatMap(x -> ofNullable(x.getAddress())).flatMap(GETTER_STREET);
		
	/**
	 * {@link Function} that returns a {@link Person}'s {@link Optional} {@code city} {@link Address}. 
	 */
	public static final SerializableFunction<Person, Optional<String>> GETTER_OP_ADDRESS_CITY = person -> 
		ofNullable(person).flatMap(x -> ofNullable(x.getAddress())).flatMap(GETTER_CITY);
		
	/**
	 * {@link Function} that returns a {@link Person}'s {@link Optional} {@code name}. 
	*/
	public static final SerializableFunction<Person, Optional<String>> GETTER_OP_NAME = person -> 
		ofNullable(person).flatMap(x -> ofNullable(x.getName()));
		
	/**
	 * {@link BiConsumer} to set a {@link Person}'s name.
	 */
	public static final SerializableBiConsumer<Person, String> SETTER_NAME = (person, name) -> 
		person.setName(name);
		
	/**
	 * {@link Function} that returns the {@link Address}, creating it as necessary.
	 */
	public static final SerializableFunction<Person, Address> GETTER_ADDRESS = person -> { 
		if (person.getAddress() == null) {
			person.setAddress(new Address());
		}
		return person.getAddress();
	};
	
	/**
	 * {@link BiConsumer} to set a {@link Person}'s {@code street} {@link Address}, creating the {@link Address} as necessary.
	 */
	public static final SerializableBiConsumer<Person, String> SETTER_ADDRESS_STREET = (person, street) -> 
		GETTER_ADDRESS.apply(person).setStreet(street);
		
	/**
	 * {@link BiConsumer} to set a {@link Person}'s {@code city} {@link Address}, creating the {@link Address} as necessary.
	 */
	public static final SerializableBiConsumer<Person, String> SETTER_ADDRESS_CITY = (person, city) -> 
		GETTER_ADDRESS.apply(person).setCity(city);
	
	private String name;
	private Address address;
	
	public Person() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
