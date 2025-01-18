package com.jwt.starter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.starter.entities.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

	Optional<Users> findByUserName(String username);

}
