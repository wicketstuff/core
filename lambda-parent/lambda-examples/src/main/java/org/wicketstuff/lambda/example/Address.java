package org.wicketstuff.lambda.example;

import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.wicketstuff.lambda.SerializableFunction;

/**
 * Basic information about an address: street and city.
 */
public class Address {

	/**
	 * {@link Function} that returns the {@link Address}'s {@link Optional} street.
	 */
	public static final SerializableFunction<Address, Optional<String>> GETTER_STREET = a -> 
		ofNullable(a).flatMap(x -> ofNullable(x.getStreet()));

	/**
	 * {@link Function} that returns the {@link Address}'s {@link Optional} city.
	 */
	public static final SerializableFunction<Address, Optional<String>> GETTER_CITY = a -> 
		ofNullable(a).flatMap(x -> ofNullable(x.getCity()));
		
	private String street;
	private String city;
	
	public Address() {
		super();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
