package com.spring.cab.service;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.CurrentUserSession;
import com.spring.cab.model.UserLoginDTO;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface  UserLoginService {


	 CurrentUserSession login (UserLoginDTO loginUserDTO) throws LoginException;





	String LogOut(HttpServletRequest request) throws CurrentUserSessionException;
}
