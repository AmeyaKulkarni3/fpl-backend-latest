package com.ameya.fplbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecordExistsException extends RuntimeException{

	private static final long serialVersionUID = -3492531573730256712L;
	
	public RecordExistsException(String message) {
		super(message);
	}

}
