package com.example.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.users.beans.Users;

public interface UserRepository extends CrudRepository<Users, String> {
}