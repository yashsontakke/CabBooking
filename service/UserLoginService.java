package com.spring.cab.service;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.CurrentUserSession;
import com.spring.cab.model.UserLoginDTO;

@Service
public interface  UserLoginService {


	 CurrentUserSession login (UserLoginDTO loginUserDTO) throws LoginException;
}
