package com.nwoc.a3gs.group.app.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.nwoc.a3gs.group.app.dto.UserDTO;
import com.nwoc.a3gs.group.app.model.MailService;
import com.nwoc.a3gs.group.app.repository.MailRepository;

import javassist.NotFoundException;

@Service
public class MailServiceImpl {
	private static final Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
	@Autowired
	MailRepository mailRepository;
	
	@Autowired
    private JavaMailSender emailSender;
	
	
	public List<MailService> findAll() {
		return mailRepository.findAll();
	}
	
	
	 public boolean sendMail(UserDTO userDTO) throws NotFoundException {
		 boolean isSend =false;
		 LOGGER.debug("Sending User Creation Email...");
		 try {
				SimpleMailMessage message = new SimpleMailMessage();
				List<MailService> mail = mailRepository.findAll();
				for(MailService mailService: mail) {
		        message.setFrom(mailService.getFromaddress());
		        message.setTo(userDTO.getEmail());
		        message.setSubject(mailService.getSubject());
		        message.setText(mailService.getContent()); 
				}
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
