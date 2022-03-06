package com.example.department.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Departments {
	private Integer users;
	@Id
	private String department;

	public Integer getUsers() {
		return users;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
