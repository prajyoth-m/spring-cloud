package com.example.department.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.department.beans.Departments;
import com.example.department.beans.Users;
import com.example.department.repository.DepartmentRepository;
import com.example.department.repository.UserClient;

@RestController
public class DepartmentController {

	@Autowired
	DepartmentRepository deptRepo;
	@Autowired
	UserClient userClient;

	@GetMapping("/dept/userDepartment/{department}")
	public Users getUserByDepartment(@PathVariable String department) {
		Users user = new Users();
		Departments dept = deptRepo.findById(department).orElse(null);
		if (null != dept) {
			user = userClient.getUserByName(dept.getUsername());
			user.setDepartment(dept.getDepartment());
		}
		return user;
	}
}
