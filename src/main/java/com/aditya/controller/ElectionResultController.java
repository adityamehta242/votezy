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

import com.aditya.dto.ElectionResultRequestDTO;
import com.aditya.dto.ElectionResultResponseDTO;
import com.aditya.entity.ElectionResult;
import com.aditya.service.ElectionResultService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/result")
public class ElectionResultController {
	private final ElectionResultService electionResultService;

	public ElectionResultController(ElectionResultService electionResultService) {
		this.electionResultService = electionResultService;
	}
	
	@PostMapping("/declare")
	public ResponseEntity<ElectionResultResponseDTO> declareElectionResult(@Valid @RequestBody ElectionResultRequestDTO electionResultRequestDTO)
	{
		ElectionResult electionResult =  electionResultService.declareElectionResult(electionResultRequestDTO.getElectionName());
		ElectionResultResponseDTO electionResultResponseDTO =  new ElectionResultResponseDTO();
		
		electionResultResponseDTO.setName(electionResult.getElectionName());
		electionResultResponseDTO.setTotalVote(electionResult.getTotalVotes());
		electionResultResponseDTO.setWinnerId(electionResult.getWinnerId());
		electionResultResponseDTO.setWinnerVote(electionResult.getWinner().getVoteCount());
		
		return new ResponseEntity<ElectionResultResponseDTO>(electionResultResponseDTO , HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<ElectionResult>> getAllResults()
	{
		return ResponseEntity.ok(electionResultService.getAllResults());
	}
	
	
}
