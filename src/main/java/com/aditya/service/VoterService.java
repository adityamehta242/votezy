package com.aditya.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.entity.Candidate;
import com.aditya.entity.Vote;
import com.aditya.entity.Voter;
import com.aditya.exception.DuplicateResourceException;
import com.aditya.exception.ResourceNotFoundException;
import com.aditya.repository.CandidateRepository;
import com.aditya.repository.VoterRepository;

import jakarta.transaction.Transactional;

@Service
public class VoterService {
	
	private static final Logger logger = LoggerFactory.getLogger(VoterService.class);
	
	private VoterRepository voterRepository;
	private CandidateRepository candidateRepository;
	
	public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
		this.voterRepository = voterRepository;
		this.candidateRepository = candidateRepository;
	}
	
	
	public Voter registerVoter(Voter voter)
	{
		if(voterRepository.existsByEmail(voter.getEmail()))
		{
			logger.error("Email already exists: {}", voter.getEmail());
			throw new DuplicateResourceException("Voter with email already exists: " + voter.getEmail());
		}
		logger.info("Voter successfully registered: {}", voter.getEmail());
		return voterRepository.save(voter);
		
		
	}
	
	public List<Voter> getAllVoter()
	{
		logger.info("fetch all the resources.");
		return voterRepository.findAll();
	}
	
	
	public Voter getVoterById(Long id)
	{
		Voter voter = voterRepository.findById(id).orElse(null);
		if(voter == null)
		{
			logger.info("Voter id not found : " + id);
			throw new ResourceNotFoundException(String.format("Resource with ID %d not found", id));
		}
		logger.info("Voter ID {} fetched successfully.", id);
		return voter;
	}
	
	public Voter updateVoter(Long id , Voter updateVoter)
	{
		Voter voter = voterRepository.findById(id).orElse(null);
		
		if(voter == null)
		{
			logger.info("Voter id not found : " + id);
			throw new ResourceNotFoundException(String.format("Resource with ID %d not found", id));
		}
		
		if(updateVoter.getEmail()!=null)
		{
			voter.setEmail(updateVoter.getEmail());			
		}
		
		if(updateVoter.getName() != null)
		{
			voter.setName(updateVoter.getName());			
		}
		
		if(updateVoter.getVoterImg() != null)
		{
			voter.setVoterImg(updateVoter.getVoterImg());
		}
		
		logger.info("Voter ID {} update successfully" , id);
		return voterRepository.save(voter);
	}
	
	
	@Transactional
	public Boolean deleteVoter(Long id)
	{
		Voter voter = voterRepository.findById(id).orElse(null);
		if(voter == null)
		{
			throw new ResourceNotFoundException(String.format("Voter with ID %d not found for delete.", id));
		}
		
		Vote vote = voter.getVote();
		if(vote!=null)
		{
			Candidate candidate = vote.getCandidate();
			candidate.setVoteCount(candidate.getVoteCount() - 1);
			candidateRepository.save(candidate);
		}
		
		
		voterRepository.delete(voter);
		
		logger.info("voter id {} delete successfully" , id);
		return true;
	}
	
	public Long countVoter()
	{
		return voterRepository.count();
	}
	
	@Transactional
	public Voter uploadVoterImage(Long voterId, MultipartFile imageFile) throws IOException {
        Voter voter = voterRepository.findById(voterId)
            .orElseThrow(() -> new RuntimeException("Voter not found"));

        byte[] imageData = imageFile.getBytes();
        voter.setVoterImg(imageData);
        return voterRepository.save(voter);
    }
	
}
