package com.spring.cab.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.UserLoginDTO;
import com.spring.cab.service.UserLoginService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/Userlogin")
public class LoginController {
	
	private final UserLoginService userLoginService ;
		
	public LoginController(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<UUID> login(@RequestBody UserLoginDTO userLoginDto) throws LoginException{
		System.out.println("inside login controller");
	    return new ResponseEntity<UUID>(userLoginService.login(userLoginDto), HttpStatus.ACCEPTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) throws CurrentUserSessionException, LoginException{
		System.out.println("inside login controller");
	    return new ResponseEntity<String>(userLoginService.LogOut(request), HttpStatus.ACCEPTED);
	}

		
}
