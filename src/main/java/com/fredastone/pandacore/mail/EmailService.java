package com.fredastone.pandacore.mail;

import com.fredastone.pandacore.entity.User;

public interface EmailService{
	
	void sendPasswordResetRequest(String token, User user);
	
	void emailVarification(String token, User user);

}
