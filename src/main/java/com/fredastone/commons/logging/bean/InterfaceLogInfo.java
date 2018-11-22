package com.fredastone.commons.logging.bean;


import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class InterfaceLogInfo {
	
	private @Getter final InterfaceLogParamsMandatory mandatoryParam;
	private @Getter final InterfaceLogParamsOptional optionParam;

	
	 @Override
	    public String toString() {
	        final StringBuilder result = new StringBuilder();
	        
	        result.append(StringUtil.dealNull(this.mandatoryParam.getInterfaceType())).append('|')
	        .append(StringUtil.dealNull(this.mandatoryParam.getInterfaceName())).append('|')
	        .append(StringUtil.dealNull(this.mandatoryParam.getSourceDevice())).append('|')
	        .append(StringUtil.dealNull(this.mandatoryParam.getDestDevice())).append('|')
	        .append(StringUtil.dealNull(this.optionParam.getTraceUniqueID())).append('|')
	        .append(StringUtil.dealNull(this.optionParam.getTransactionID())).append('|')
	        .append(StringUtil.dealNull(this.mandatoryParam.getReturnCode())).append('|')
	        .append(StringUtil.dealNull(this.mandatoryParam.getReturnDesc())).append('|')
	        .append(StringUtil.dealNull(this.optionParam.getAccountId())).append('|')
	        .append(StringUtil.dealNull(this.optionParam.getMsisdn())).append('|')
	        .append(StringUtil.dealNull(this.optionParam.getExtendedInfo()))
	        .append(System.getProperty("line.separator")).append("Content=");
	        
	        if (null != this.mandatoryParam.getInterfaceMessage()) {
	            result.append(this.mandatoryParam.getInterfaceMessage());
	        }
	        
	        return result.toString();
	    }

}
