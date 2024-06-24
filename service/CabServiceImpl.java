package com.spring.cab.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.cab.Exception.CabException;
import com.spring.cab.Repository.CabRepository;
import com.spring.cab.model.Cab;

@Service 
public class CabServiceImpl implements CabService{

	private final CabRepository cabRepository ;		
	
	public CabServiceImpl(CabRepository cabRepository) {
		this.cabRepository = cabRepository;
	}


	@Override
	public Cab insert(Cab cab) throws CabException {
		Optional<Cab> findCab = cabRepository.findByCarNumber(cab.getCarNumber());
		
		if(findCab.isPresent()) throw new CabException("Cab Already Exists with car number " + cab.getCarNumber());
		
		return cabRepository.save(cab);
		
	}

}

