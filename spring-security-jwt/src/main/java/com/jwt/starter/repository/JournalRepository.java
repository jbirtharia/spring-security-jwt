package com.jwt.starter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.starter.entities.JournalEntry;

public interface JournalRepository extends JpaRepository<JournalEntry, Integer>{

	List<JournalEntry> findByUserId(Integer userId);

}
