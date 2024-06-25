package com.spring.cab.service;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CabException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.Cab;

import jakarta.servlet.http.HttpServletRequest;

public  interface  CabService {
	Cab insert(Cab cab ) throws CabException;


	Cab updateCab(Cab cab, HttpServletRequest request) throws CabException, AdminException, LoginException, CurrentUserSessionException;
}
