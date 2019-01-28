package com.fredastone.commons.logging.bean;


import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InterfaceLogInfo extends AbstractLogInfo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1086109958974792078L;
	private @Getter final InterfaceLogParamsMandatory mandatoryParam;
	private @Getter final InterfaceLogParamsOptional optionParam;

	
	 @Override
	    public String toString() {
	        final StringBuilder result = new StringBuilder();
	        
	        result.append(getLogTime()).append(PIPE)
	        .append(StringUtil.dealNull(this.mandatoryParam.getInterfaceType())).append(PIPE)
	        .append(StringUtil.dealNull(this.mandatoryParam.getInterfaceName())).append(PIPE)
	        .append(StringUtil.dealNull(this.mandatoryParam.getSourceDevice())).append(PIPE)
	        .append(StringUtil.dealNull(this.mandatoryParam.getDestDevice())).append(PIPE)
	        .append(StringUtil.dealNull(this.optionParam.getTraceUniqueID())).append(PIPE)
	        .append(StringUtil.dealNull(this.optionParam.getTransactionID())).append(PIPE)
	        .append(StringUtil.dealNull(this.mandatoryParam.getReturnCode())).append(PIPE)
	        .append(StringUtil.dealNull(this.mandatoryParam.getReturnDesc())).append(PIPE)
	        .append(StringUtil.dealNull(this.optionParam.getAccountId())).append(PIPE)
	        .append(StringUtil.dealNull(this.optionParam.getMsisdn())).append(PIPE)
	        .append(StringUtil.dealNull(this.optionParam.getExtendedInfo()))
	        .append("Content= [");
	      
	        
	        if (null != this.mandatoryParam.getInterfaceMessage()) {
	            result.append(this.mandatoryParam.getInterfaceMessage());
	        }
	        result.append("]\n");
	        
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
