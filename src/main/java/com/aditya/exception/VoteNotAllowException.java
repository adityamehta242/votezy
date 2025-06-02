package com.aditya.exception;

public class VoteNotAllowException extends RuntimeException{
	public VoteNotAllowException(String message)
	{
		super(message);
	}
}
