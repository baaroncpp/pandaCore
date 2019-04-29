package com.fredastone.pandacore.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.fredastone.pandacore.models.Fields;
import com.fredastone.pandacore.models.ValidationExceptionResponse;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class GlobalExceptionAdvice {
	
	@ExceptionHandler(value= {MethodArgumentNotValidException.class})
	public ResponseEntity<Object> constraintValidationException(Exception exception,WebRequest request) {
		
		
		if (exception instanceof MethodArgumentNotValidException) {
		
			BindingResult result = ((MethodArgumentNotValidException) exception).getBindingResult();
			
			
			 ValidationExceptionResponse response  = ValidationExceptionResponse.builder()
					  .message(processFieldErrors(result.getFieldErrors()))
					  .status(400)
					  .error("Invalid Arguments Provided in Request").build();
		      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		      
		}else
		{
			ValidationExceptionResponse response  = ValidationExceptionResponse.builder()
					  .message(null)
					  .status(500)
					  .error(exception.getMessage()).build();
		      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> transactionSystemException(Exception exception,WebRequest request) {
		
			exception.printStackTrace();
			
			ValidationExceptionResponse response  = ValidationExceptionResponse.builder()
					  .status(500)
					  .error(exception.getMessage()).build();
		      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
	
	}
	
	
	

	 private List<Fields> processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
	      	List<Fields> errors = new ArrayList<>();
	        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
	           errors.add(new Fields(fieldError.getField(),fieldError.getDefaultMessage()));
	        }
	        return errors;
	    }
}
