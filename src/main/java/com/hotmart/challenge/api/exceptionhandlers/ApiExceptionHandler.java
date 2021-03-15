package com.hotmart.challenge.api.exceptionhandlers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hotmart.challenge.domain.exceptions.NotFoundException;
import com.hotmart.challenge.domain.exceptions.ValidationException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	MessageSource messageSource;
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidation(ValidationException ex, WebRequest request){
		var status = HttpStatus.BAD_REQUEST;
		var errorModel = populateError(ex, null, status, null);
		
		return super.handleExceptionInternal(ex, errorModel, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFound(ValidationException ex, WebRequest request){
		var status = HttpStatus.NOT_FOUND;
		var errorModel = populateError(ex, null, status, null);
		
		return super.handleExceptionInternal(ex, errorModel, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var listFields = new ArrayList<ErrorModel.Field>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			listFields.add(new ErrorModel.Field(((FieldError)error).getField(), messageSource.getMessage(error, LocaleContextHolder.getLocale())));
		}
		
		var errorModel = populateError(ex, "Check the invalid fields!", status, listFields);
		return super.handleExceptionInternal(ex, errorModel, headers, status, request);
	}
	
	private ErrorModel populateError(Exception ex, String message, HttpStatus status, List<ErrorModel.Field> listField) {
		var errorModel = new ErrorModel();
		
		errorModel.setStatus(status.value());
		errorModel.setOffsetDateTime(OffsetDateTime.now());
		errorModel.setTitle(null != message ? message : ex.getMessage());
		
		if(null != listField) {
			errorModel.setListFileds(listField);
		}
		return errorModel;
	}
	
}