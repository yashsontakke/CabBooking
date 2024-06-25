package com.spring.cab.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.AdminException;
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
	
	private final  CurrentUserSessionRepository currentUserSessionRepository;
	
	private final CustomerRepository customerRepository ;
		
	private final TokenRepository tokenRepository ;
	
	
	public UserLoginServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository, CurrentUserSessionRepository currentUserSessionRepository, TokenRepository tokenRepository) {
		this.adminRepository = adminRepository;
		this.customerRepository = customerRepository;
		this.currentUserSessionRepository = currentUserSessionRepository;
		this.tokenRepository = tokenRepository;
	}


	// will include validation later on that only customer and admin roles are allowed 
	public UUID login (UserLoginDTO loginUserDTO) throws LoginException{
		
		
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
			currentUserSessionRepository.save(currentUser);
			
			return token.getTokenId();
		}
		
		if(customer.isPresent()) {
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
				currentUserSessionRepository.save(currentUser);
				
				return token.getTokenId();
		}
	
		   
		throw new LoginException("No User Available");
	  
	   }
	
	@Override
	public String LogOut(HttpServletRequest request) throws CurrentUserSessionException ,CurrentUserSessionException , LoginException{
		// TODO Auto-generated method stub
		 UUID tokenUUID = extractTokenFromHeaderAndValidate(request);
        
		Optional<Token> validAdminOrCustomer = tokenRepository.findById(tokenUUID);
		
		if(validAdminOrCustomer.isPresent()) {
			
			tokenRepository.delete(validAdminOrCustomer.get());
			return "User Logged Out Successfully";
			
		}
		else {
			throw new CurrentUserSessionException("User Not Logged In with this Credentials");
		}
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
