package com.springboot.blogging.exceptions;


public class ApiMessageException extends RuntimeException{
	
	public ApiMessageException( String message)
	{
		super(message);
	}

}
