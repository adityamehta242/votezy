package com.aditya.dto;

import lombok.Data;

@Data
public class ElectionResultResponseDTO {
	private String name;
	private int totalVote;
	private Long winnerId;
	private int winnerVote;
}
