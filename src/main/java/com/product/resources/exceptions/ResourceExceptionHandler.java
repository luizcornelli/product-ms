package com.product.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.product.services.exceptions.InvalidFieldException;
import com.product.services.exceptions.PriceNotPositiveException;
import com.product.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(PriceNotPositiveException.class)
	public ResponseEntity<StandardError> priceNotPositive(PriceNotPositiveException priceNotPositiveException,
			HttpServletRequest httpServletRequest) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		StandardError standardError = new StandardError();

		standardError.setStatus(httpStatus.value());
		standardError.setMessage(priceNotPositiveException.getMessage());

		return ResponseEntity.status(httpStatus.value()).body(standardError);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException resourceNotFoundException,
			HttpServletRequest httpServletRequest) {

		HttpStatus httpStatus = HttpStatus.NOT_FOUND;

		StandardError standardError = new StandardError();

		standardError.setStatus(httpStatus.value());
		standardError.setMessage(resourceNotFoundException.getMessage());

		return ResponseEntity.status(httpStatus.value()).body(standardError);
	}

	@ExceptionHandler(InvalidFieldException.class)
	public ResponseEntity<StandardError> invalidFiel(InvalidFieldException invalidFieldException,
			HttpServletRequest httpServletRequest) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		StandardError standardError = new StandardError();

		standardError.setStatus(httpStatus.value());
		standardError.setMessage(invalidFieldException.getMessage());

		return ResponseEntity.status(httpStatus.value()).body(standardError);
	}

}
