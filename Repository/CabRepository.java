package com.spring.cab.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.cab.model.Cab;

@Repository
public interface CabRepository extends JpaRepository<Cab, Integer> {

	Optional<Cab> findByCarNumber(String carNumber);

}
