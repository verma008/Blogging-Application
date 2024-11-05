package com.springboot.blogging.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.blogging.DTO.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<CommonResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException rx)
     {
		String message = rx.getMessage();
//		System.out.println("mess" + message);
		
		CommonResponse response = new CommonResponse(message);
		
		return new ResponseEntity<CommonResponse>(response, HttpStatus.NOT_FOUND);
     }
	
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<CommonResponse> AlreadyExistExceptionHandler(AlreadyExistException ae)
	{
		String message = ae.getMessage();
		CommonResponse response=new CommonResponse(message);
		return new ResponseEntity<CommonResponse>(response,HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<CommonResponse> UnauthorizedExceptionHandler(UnauthorizedException ua)
	{
		String message = ua.getMessage();
		CommonResponse response=new CommonResponse(message);
		return new ResponseEntity<CommonResponse>(response,HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(ApiMessageException.class)
	public ResponseEntity<CommonResponse> ApiMessageExceptionHandler(ApiMessageException rx)
    {
		String message = rx.getMessage();
		CommonResponse response = new CommonResponse(message);
		return new ResponseEntity<CommonResponse>(response, HttpStatus.NOT_FOUND);
    }
}
