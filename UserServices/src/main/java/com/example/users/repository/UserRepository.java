package com.example.users.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.users.beans.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, String> {
	
	List<Users> findByDepartment(String department);
}
