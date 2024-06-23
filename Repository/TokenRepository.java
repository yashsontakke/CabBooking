package com.spring.cab.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.cab.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

}
