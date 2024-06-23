package com.spring.cab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.CurrentUserSession;
import com.spring.cab.model.UserLoginDTO;
import com.spring.cab.service.UserLoginService;


@RestController
@RequestMapping("/Userlogin")
public class LoginController {
	
	private final UserLoginService userLoginService ;
		
	public LoginController(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<CurrentUserSession> login(@RequestBody UserLoginDTO userLoginDto) throws LoginException{
		System.out.println("inside login controller");
	    return new ResponseEntity<CurrentUserSession>(userLoginService.login(userLoginDto), HttpStatus.ACCEPTED);
	}

	
		
}
