package com.fredastone.commons.logging.bean;

import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DebugLogInfo  extends AbstractLogInfo{
	
	
	@Getter @Setter private String timeStamp;
	@Getter @Setter private String type;
	@Getter @Setter private String msisdn;
	@Getter @Setter private String transactionId;
	@Getter @Setter private String traceUniqueID;
	@Getter @Setter private String classFileName;
	@Getter @Setter private String classFileLine;
	@Getter @Setter private String stackTrace;

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		
		result.append(StringUtil.dealNull(type)).append(PIPE)
		.append(StringUtil.dealNull(msisdn)).append(PIPE)
		.append(StringUtil.dealNull(transactionId)).append(PIPE)
		.append(StringUtil.dealNull(classFileName)).append(PIPE)
		.append(StringUtil.dealNull(classFileLine)).append(PIPE)
		.append(StringUtil.dealNull(timeStamp)).append(PIPE);
		
		if( !stackTrace.isEmpty()) {
			result.append('[').append(stackTrace).append(']');
		}
		
		return result.toString();
		
	}

}
