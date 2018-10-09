package com.test.producer;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/rest")
public class RestResource {

	/**
	 * Randomly sends error or success
	 * @return
	 */
	@GetMapping("/errorCase")
	public User errorCase() {
		if (RandomUtils.nextBoolean()) {

			return new User("123", "Test", "User");
		}
		throw new CustomException("dummy bad request", "failed", HttpStatus.BAD_REQUEST);
	}

	@HystrixCommand(commandKey = "createUser", groupKey = "hello11")
	@PostMapping("/api/users")
	public User createUser(@RequestBody User user) {
		System.out.println("---hello-- " + user.getFirstName());
		return user;
	}
}
