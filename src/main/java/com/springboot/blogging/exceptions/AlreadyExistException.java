package com.springboot.blogging.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistException extends RuntimeException{
	
//	private String message;
	public AlreadyExistException(String message)
	{
//		this.message=message;
	}

}
