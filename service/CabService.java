package com.spring.cab.service;

import java.util.List;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CabException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.Cab;

import jakarta.servlet.http.HttpServletRequest;

public  interface  CabService {
	
	Cab insert(Cab cab ) throws CabException;


	Cab updateCab(Cab cab, HttpServletRequest request) throws CabException, AdminException, LoginException, CurrentUserSessionException;


	List<Cab> getCabOfTypes(String cabType, HttpServletRequest request)
			throws AdminException, CurrentUserSessionException, CabException, LoginException;

	Integer countCabsOfType(String carType, HttpServletRequest request) throws CabException, CurrentUserSessionException, AdminException, LoginException;


	Cab delete(String cabId, HttpServletRequest request) throws CurrentUserSessionException, CabException, AdminException, LoginException;
	
	
}
