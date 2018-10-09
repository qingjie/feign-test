package com.test.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.client.User;
import com.test.client.UserClient;

@RestController
public class APIController {

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private FeignClientProperties properties;


	@PostMapping(value = "/usersAsOptional")
	public Optional<User> createUserAsOptional(@RequestBody User user) {
	
		return userClient.createUserAsOptional(user);
	}
	
	@PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
    
    	return userClient.createUser(user);
    }
	
	@PostMapping(value = "/usersAsRE")
    public ResponseEntity<User> createUserAsRE(@RequestBody User user) {
    
    	return userClient.createUserAsRE(user);
    }
	
	@GetMapping("/errorCase")
	public User errorCase() {
		return userClient.errorCase();
	}
    
    @GetMapping(value = "/config")
    public FeignClientProperties feignConfig() {
    	return properties;
    }
}
