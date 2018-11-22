package com.fredastone.commons.logging.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RuntimeLogInfo {

	@Getter @Setter String timestamp;
	@Getter @Setter String operation;
	@Getter @Setter String traceUniqueId;
	@Getter @Setter String message;
	
	
}
