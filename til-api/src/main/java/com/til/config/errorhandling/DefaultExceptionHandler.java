package com.til.config.errorhandling;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(final BaseException e) {
		log.error("handleCustomException: {}", e.getErrorCode());
		ErrorResponse response = ErrorResponse.of(e.getErrorCode());

		return ResponseEntity
			.status(e.getErrorCode().getStatus().value())
			.body(response);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedException(
		final HttpRequestMethodNotSupportedException e) {
		log.error("HttpRequestMethodNotSupportedException: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(response);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorResponse> NoHandlerFoundException(final NoHandlerFoundException e) {
		log.error("NoHandlerFoundException: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND);

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(response);
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ErrorResponse> AccessDeniedException(final AccessDeniedException e) {
		log.error("AccessDeniedException: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.of(ErrorCode.ACCESS_DENIED);

		return ResponseEntity
			.status(HttpStatus.FORBIDDEN)
			.body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> MethodArgumentNotValidException(final MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_REQUEST);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> HttpMessageNotReadableException(final HttpMessageNotReadableException e) {
		log.error("HttpMessageNotReadableException: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_REQUEST);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(response);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> Exception(final Exception e) {
		log.error("Exception: {}", e.getMessage());
		ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(response);
	}

}
