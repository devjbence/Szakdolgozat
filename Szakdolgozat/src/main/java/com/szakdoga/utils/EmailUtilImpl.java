package com.szakdoga.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.szakdoga.exceptions.EmailSendingException;

@Component
public class EmailUtilImpl implements EmailUtil {

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try 
		{
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text,true);
		} 
		catch (MessagingException e) 
		{
			throw new EmailSendingException(e.getLocalizedMessage());
		}

		emailSender.send(message);
	}
}
