package com.jwt.starter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.starter.entities.JournalEntry;
import com.jwt.starter.security.AuthenticatedUser;
import com.jwt.starter.service.JournalService;
import com.jwt.starter.utils.SecurityContextHolderUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/journals")
@RestController
public class JournalController {
	
	@Autowired
	private JournalService journalService;
	
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "To get list of journals of sepecific user", description = "Returns list of journals")
	@GetMapping
	@PreAuthorize("isAuthenticated()") // Ensures the user is authenticated
	public ResponseEntity<List<JournalEntry>> getJournalsForUser() {
		
		AuthenticatedUser authenticatedUser = SecurityContextHolderUtil.getAuthenticatedUser();
		log.info("Fetching journals of user - {}", authenticatedUser.getUsername());
		return ResponseEntity.ok
				(journalService.getJournalFromUserId(authenticatedUser.getUsers().getId()));
	}
	
	@SecurityRequirement(name = "BearerAuth")
	@Operation(summary = "To create journal entry", description = "Returns journal object after creation")
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<JournalEntry> createJournalEntryForUser(@RequestBody JournalEntry journalEntry) {
		
		AuthenticatedUser authenticatedUser = SecurityContextHolderUtil.getAuthenticatedUser();
		log.info("Creating journals of user - {}", authenticatedUser.getUsername());
		journalEntry.setUserId(authenticatedUser.getUsers().getId());
		return ResponseEntity.ok
				(journalService.createJournalForUser(journalEntry));
	}
	

}
