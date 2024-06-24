package com.spring.cab.service;

import com.spring.cab.Exception.CabException;
import com.spring.cab.model.Cab;

public  interface  CabService {
	Cab insert(Cab cab ) throws CabException;
}
