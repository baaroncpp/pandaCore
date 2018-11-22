package com.fredastone.commons.logging.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
public class InterfaceLogParamsMandatory implements Serializable{
	
     private static final long serialVersionUID = -5938555875477524252L;
     @Getter private final String requestFlow;
     @Getter private final String interfaceType;
     @Getter private final String interfaceName;
     @Getter private final String sourceDevice;
     @Getter private final String destDevice;
     
     @Getter @Setter private String returnCode;
     @Getter @Setter  private String returnDesc;
     @Getter @Setter private Object interfaceMessage;
     @Getter @Setter private String requestOrResponse;
    

}

