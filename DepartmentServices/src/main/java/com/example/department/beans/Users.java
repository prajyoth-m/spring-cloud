package com.example.department.beans;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Users {
	@Id
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private BigInteger phone;
	private String department;
}
