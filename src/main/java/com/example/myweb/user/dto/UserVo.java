package com.example.myweb.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
	// ID  	PASSWORD  	NAME  	ROLE
	private String id;
	private String password;
	private String name;
	private String role;
}
