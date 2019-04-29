package com.fredastone.pandacore.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationExceptionResponse {

	private String timestamp;
	private int status;
	private String error;
	private List<Fields> message;
	
}
