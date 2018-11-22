package com.fredastone.commons.logging.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class InterfaceLogParamsOptional  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 408548690140757847L;
	private @Getter @Setter String transactionID; // TransactionId from 3rd party system
    private @Getter @Setter String msisdn; //Msisdn doing transaction
    private @Getter @Setter String accountId; // Account ID involved in this transaction
    private @Getter @Setter String extendedInfo; // Any other additional Details
    private @Getter @Setter String traceUniqueID; // If we must include any unique tracing Id    
 
}
