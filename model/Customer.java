package com.spring.cab.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer CustomerId ;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<TripBooking> tripBooking;
}