package com.szakdoga.utils;

public interface EmailUtil {
	
	void sendSimpleMessage(String to, String subject, String text);
	
	//TODO:
	//void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
}