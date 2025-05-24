package com.aditya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	List<Candidate> findAllByOrderByVoteCountDesc();
}
