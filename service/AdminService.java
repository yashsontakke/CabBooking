package com.spring.cab.service;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.model.Admin;

public interface AdminService {

	Admin insertAdmin(Admin admin) throws AdminException;

}
