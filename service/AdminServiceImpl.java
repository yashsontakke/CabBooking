package com.spring.cab.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.Repository.AdminRepository;
import com.spring.cab.Repository.CurrentUserSessionRepository;
import com.spring.cab.Repository.TokenRepository;
import com.spring.cab.model.Admin;
import com.spring.cab.model.CurrentUserSession;
import com.spring.cab.model.Token;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminServiceImpl implements  AdminService {
	
	private final AdminRepository adminRepository ;
	
	
	private final TokenRepository tokenRepository ;
	
	private final  CurrentUserSessionRepository currentUserSessionRepository;

	public AdminServiceImpl(AdminRepository adminRepository, TokenRepository tokenRepository, CurrentUserSessionRepository currentUserSessionRepository) {
		this.adminRepository = adminRepository;
		this.tokenRepository = tokenRepository;
		this.currentUserSessionRepository = currentUserSessionRepository;
	}


	@Override
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
	
	@Override
	public Admin updateAdmin(HttpServletRequest request , Admin admin ) throws AdminException, CurrentUserSessionException  , LoginException{
		
		UUID  tokenUUID = extractTokenFromHeaderAndValidate(request);
		
		
		
		Optional<Token> validUser = tokenRepository.findById(tokenUUID);
		
		if(!validUser.isPresent())  throw new AdminException("User is not logged in");
			
	
		
		int userId = validUser.get().getUser().getCurrUserId();
	
		
		Optional<CurrentUserSession> currentUser = currentUserSessionRepository.findByCurrUserIdAndCurrRole(userId , "Admin");
		
		
		if(!currentUser.isPresent()) throw new AdminException("No Admin with given token");
		
		Optional<Admin> toUpdateAdmin = adminRepository.findById(currentUser.get().getCurrUserId());
		return updateAdminProperties(toUpdateAdmin.get(),admin);				
		
	}
	
	
	@Override 
	public Admin deleteAdmin(HttpServletRequest request ) throws AdminException, LoginException, CurrentUserSessionException {
		
		UUID tokenUUID = extractTokenFromHeaderAndValidate(request);
		
		System.out.println(tokenUUID);
		Admin admin = searchForLoginAdmin(tokenUUID);		
						
		adminRepository.delete(admin);
//		currentUserSessionRepository.deleteByCurrUserId(admin.getAdminId());
		tokenRepository.deleteById(tokenUUID);
			 
		return admin ;
				
	}
	
	
	public Admin searchForLoginAdmin(UUID tokenUUID ) throws AdminException {
		Optional<Token> validUser = tokenRepository.findById(tokenUUID);
		
		if(!validUser.isPresent())  throw new AdminException("User is not logged in");
			
		int userId = validUser.get().getUser().getCurrUserId();
			
		Optional<CurrentUserSession> currentUser = currentUserSessionRepository.findByCurrUserIdAndCurrRole(userId , "Admin");
		
		if(!currentUser.isPresent()) throw new AdminException("No Admin with given token");
		
		Optional<Admin> admin = adminRepository.findById(currentUser.get().getCurrUserId());
		
		System.out.println(admin +"@114");
		
		return admin.get() ;
	}
	
	
	
	public Admin updateAdminProperties(Admin toUpdateAdmin, Admin admin) {
	    toUpdateAdmin.setUserName(admin.getUserName());
	    toUpdateAdmin.setPassword(admin.getPassword());
	    toUpdateAdmin.setAddress(admin.getAddress());
	    toUpdateAdmin.setMobileNumber(admin.getMobileNumber());
	    toUpdateAdmin.setEmail(admin.getEmail());
	    adminRepository.save(toUpdateAdmin);
	    return toUpdateAdmin;
	}
	
	private UUID extractTokenFromHeaderAndValidate(HttpServletRequest request) throws LoginException , CurrentUserSessionException{
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token =  authHeader.substring(7); // Remove "Bearer " prefix
            if (token == null) {
                throw new CurrentUserSessionException("Token not found in request headers");
            }

           
            try {
                UUID tokenUUID = UUID.fromString(token);
                return tokenUUID;
               
            } catch (IllegalArgumentException e) {
                throw new CurrentUserSessionException("Invalid token format");
            }
        }else {
        	throw new LoginException("invalid token");
        }
 
    }


}
