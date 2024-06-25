package com.spring.cab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CabException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.model.Cab;
import com.spring.cab.service.CabService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cab")
public class CabController {

	private final CabService cabService ;			
	
	public CabController(CabService cabService) {
		this.cabService = cabService;
	}

	@PostMapping("/register")
	public ResponseEntity<Cab> register(@RequestBody Cab cab ) throws CabException {
		return new ResponseEntity<Cab>(cabService.insert(cab),HttpStatus.CREATED);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Cab> register(@RequestBody Cab cab , HttpServletRequest request  ) throws CabException, AdminException, LoginException, CurrentUserSessionException {
		return new ResponseEntity<Cab>(cabService.updateCab(cab , request ),HttpStatus.CREATED);
	}
}
  