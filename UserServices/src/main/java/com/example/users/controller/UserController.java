package com.example.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.beans.Users;
import com.example.users.beans.ValidateUser;
import com.example.users.repository.UserRepository;


@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;

	@GetMapping("/user/{username}")
	public Users getUserByName(@PathVariable String username) {
		return userRepo.findById(username).orElse(null);
	}

	@PostMapping("/user/validate")
	public ValidateUser validateUser(@RequestBody Users userData) {
		ValidateUser valid = new ValidateUser();
		Users user = userRepo.findById(userData.getUsername()).orElse(null);
		valid.setUsername(userData.getUsername());
		if (user != null) {
			valid.setPasswordAccepted(userData.getPassword().equals(user.getPassword()));
		} else {
			valid.setPasswordAccepted(Boolean.FALSE);
		}
		return valid;
	}

}