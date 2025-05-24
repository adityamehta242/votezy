package com.aditya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aditya.entity.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long>{
	public Boolean existsByEmail(String email);
}
