package com.spring.cab.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cab {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cabId;
	private String carType;
	private String carName;
	private String carNumber;
	private float perKmRate;
	private String CurrLocation;
	private String cabCurrStatus;
	
	@OneToOne
	@JsonIgnore
	@JsonBackReference 
	private Driver driver;
	
}