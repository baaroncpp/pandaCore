package com.fredastone.pandacore.mail;

import java.io.File;
import java.io.FileNotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fredastone.pandacore.entity.User;

@Service
public class EmailServiceImp implements EmailService{
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Autowired
    private JavaMailSender emailSender;
	
    
	/*
	 * public EmailServiceImp(JavaMailSender emailSender) { this.emailSender =
	 * emailSender; }
	 */
	
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
 
    public void sendSimpleMessage(String email, String subject, String body, File file) throws MessagingException {
    	
    	MimeMessage pandamail = emailSender.createMimeMessage();
	
		MimeMessageHelper helper = new MimeMessageHelper(pandamail, true, CharEncoding.UTF_8);
		try {
			helper.setText(body, true);
			helper.setFrom(fromMail);
			helper.setSubject(subject);
			helper.setTo(email);
			helper.addAttachment("payment.html", file);
			emailSender.send(pandamail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
    }
    
    public void sendPasswordResetRequest(String token, User user) {
    	  
    	String htmlBodyWithToken = PASSWORD_RESET_HTMLBODY.replace("$tokenValue", token);
    	htmlBodyWithToken = htmlBodyWithToken.replace("$username", user.getFirstname()+" "+user.getLastname());
    	
    	String subject = "Password Reset";
    	
    	//sendSimpleMessage(user.getEmail(), subject, htmlBodyWithToken); 
    	
    }
    
    public void emailVarification(String token, User user) {
    	String htmlBodyWithToken = EMAIL_VARIFICATION_HTMLBODY.replace("$tokenValue", token);
    	htmlBodyWithToken = htmlBodyWithToken.replace("$username", user.getFirstname()+" "+user.getLastname());
    	
    	String subject = "Welcome to PANDA SOLAR";
    	
    	String fileName = "reports/payments.html";
        ClassLoader classLoader = new EmailServiceImp().getClass().getClassLoader();
 
    	
    	//File file = new File(classLoader.getResource("dailypaymentsreport.jrxml").getFile());
        File file = null;
		try {
			file = ResourceUtils.getFile("classpath:dailypaymentsreport.jrxml");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			sendSimpleMessage(user.getEmail(), subject, htmlBodyWithToken, file);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

}
