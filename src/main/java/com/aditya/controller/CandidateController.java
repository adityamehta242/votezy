package com.aditya.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.entity.Candidate;
import com.aditya.exception.ResourceNotFoundException;
import com.aditya.service.CandidateService;

import jakarta.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api/candidate")
public class CandidateController {
	
	private CandidateService candidateService;

	public CandidateController(CandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Candidate> registerCandidate(@RequestBody @Valid  Candidate candidate)
	{
		return new ResponseEntity<>(candidateService.registerCandidate(candidate), HttpStatus.CREATED);
		
	}
	
	@GetMapping()
	public ResponseEntity<List<Candidate>> getAllCandidate()
	{
		return new ResponseEntity<>(candidateService.getAllCandiddate(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id)
	{
		return new ResponseEntity<Candidate>(candidateService.getCandidateById(id), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id , @RequestBody Candidate candidate)
	{
		return new ResponseEntity<Candidate>(candidateService.updateCandidate(id, candidate) , HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
	    try {
	        candidateService.deleteCandidate(id);
	        return new ResponseEntity<>("Candidate deleted successfully.", HttpStatus.OK);
	    } catch (ResourceNotFoundException e) {
	        return new ResponseEntity<>("Candidate not found with id: " + id, HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Failed to delete candidate: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> getCandidateCount() {
		return new ResponseEntity<Long>(candidateService.countCandidate() , HttpStatus.OK);
	}
	
	@PostMapping("/{id}/upload-image")
	public ResponseEntity<?> uploadCandidateImage(@PathVariable Long id , @RequestParam("image") MultipartFile imageFile)
	{
		try {
			Candidate candidate = candidateService.uploadCandidateImage(id, imageFile);
			return ResponseEntity.ok(candidate);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
		}
	}
	
	@PostMapping("/{id}/upload-image-symbol")
	public ResponseEntity<?> uploadSymbol(@PathVariable Long id , @RequestParam("image") MultipartFile imageFile)
	{
		try {
			Candidate candidate = candidateService.uploadSymbol(id, imageFile);
			return new ResponseEntity<>(candidate , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to uplaod symbol" , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
