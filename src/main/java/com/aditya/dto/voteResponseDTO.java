package com.aditya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class voteResponseDTO {
	private String message;
	private boolean success;
	private Long voterId;
	private Long candidateId;
	
}
