package com.example.department.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.department.beans.BaseResponse;
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
	public List<Users> getUserByDepartment(@PathVariable String department) {
		List<Users> users = new ArrayList<>();
		Departments dept = deptRepo.findById(department).orElse(null);
		if (null != dept) {
			users = userClient.getUsersByDept(department);
		}
		return users;
	}

	@PostMapping("/department")
	public BaseResponse insertDepartment(@RequestBody Departments dept) {
		BaseResponse resp = new BaseResponse();
		dept.setUsers(0);
		Departments savedDept = deptRepo.save(dept);
		if (null != savedDept) {
			resp.setSuccess(true);
			resp.setDepartment(savedDept.getDepartment());
		} else {
			resp.setSuccess(false);
		}
		return resp;
	}
	
	@GetMapping("/department")
	public List<Departments> getAllDepartments() {
		return (List<Departments>) deptRepo.findAll();
	}
}
