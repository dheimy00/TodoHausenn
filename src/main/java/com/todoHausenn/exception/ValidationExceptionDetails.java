package com.todoHausenn.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
	
	private String fields;
	
	private String fieldsMesseges;

}
