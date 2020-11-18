package com.todoHausenn.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.todoHausenn.exception.ExceptionDetails;
import com.todoHausenn.exception.ResourceNotFoundDetails;
import com.todoHausenn.exception.ResourceNotFoundException;
import com.todoHausenn.exception.ValidationExceptionDetails;


@ControllerAdvice
public class RestExceptionHandler  extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			HttpServletRequest request) {

		return new ResponseEntity<>(ResourceNotFoundDetails.builder()
				.timeStamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.title("Resource Not Found")
				.detail(exception.getMessage())
				.developerMessage(exception.getClass().getName())
				.build(), null, HttpStatus.NOT_FOUND);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<FieldError> fieldsErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldsErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldsMesseges = fieldsErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
				.timeStamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Fields Validation Error")
				.detail("Check the field(s) below")
				.developerMessage(exception.getClass().getName())
				.fields(fields)
				.fieldsMesseges(fieldsMesseges)
				.build(),
				null, HttpStatus.BAD_REQUEST);

	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.NOT_FOUND.value())
		.title(exception.getCause().getMessage())
		.detail(exception.getMessage())
		.developerMessage(exception.getClass().getName())
		.build();
		return new ResponseEntity<>(exceptionDetails, headers, status);
	}

	 
}
