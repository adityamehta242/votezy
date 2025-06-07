package com.aditya.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aditya.entity.Candidate;
import com.aditya.entity.Vote;
import com.aditya.entity.Voter;
import com.aditya.exception.ResourceNotFoundException;
import com.aditya.exception.VoteNotAllowException;
import com.aditya.repository.CandidateRepository;
import com.aditya.repository.VoteRepository;
import com.aditya.repository.VoterRepository;

import jakarta.transaction.Transactional;

@Service
public class VotingService {

	private VoteRepository voteRepository;
	private CandidateRepository candidateRepository;
	private VoterRepository voterRepository;
	
	public VotingService(VoteRepository voteRepository, CandidateRepository candidateRepository , VoterRepository voterRepository) {
		this.voteRepository = voteRepository;
		this.candidateRepository = candidateRepository;
		this.voterRepository = voterRepository;
	}
	
	@Transactional
	public Vote castVote(Long voterId , Long candidateId)
	{
		Voter voter = voterRepository.findById(voterId).orElse(null);
		Candidate candidate = candidateRepository.findById(candidateId).orElseGet(null);
		
		if(voter == null)
		{
			throw new ResourceNotFoundException("Voter id not Found");
		}
		 
		if(voter.getHas_voted() == true)
		{
			throw new VoteNotAllowException("Already voter cast their vote.");
		}
		
		if(candidate == null)
		{
			throw new ResourceNotFoundException("candidate not found exception");
		}
		
		Vote vote = new Vote();
		
		vote.setVoter(voter);
		vote.setCandidate(candidate);
		
		voteRepository.save(vote);
		
		candidate.setVoteCount(candidate.getVoteCount() + 1 );
		candidateRepository.save(candidate);
		
		voter.setHas_voted(true);
		voterRepository.save(voter);
		
		return vote;
		
	}
	
	public List<Vote> getAllVotes()
	{
		return voteRepository.findAll();
	}
	
}
