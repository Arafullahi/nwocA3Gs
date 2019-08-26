package com.nwoc.a3gs.group.app.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.nwoc.a3gs.group.app.dto.UserDTO;

import javassist.NotFoundException;

@Service
public class MailServiceImpl {
	private static final Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	private Environment env;
	

	
	
	 public boolean sendMail(UserDTO userDTO) throws NotFoundException {
		 boolean isSend =false;
		 LOGGER.debug("Sending User Creation Email...");
		 try {
				SimpleMailMessage message = new SimpleMailMessage();
				String from =env.getProperty("spring.mail.username"); 
		        message.setFrom(from);
		        message.setTo(userDTO.getEmail());
		        String subject = env.getProperty("user.creation.mail.subject");
		        message.setSubject(subject);
		        String body = env.getProperty("user.creation.mail.body");
		        message.setText(body); 
		        emailSender.send(message);
		        LOGGER.info("Email Successfully Send...");
		        isSend = true;
				
			} catch (MailException e) {
				LOGGER.error("Can't send email... " + e.getMessage(), e);
				e.printStackTrace();
			}
		 return isSend;
			
		
			}

}
