package com.jwt.starter.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {

	@Schema(hidden = true) 
	private Integer id;
	
	private String appName;
	
	private String description;
	
	private String version;
}
