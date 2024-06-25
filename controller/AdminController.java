package com.spring.cab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.Admin;
import com.spring.cab.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	
	

	public AdminController(AdminService adminService) {
		super();
		this.adminService = adminService;
	}


	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin ) throws AdminException {
		System.out.println("in admin controller register method");
		return new ResponseEntity<Admin>(adminService.insertAdmin(admin),HttpStatus.CREATED);
	}
	

	@PostMapping("/update")
	public ResponseEntity<Admin> updateAdmin(HttpServletRequest request  , @RequestBody Admin admin ) throws AdminException, CurrentUserSessionException, LoginException {
		return new ResponseEntity<Admin>(adminService.updateAdmin(request , admin),HttpStatus.OK);
	}
	@PostMapping("/delete")
	public ResponseEntity<Admin> deleteAdmin(HttpServletRequest request  ) throws AdminException, CurrentUserSessionException, LoginException {
		return new ResponseEntity<Admin>(adminService.deleteAdmin(request),HttpStatus.OK);
	}
	
}
