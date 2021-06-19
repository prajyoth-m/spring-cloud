package com.example.department.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.department.beans.Departments;

@Repository
public interface DepartmentRepository extends CrudRepository<Departments, String> {

}
