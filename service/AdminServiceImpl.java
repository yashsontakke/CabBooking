package com.spring.cab.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Repository.AdminRepository;
import com.spring.cab.model.Admin;

@Service
public class AdminServiceImpl implements  AdminService {
	
	private final AdminRepository adminRepository ;
	
	

	public AdminServiceImpl(AdminRepository adminRepository) {
		super();
		this.adminRepository = adminRepository;
	}



	public Admin insertAdmin(Admin admin) throws AdminException {
		// TODO Auto-generated method stub
		
		Optional<Admin> adm = adminRepository.findByEmail(admin.getEmail());
		
		if(adm.isPresent()) {
			throw new  AdminException("Admin Already Exists");
		}
		
		if(!"Admin".equalsIgnoreCase(admin.getUserRole())) {
			throw new  AdminException("user is not admin ");
		}
		
		return adminRepository.save(admin);
	
	}

}
