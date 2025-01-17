package com.jwt.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.starter.entities.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

	Users findByUserName(String username);

}
