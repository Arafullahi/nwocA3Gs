package com.nwoc.a3gs.group.app.controller;

import java.net.URI;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nwoc.a3gs.group.app.dto.ServicesDTO;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.services.ServicesService;

import javassist.NotFoundException;

@Controller
public class ServiceController {

	@Autowired
	private ServicesService servicesService;
	private static final Logger LOGGER = LogManager.getLogger(ServiceController.class);

	@PostMapping("/services")
	public ResponseEntity<ServicesDTO> createService(@ModelAttribute ServicesDTO services) {
		
		try {
			services = servicesService.save(services);
			return ResponseEntity.created(new URI("/services/"+services.getId().toString())).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	@PostMapping("/services/{serviceId}")
	public ResponseEntity<?> updateService(@PathVariable Long serviceId,  @ModelAttribute ServicesDTO services) {
		Optional<Services> servicesOpt= servicesService.findOne(serviceId);
		if(!servicesOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		 try {
			if(servicesService.updateService(services)){
				 return new ResponseEntity<ServicesDTO>(services, HttpStatus.OK); 
			 }else{
				 return  ResponseEntity.badRequest().build(); 
			 }
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.notFound().build();
			
		}
		
	}
}
