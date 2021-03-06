package com.barbearia.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){

		ApiExceptionDto apiException = new ApiExceptionDto(
				e.getMessage(), 
				e.getHttpStatus(), 
				ZonedDateTime.now(ZoneId.of("Z")));
		
		return new ResponseEntity<Object>(apiException, e.getHttpStatus());
	}
}
