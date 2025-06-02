package com.aditya.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class voteRequestDTO {
	
	@NotNull(message = "voterId is required")
	private Long voterId;
	@NotNull(message = "candidateId is required")
	private Long candidateId;
}
