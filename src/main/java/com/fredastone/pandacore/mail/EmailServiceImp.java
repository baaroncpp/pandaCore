package com.fredastone.pandacore.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.entity.User;

@Service
public class EmailServiceImp implements EmailService{
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
    private JavaMailSender emailSender;
	
    @Autowired
	public EmailServiceImp(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	private String PASSWORD_RESET_HTMLBODY = ""
			+ "<h1>Request to reset password</h1>"
			+ "<p>Hi $username</p>"
			+ "<p>Someone has requested to reset your password with "
			+ "PANDA SOLAR. If it was not you, please ignore it, otherwise "
			+ "click on the link bellow.<br/>"
			+ "<a href='?token=$tokenValue'>Click this to reset Password<br/><br/></a>"
			+ "thank you </p>";
	
	private String EMAIL_VARIFICATION_HTMLBODY = ""
			+ "<h1>Welcome to PANDA SOLAR</h1>"
			+ "<p>Hi $username</p>"
			+ "<p><br/>"
			+ "Click on the link bellow to login into your account.</br>"
			+ "<a href='?token=$tokenValue'>Click this to login<br/><br/></a>"
			+ "thank you"
			+ "</p>";
 
    public void sendSimpleMessage(String email, String subject, String body) {
    	
    	MimeMessage pandamail = emailSender.createMimeMessage();
	
		MimeMessageHelper helper = new MimeMessageHelper(pandamail);
		try {
			helper.setText(body, true);
			helper.setFrom(fromMail);
			helper.setSubject(subject);
			helper.setTo(email);
			emailSender.send(pandamail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
    }
    
    public void sendPasswordResetRequest(String token, User user) {
    	  
    	String htmlBodyWithToken = PASSWORD_RESET_HTMLBODY.replace("$tokenValue", token);
    	htmlBodyWithToken = htmlBodyWithToken.replace("$username", user.getFirstname()+" "+user.getLastname());
    	
    	String subject = "Password Reset";
    	
    	sendSimpleMessage(user.getEmail(), subject, htmlBodyWithToken); 
    	
    }
    
    public void emailVarification(String token, User user) {
    	String htmlBodyWithToken = EMAIL_VARIFICATION_HTMLBODY.replace("$tokenValue", token);
    	htmlBodyWithToken = htmlBodyWithToken.replace("$username", user.getFirstname()+" "+user.getLastname());
    	
    	String subject = "Welcome to PANDA SOLAR";
    	
    	sendSimpleMessage(user.getEmail(), subject, htmlBodyWithToken); 
    }

}
