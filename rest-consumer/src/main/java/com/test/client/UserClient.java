package com.test.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import feign.jackson.JacksonEncoder;

@FeignClient(url = "http://localhost:8092/rest", name = "USER-CLIENT")
public interface UserClient {

	@PostMapping(
			value = "/api/users",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<User> createUserAsRE(User user);

	/**
	 * {@link JacksonEncoder} fails if produces / consumes are absent. Default
	 * configuration handles it.
	 * 
	 */
	@PostMapping(value = "/api/users")
	Optional<User> createUserAsOptional(User user);

	@PostMapping(value = "/api/users")
	User createUser(User user);

	@GetMapping("/errorCase")
	public User errorCase();
}
 