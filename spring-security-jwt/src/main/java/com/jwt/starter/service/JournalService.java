package com.jwt.starter.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.starter.entities.JournalEntry;
import com.jwt.starter.repository.JournalRepository;

@Service
public class JournalService {
	
	@Autowired
	private JournalRepository journalRepository;
	
	
	public List<JournalEntry> getJournalFromUserId(Integer userId) {
		
		return journalRepository.findByUserId(userId);
	}


	@Transactional
	public JournalEntry createJournalForUser(JournalEntry journalEntry) {
		
		journalEntry.setDate(LocalDateTime.now());
		return journalRepository.save(journalEntry);
	}

}
