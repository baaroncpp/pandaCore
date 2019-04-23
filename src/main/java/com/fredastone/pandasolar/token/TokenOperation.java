package com.fredastone.pandasolar.token;

import com.fredastone.pandasolar.token.exception.UnsupportedNumberOfDaysError;
import com.fredastone.pandasolar.token.exception.UnsupportedSerialException;

public class TokenOperation {
	
	public String generateGeneralPurposeToken(String deviceSerial,CommandNames command,int times) {
		
		checkInputValidity(deviceSerial, times);
		
		String token = null;
		
		switch (command) {
		//Block device
		case DEMOLISH:			
			token = TokenGenerator.generateToken("1", CommandNames.DEMOLISH, times, deviceSerial);
			break;
			//Unlock blocked device
		case UNLOCK:			
			token = TokenGenerator.generateToken("1", CommandNames.UNLOCK, times, deviceSerial);
			break;
			// Unlock device after loan is cleard
		case CLEAR_LOAN:
			token = TokenGenerator.generateToken("1", CommandNames.CLEAR_LOAN, times, deviceSerial);
			break;
			//Reset device back to factory settings after error
		case RESET:
			token = TokenGenerator.generateToken("1", CommandNames.RESET, times, deviceSerial);
			break;
		default:
			break;
		}

		return token;
		
	}
	
	public String generatePaymentToken(String deviceSerial,int times,String days) {
		
		checkInputValidity(deviceSerial, times);
		
		if(Integer.valueOf(days) < 1  ) {
			throw new UnsupportedNumberOfDaysError();
		}
				
		return TokenGenerator.generateToken(days, CommandNames.PAY, times, deviceSerial);
		
	}
	
	private void checkInputValidity(String deviceSerial,int times) {
		
		if(deviceSerial.isEmpty()) {
			throw new UnsupportedSerialException("Device Serial cannot be empty");
		}
		
		if(deviceSerial.length() != 15) {
			throw new UnsupportedSerialException("Device serial length must be equal to 15");
		}
		
		if(times < 1  ) {
			throw new UnsupportedSerialException("Generation times for serial cannot be less than 1");
		}
	}

}
