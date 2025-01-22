package com.jwt.starter.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.starter.config.TestConfig;
import com.jwt.starter.entities.JournalEntry;
import com.jwt.starter.repository.JournalRepository;
import com.jwt.starter.service.JournalService;

@WebMvcTest(JournalController.class)
@Import(TestConfig.class) // Import the test configuration
@AutoConfigureMockMvc(addFilters = false) 
public class JournalControllerWithoutAuthenticationTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper; 
	
	@Autowired 
	private JournalService journalService;
	
	@Autowired
	JournalRepository journalRepository;
		
	
	@WithMockUser
	@Test
    public void testGetJournalListFromDB() throws Exception {
		
		List<JournalEntry> listOfEntries = List.of(
				JournalEntry.builder().id(1).title("My first match").userId(1)
				.date(LocalDateTime.now()).build(),
				JournalEntry.builder().id(2).title("My first love").userId(1)
				.date(LocalDateTime.now()).build()
				);

		
		when(journalService.getJournalFromUserId(any())).thenReturn(listOfEntries);
		
		 // Act and Assert
        mockMvc.perform(get("/journals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listOfEntries)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
	}

}
