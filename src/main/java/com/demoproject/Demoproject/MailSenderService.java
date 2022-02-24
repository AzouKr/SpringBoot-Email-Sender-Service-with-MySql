package com.demoproject.Demoproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.*;
import org.springframework.mail.SimpleMailMessage;

@Service
public class MailSenderService{
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String[] emailIds, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("Your email");
		message.setTo(emailIds);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		System.out.println("email sent");
	}

}
