package com.spring.cab.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.Repository.AdminRepository;
import com.spring.cab.Repository.CurrentUserSessionRepository;
import com.spring.cab.Repository.CustomerRepository;
import com.spring.cab.Repository.TokenRepository;
import com.spring.cab.model.Admin;
import com.spring.cab.model.CurrentUserSession;
import com.spring.cab.model.Customer;
import com.spring.cab.model.Token;
import com.spring.cab.model.UserLoginDTO;

import jakarta.servlet.http.HttpServletRequest;




@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	private final AdminRepository adminRepository ;
	
	private final CustomerRepository customerRepository ;
	
	private final  CurrentUserSessionRepository currentUserSessionRepository; 
	
	
	private final TokenRepository tokenRepository ;
	
	
	public UserLoginServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository, CurrentUserSessionRepository currentUserSessionRepository, TokenRepository tokenRepository) {
		this.adminRepository = adminRepository;
		this.customerRepository = customerRepository;
		this.currentUserSessionRepository = currentUserSessionRepository;
		this.tokenRepository = tokenRepository;
	}


	// will include validation later on that only customer and admin roles are allowed 
	public CurrentUserSession login (UserLoginDTO loginUserDTO) throws LoginException{
		
		
		Optional<Admin> admin = adminRepository.findByEmail(loginUserDTO.getEmail());
		
		Optional<Customer> customer = customerRepository.findByEmail(loginUserDTO.getEmail());
		
		
		
		if(admin.isPresent()) {
			

			Optional<CurrentUserSession> user = currentUserSessionRepository.findById(admin.get().getAdminId());
			
			if(user.isPresent()) throw new LoginException("User Already Login");
			
			if(!admin.get().getPassword().equals(loginUserDTO.getPassword())){
				throw new LoginException("Wrong Password ");
			}
			
			CurrentUserSession currentUser = new CurrentUserSession();
			currentUser.setCurrUserId(admin.get().getAdminId());
			currentUser.setCurrRole("Admin");
			currentUser.setCurrStatus("Login Successfull");
			
			Token token = new Token();
			token.setUser(currentUser);
			
			
			tokenRepository.save(token);
			return currentUserSessionRepository.save(currentUser);
		}
		
	
		 Optional<CurrentUserSession> user = currentUserSessionRepository.findById(customer.get().getCustomerId());
		
		 if(user.isPresent()) throw new LoginException("User Already Login");
			
			if(!customer.get().getPassword().equals(loginUserDTO.getPassword())){
				throw new LoginException("Wrong Password ");
			}
			
			CurrentUserSession currentUser = new CurrentUserSession();
			currentUser.setCurrUserId(customer.get().getCustomerId());
			currentUser.setCurrRole("Customer");
			currentUser.setCurrStatus("Login Successfull");
			
			Token token = new Token();
			token.setUser(currentUser);
			
			
			tokenRepository.save(token);
			return currentUserSessionRepository.save(currentUser);
		
	  
	   }
	
	@Override
	public String LogOut(HttpServletRequest request) throws CurrentUserSessionException {
		// TODO Auto-generated method stub
		String token = extractTokenFromHeader(request);
        if (token == null) {
            throw new CurrentUserSessionException("Token not found in request headers");
        }
        System.out.println(token);
        
        UUID tokenUUID = UUID.fromString(token);
        System.out.println(tokenUUID);
        
		Optional<Token> validAdminOrCustomer = tokenRepository.findById(tokenUUID);
		
		if(validAdminOrCustomer.isPresent()) {
			
			tokenRepository.delete(validAdminOrCustomer.get());
			return "User Logged Out Successfully";
			
		}
		else {
			throw new CurrentUserSessionException("User Not Logged In with this Credentials");
		}
	}
	private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

	
}
