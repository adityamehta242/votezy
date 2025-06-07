package com.aditya.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.entity.Candidate;
import com.aditya.entity.Vote;
import com.aditya.exception.ResourceNotFoundException;
import com.aditya.repository.CandidateRepository;

import jakarta.transaction.Transactional;

@Service
public class CandidateService {
    
    private final CandidateRepository candidateRepository;

    // Use constructor injection only
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate registerCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandiddate() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + id + " not found"));
    }

    public Candidate updateCandidate(Long id, Candidate updatedCandidate) {
        if (updatedCandidate == null) {
            throw new IllegalArgumentException("Updated candidate data must not be null");
        }
        
        Candidate candidate = getCandidateById(id);
        
        if (updatedCandidate.getEmail() != null) {
            candidate.setEmail(updatedCandidate.getEmail());
        }
        if (updatedCandidate.getName() != null) {
            candidate.setName(updatedCandidate.getName());
        }
        if (updatedCandidate.getPartyName() != null) {
            candidate.setPartyName(updatedCandidate.getPartyName());
        }
        
        return candidateRepository.save(candidate);
    }

    @Transactional
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + id + " not found"));
        
        // Clear votes associated with this candidate
        if (candidate.getVote() != null) {
            for (Vote vote : candidate.getVote()) {
                vote.setCandidate(null);
            }
            candidate.getVote().clear();
        }
        
        candidateRepository.delete(candidate);
    }

    public Long countCandidate() {
        return candidateRepository.count();
    }

    @Transactional
    public Candidate uploadCandidateImage(Long id, MultipartFile imageFile) throws IOException {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + id + " not found"));
        
        byte[] image = imageFile.getBytes();
        candidate.setCandidateImage(image);
        return candidateRepository.save(candidate);
    }

    @Transactional
    public Candidate uploadSymbol(Long id, MultipartFile imageFile) throws IOException {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + id + " not found"));
        
        byte[] image = imageFile.getBytes();
        candidate.setSymbol(image);
        return candidateRepository.save(candidate);
    }
}