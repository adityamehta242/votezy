package com.aditya.exception;

public class VoteNotAllowException extends RuntimeException{
	VoteNotAllowException(String message)
	{
		super(message);
	}
}
