package org.wicketstuff.springboot.starter.example;

import org.springframework.stereotype.Service;

/**
 * A simple Spring-managed service.
 */
@Service
public class SpringService {

	public String getMessage() {
		return "Hello from Spring Service injection!";
	}
}
