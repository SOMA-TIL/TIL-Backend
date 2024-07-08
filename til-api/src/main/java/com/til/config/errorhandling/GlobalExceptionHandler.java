package com.til.config.errorhandling;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.til.domain.common.enums.BaseErrorCode;
import com.til.domain.common.exception.BaseException;
import com.til.domain.common.exception.InvalidDtoException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(final BaseException ex) {
		log.error("handleCustomException: {}", ex.getErrorCode());
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode());

		return ResponseEntity
			.status(ex.getErrorCode().getStatus().value())
			.body(response);
	}

	@ExceptionHandler(InvalidDtoException.class)
	protected ResponseEntity<ErrorResponse> handleInvalidDtoException(final InvalidDtoException ex) {
		log.error("InvalidDtoException: {}", ex.getMessage());
		ErrorResponse response = ErrorResponse.of("INVALID_DTO", ex.getMessage());

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(response);
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ErrorResponse> handleAccessDenied(final AccessDeniedException ex) {
		log.error("AccessDeniedException: {}", ex.getMessage());
		ErrorResponse response = ErrorResponse.of(BaseErrorCode.ACCESS_DENIED);

		return ResponseEntity
			.status(HttpStatus.FORBIDDEN)
			.body(response);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse response = ErrorResponse.of(BaseErrorCode.METHOD_NOT_ALLOWED);

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(response);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
		HttpStatusCode status, WebRequest request) {
		ErrorResponse response = ErrorResponse.of(BaseErrorCode.NOT_FOUND);

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(response);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse response = ErrorResponse.of(BaseErrorCode.BAD_REQUEST);

		return ResponseEntity
			.badRequest()
			.body(response);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse response = ErrorResponse.of(BaseErrorCode.BAD_REQUEST);

		return ResponseEntity
			.badRequest()
			.body(response);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleAllException(final Exception ex) {
		log.error("Exception: {}", ex.getMessage());
		ErrorResponse response = ErrorResponse.of(BaseErrorCode.INTERNAL_SERVER_ERROR);

		return ResponseEntity
			.internalServerError()
			.body(response);
	}
}
