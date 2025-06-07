package com.aditya.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.dto.VoteResponseDTO;
import com.aditya.dto.voteRequestDTO;
import com.aditya.entity.Vote;
import com.aditya.service.VotingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/votes")
public class VotingController {
	private VotingService votingService;

	public VotingController(VotingService votingService) {
		this.votingService = votingService;
	}
	
	@PostMapping("/cast")
	public ResponseEntity<VoteResponseDTO> castVote(@RequestBody @Valid voteRequestDTO voteRequestDTO)
	{
		
		Vote vote = votingService.castVote(voteRequestDTO.getVoterId(), voteRequestDTO.getCandidateId());
		VoteResponseDTO voteResponseDTO = new VoteResponseDTO("vote casted successfully" , true , vote.getVoterId() , vote.getCandidateId());
		return new ResponseEntity<VoteResponseDTO>( voteResponseDTO , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Vote>> getAllVotes()
	{
		return new ResponseEntity<List<Vote>>(votingService.getAllVotes() , HttpStatus.OK);
	}
	
	
}
