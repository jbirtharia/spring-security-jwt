package com.jwt.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.starter.entities.AppInfo;
import com.jwt.starter.service.RedisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@RestController
@RequestMapping("/info")
public class InfoController {
	
	private final static String INFO_KEY = "Info";
	
	@Autowired
	private RedisService redisService;

	@GetMapping
	@SecurityRequirements
	public ResponseEntity<AppInfo> getAppInfo(){
		
		return ResponseEntity.status(HttpStatus.OK).body(redisService.getObject(INFO_KEY, AppInfo.class));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Hidden Endpoint", hidden = true)
	public ResponseEntity<AppInfo> setAppInfo(@RequestBody AppInfo appInfo){
		
		AppInfo oldAppInfo = redisService.getObject(INFO_KEY, AppInfo.class);
		if(oldAppInfo != null) {
			appInfo.setId(oldAppInfo.getId()+1);
		}else {
			appInfo.setId(1);
		}
		redisService.setObject(INFO_KEY, appInfo);
		return ResponseEntity.status(HttpStatus.CREATED).body(appInfo);
	}
	
}
