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

import com.aditya.entity.Voter;
import com.aditya.service.VoterService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/voters")
public class VoterController {
	
	private VoterService voterService;

	public VoterController(VoterService voterService) {
		this.voterService = voterService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Voter> registerVoter(@RequestBody @Valid Voter voter)
	{
		Voter saveVoter = voterService.registerVoter(voter);
		return new ResponseEntity<>(saveVoter , HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getVoterById(@PathVariable Long id)
	{
		Voter voter = voterService.getVoterById(id);
		return new ResponseEntity<>(voter , HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllVoter()
	{
		List<Voter> voterList = voterService.getAllVoter();
		return new ResponseEntity<>(voterList , HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateVoter(@PathVariable Long id , @RequestBody Voter voter)
	{
		Voter updatedVoter = voterService.updateVoter(id, voter);
		
		return new ResponseEntity<>(updatedVoter , HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteVoter(@PathVariable("id") Long id) {
	    boolean isDeleted = voterService.deleteVoter(id);

	    if (!isDeleted) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body("Voter with ID " + id + " not found or could not be deleted.");
	    }

	    return ResponseEntity
	            .ok("Voter with ID " + id + " was deleted successfully.");
	}
	
	@PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadVoterImage(@PathVariable Long id, 
                                                   @RequestParam("image") MultipartFile imageFile) {
        try {
            Voter voter = voterService.uploadVoterImage(id, imageFile);
            return ResponseEntity.ok(voter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");
        }
    }
	
	@GetMapping("/count")
	public ResponseEntity<Long> countVoter()
	{
		return new ResponseEntity<Long>(voterService.countVoter() , HttpStatus.OK);
	}

}
