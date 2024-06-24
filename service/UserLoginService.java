package com.spring.cab.service;

import java.util.UUID;


import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.UserLoginDTO;

import jakarta.servlet.http.HttpServletRequest;


public interface  UserLoginService {


	 UUID login (UserLoginDTO loginUserDTO) throws LoginException;


 	 String LogOut(HttpServletRequest request) throws CurrentUserSessionException, LoginException;
}
