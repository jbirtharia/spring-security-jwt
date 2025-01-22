package com.jwt.starter.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.starter.config.TestConfig;
import com.jwt.starter.entities.AppInfo;
import com.jwt.starter.service.RedisService;


@WebMvcTest(InfoController.class)
@Import(TestConfig.class) // Import the test configuration
@AutoConfigureMockMvc(addFilters = false) 
public class InfoControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

	@Autowired
    private RedisService redisService;

    @InjectMocks
    private InfoController appController;
    
    @Autowired
    private ObjectMapper objectMapper; 
    
    @Mock
    private ValueOperations<String, AppInfo> valueOperations;

    private static final String INFO_KEY = "Info";

    @Test
    void testStatusOfGetAppInfo_Success() throws Exception {
        // Arrange
        AppInfo appInfo = AppInfo.builder()
        		.appName("Test App").version("1.0.0").build();
        
        when(redisService.getObject(INFO_KEY, AppInfo.class)).thenReturn(appInfo);

        // Act & Assert
        mockMvc.perform(get("/info"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testPostAppInfoIfNoObjectInRedis_Success() throws Exception {
    	 // Arrange
        AppInfo app = AppInfo.builder().id(1)
        		.appName("Journal App").version("1.0.0").build();

        when(redisService.getObject(INFO_KEY, AppInfo.class)).thenReturn(app);

        // Act and Assert
        mockMvc.perform(post("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(app))) // Pass content without headers
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.appName").value("Journal App"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.id").value(2));
    }

    
    @Test
    void testPostAppInfoIfObjectInRedis_Success() throws Exception {
    	 // Arrange
        AppInfo oldAppInfo = AppInfo.builder().id(1)
        		.appName("Old App").version("1.0.0").build();

        AppInfo newAppInfo = AppInfo.builder().id(oldAppInfo.getId() + 1)
        		.appName("New App").version("2.0.0").build();

        when(redisService.getObject(INFO_KEY, AppInfo.class)).thenReturn(oldAppInfo);

        // Act and Assert
        mockMvc.perform(post("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAppInfo))) // Pass content without headers
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.appName").value("New App"))
                .andExpect(jsonPath("$.version").value("2.0.0"))
                .andExpect(jsonPath("$.id").value(2));
    }

}
