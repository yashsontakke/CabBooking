package com.spring.cab.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.cab.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer >{

	public Optional<Admin> findByEmail(String email);
}
