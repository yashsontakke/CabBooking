package com.spring.cab.model;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID tokenId;
	
	@OneToOne(cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
	private CurrentUserSession user ;

	public UUID getTokenId() {
		return tokenId;
	}

	public void setTokenId(UUID tokenId) {
		this.tokenId = tokenId;
	}

	public CurrentUserSession getUser() {
		return user;
	}

	public void setUser(CurrentUserSession user) {
		this.user = user;
	}
			

}
