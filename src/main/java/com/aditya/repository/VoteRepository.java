package com.aditya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
