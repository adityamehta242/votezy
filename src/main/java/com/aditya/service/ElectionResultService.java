package com.aditya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aditya.entity.Candidate;
import com.aditya.entity.ElectionResult;
import com.aditya.exception.ResourceNotFoundException;
import com.aditya.repository.CandidateRepository;
import com.aditya.repository.ElectionResultRepository;
import com.aditya.repository.VoteRepository;

@Service
public class ElectionResultService {
	
	private CandidateRepository candidateRepository;
	private ElectionResultRepository electionResultRepository;
	private VoteRepository voteRepository;
	
	
	
	public ElectionResultService(CandidateRepository candidateRepository,
			ElectionResultRepository electionResultRepository, VoteRepository voteRepository) {
		this.candidateRepository = candidateRepository;
		this.electionResultRepository = electionResultRepository;
		this.voteRepository = voteRepository;
	}



	public ElectionResult declareElectionResult(String electionName)
	{
		Optional<ElectionResult> existingResult = electionResultRepository.findByElectionName(electionName);
		
		if(existingResult.isPresent())
		{
			return existingResult.get();
		}
		
		if(voteRepository.count() == 0)
		{
			throw new IllegalStateException("cannot declare the result has been no vote casted.");
		}
		
		List<Candidate> allCandidate = candidateRepository.findAllByOrderByVoteCountDesc();
		
		if(allCandidate.isEmpty())
		{
			throw new ResourceNotFoundException("Not candidate availabe");
		}
		
		Candidate winner = allCandidate.get(0);
		
		int totalVote = 0;
		for(Candidate candidate : allCandidate)
		{
			totalVote +=candidate.getVoteCount();
		}
		
		ElectionResult result = new ElectionResult();
		result.setTotalVotes(totalVote);
		result.setWinner(winner);
		result.setElectionName(electionName);
		
		return electionResultRepository.save(result);
	}
	
	public List<ElectionResult> getAllResults(){
		return electionResultRepository.findAll();
	}
}
