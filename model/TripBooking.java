package com.spring.cab.model;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class TripBooking {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer tripBookingId ;
	
	
	@NotNull
	private String pickupLocation;
	@NotNull
	private String fromDateTime;
	@NotNull
	private String dropLocation;
	@NotNull
	private String toDateTime;
	@NotNull
	private float distanceInKm;
	
	//	Prevents the currStatus field from being included in JSON representations of the TripBooking object
	@JsonIgnore
	private String currStatus;
	
	// To Avoid Circular Reference 
	@ManyToOne
	@JsonBackReference
	private Driver driver ;
			
}
