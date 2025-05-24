package com.aditya.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Candidate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotBlank(message = "name of candidate required")
	private String name;
	
	@NotBlank(message = "party name required")
	private String partyName;
	
	@NotBlank(message = "page symbol required")
	private String symbol;
	
	@NotBlank(message = "candidate email is required")
	@Email(message = "Invalid email, provide valid email")
	private String email;
	
	private int voteCount = 0;
	
	@OneToMany(mappedBy = "candidate" , cascade = CascadeType.ALL)
	private List<Vote> vote;
}
