package com.example.Exam.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}