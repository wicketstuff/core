package com.example.wicket;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	public String greet() {
		return "Hello from a Spring bean";
	}
}
