package com.fredastone.commons.logging;

import com.fredastone.commons.logging.bean.InterfaceLogParamsMandatory;
import com.fredastone.commons.logging.bean.InterfaceLogParamsOptional;

public interface InterfaceLog {
	
	void info(final InterfaceLogParamsMandatory mandatoryParam,InterfaceLogParamsOptional optionalParam);
	void warn(final InterfaceLogParamsMandatory mandatoryParam,InterfaceLogParamsOptional optionalParam);
	void error(final InterfaceLogParamsMandatory mandatoryParam,InterfaceLogParamsOptional optionalParam);
	

}
