package com.jwt.starter.service;

import com.jwt.starter.entities.JournalEntry;
import com.jwt.starter.repository.JournalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest
// Below annotation is used to test without loading spring context
@ExtendWith(MockitoExtension.class)
public class JournalServiceTest {

    @Mock
    private JournalRepository journalRepository;

    @InjectMocks
    private JournalService journalService;

    @Test
    public void getJournalsForUserTest() {

        List<JournalEntry> listOfEntries = List.of(
                JournalEntry.builder().id(1).title("My first match").userId(1)
                        .date(LocalDateTime.now()).build(),
                JournalEntry.builder().id(2).title("My first love").userId(1)
                        .date(LocalDateTime.now()).build()
        );

        when(journalRepository.findByUserId(any(Integer.class))).thenReturn(listOfEntries);

        assertEquals(2, journalService.getJournalFromUserId(1).size());
    }

}
