package com.nagarro.javatest.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ApplicationException extends RuntimeException{

	
	private int code;
	private String userDisplayMessage;
	private String errorMessage;
	private Throwable exception;
	
	
	public ApplicationException(int code,String userDisplayMessage,Throwable e) {
		log.error("ApplicationException caught");
		e.printStackTrace();
		this.code=code;
		this.userDisplayMessage = userDisplayMessage;
        this.exception=e;
	}


	
	
	
	
}
