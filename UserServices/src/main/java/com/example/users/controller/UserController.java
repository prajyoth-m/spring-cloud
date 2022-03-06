package com.example.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.beans.BaseResponse;
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
	
	@GetMapping("/users/{department}")
	public List<Users> getUsersByDept(@PathVariable String department){
		return userRepo.findByDepartment(department);
	}

	@PostMapping("/user/validate")
	public ValidateUser validateUser(@RequestBody Users userData) {
		ValidateUser valid = new ValidateUser();
		Users user = userRepo.findById(userData.getUsername()).orElse(null);
		valid.setUsername(userData.getUsername());
		if (user != null) {
			valid.setPasswordAccepted(userData.getPassword().equals(user.getPassword()));
			valid.setDepartment(user.getDepartment());
			valid.setUsername(user.getUsername());
		} else {
			valid.setPasswordAccepted(Boolean.FALSE);
		}
		return valid;
	}

	@PostMapping("/user")
	public BaseResponse insertUser(@RequestBody Users user) {
		BaseResponse resp = new BaseResponse();
		Users savedUser = userRepo.save(user);
		if (null != savedUser) {
			resp.setSuccess(true);
			resp.setUsername(savedUser.getUsername());
		} else {
			resp.setSuccess(false);
		}
		return resp;
	}
	
	@DeleteMapping("/user/{username}")
	public BaseResponse deleteUser(@PathVariable String username) {
		BaseResponse resp = new BaseResponse();
		userRepo.deleteById(username);
		resp.setSuccess(true);
		resp.setUsername(username);
		return resp; 
	}
	
}
