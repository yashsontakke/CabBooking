package com.spring.cab.service;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.Admin;

import jakarta.servlet.http.HttpServletRequest;

public interface AdminService {

	Admin insertAdmin(Admin admin) throws AdminException;

	Admin updateAdmin(HttpServletRequest request, Admin admin)
			throws AdminException, CurrentUserSessionException, LoginException;

}
