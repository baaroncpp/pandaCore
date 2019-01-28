package com.fredastone.commons.logging.bean;

import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class RuntimeLogInfo extends AbstractLogInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter @Setter String operation;
	@Getter @Setter String traceUniqueId;
	@Getter @Setter String message;
	
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
	
	 
	 @Override
	public String toString() {
		 
		 final StringBuilder result = new StringBuilder();
		 
		 result.append(getLogTime()).append(PIPE)
		  .append(StringUtil.dealNull(operation)).append(PIPE)
		 .append(StringUtil.dealNull(traceUniqueId)).append(PIPE)
		 .append(StringUtil.dealNull(message)).append(PIPE)
		 .append("\n");
		 
		 return result.toString();
	}


}
