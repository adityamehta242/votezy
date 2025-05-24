package com.aditya.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Voter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "email is required")
	@Email(message = "Invalid email fromate")
	private String email;
	
	private Boolean has_voted = false;
	
	@OneToOne(mappedBy = "voter" , cascade =  CascadeType.REMOVE)
	private Vote vote;
}
