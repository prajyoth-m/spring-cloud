package com.example.department.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Departments {
	private String username;
	@Id
	private String department;
}
