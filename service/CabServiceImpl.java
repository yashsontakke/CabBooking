package com.spring.cab.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.AdminException;
import com.spring.cab.Exception.CabException;
import com.spring.cab.Exception.CurrentUserSessionException;
import com.spring.cab.Exception.LoginException;
import com.spring.cab.Repository.AdminRepository;
import com.spring.cab.Repository.CabRepository;
import com.spring.cab.Repository.CurrentUserSessionRepository;
import com.spring.cab.Repository.TokenRepository;
import com.spring.cab.model.Cab;
import com.spring.cab.model.CurrentUserSession;
import com.spring.cab.model.Token;

import jakarta.servlet.http.HttpServletRequest;

@Service 
public class CabServiceImpl implements CabService{

	private final CabRepository cabRepository ;		
	
	private final TokenRepository tokenRepository ;

	private final  CurrentUserSessionRepository currentUserSessionRepository;
	
	
	public CabServiceImpl(CabRepository cabRepository, CurrentUserSessionRepository currentUserSessionRepository, TokenRepository tokenRepository) {
		this.cabRepository = cabRepository;
		this.tokenRepository = tokenRepository;
		this.currentUserSessionRepository = currentUserSessionRepository;
	}


	@Override
	public Cab insert(Cab cab) throws CabException {
		Optional<Cab> findCab = cabRepository.findByCarNumber(cab.getCarNumber());
		
		if(findCab.isPresent()) throw new CabException("Cab Already Exists with car number " + cab.getCarNumber());
		
		return cabRepository.save(cab);
		
	}

//	@Override 
	public Cab updateCab(Cab cab ,HttpServletRequest request ) throws CabException, AdminException, LoginException, CurrentUserSessionException{
		
		UUID  tokenUUID = extractTokenFromHeaderAndValidate(request);				
		
		Optional<Token> validUser = tokenRepository.findById(tokenUUID);
		
		if(!validUser.isPresent())  throw new AdminException("User is not logged in");
						
		int userId = validUser.get().getUser().getCurrUserId();
		
		Optional<CurrentUserSession> validuser = currentUserSessionRepository.findByCurrUserIdAndCurrRole(userId,"Admin");
		if(validuser.isPresent()) {
			Optional<Cab> cb = cabRepository.findByCarNumber(cab.getCarNumber());
			if(cb.isPresent()) {
				
				Cab data = cb.get();
				data.setCarName(cab.getCarName());			
				data.setCarType(cab.getCarType());
				data.setPerKmRate(cab.getPerKmRate());
				
				return cabRepository.save(data);				
			}
			else {
				throw new CabException("Cab is not Registered");
			}
		}
		else {
			throw new CurrentUserSessionException("User not login In or User is not an Admin");
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

