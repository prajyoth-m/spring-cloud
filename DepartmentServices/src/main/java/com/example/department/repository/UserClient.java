package com.example.department.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.department.beans.Users;

@FeignClient(value = "user-services")
public interface UserClient {
	@GetMapping("/user/{username}")
	Users getUserByName(@PathVariable String username);

	default String fallback_fail() {
		return "User services is under maintenance! Please try after some time!";
	};
}
