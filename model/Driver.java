package com.spring.cab.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class Driver extends User{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer driverId ;	
	
	private String licenceNo;
	private float rating;
	private String currLocation;
	private String currDriverStatus;
	
	
    //	Driver entity is persisted, any TripBooking entities associated with that Driver will also be persisted if they are not already managed. 

    //	mappedBy = "driver" indicates that the Driver entity owns the relationship, 
    //	and the driver field in the TripBooking entity maps this relationship
	
	@OneToMany(mappedBy="driver" , cascade = CascadeType.PERSIST)
	@JsonManagedReference
	@JsonIgnore
	private List<TripBooking> tripBookings = new ArrayList<>() ;
	
	@OneToOne
	@JsonIgnore
	private Cab cab;
		
}
