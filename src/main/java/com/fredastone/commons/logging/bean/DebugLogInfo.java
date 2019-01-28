package com.fredastone.commons.logging.bean;

import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DebugLogInfo  extends AbstractLogInfo{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7722526946826260940L;
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
		
		result.append(getLogTime()).append(PIPE)
		.append(StringUtil.dealNull(type)).append(PIPE)
		.append(StringUtil.dealNull(msisdn)).append(PIPE)
		.append(StringUtil.dealNull(transactionId)).append(PIPE)
		.append(StringUtil.dealNull(classFileName)).append(PIPE)
		.append(StringUtil.dealNull(classFileLine)).append(PIPE);
		
		if( !stackTrace.isEmpty()) {
			result.append("Content=[").append(stackTrace).append(']');
		}
		result.append("\n");
		
		return result.toString();
		
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFormattedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Throwable getThrowable() {
		// TODO Auto-generated method stub
		return null;
	}


}
