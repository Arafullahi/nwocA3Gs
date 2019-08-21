package com.nwoc.a3gs.group.app.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nwoc.a3gs.group.app.dto.ServicesDTO;
import com.nwoc.a3gs.group.app.model.ServiceRates;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.services.ServicesService;

import javassist.NotFoundException;

@Controller
@RequestMapping("/api")
public class ServiceController {

	@Autowired
	private ServicesService servicesService;
	private static final Logger LOGGER = LogManager.getLogger(ServiceController.class);

	@PostMapping("/services")
	public ResponseEntity<ServicesDTO> createService(@RequestBody ServicesDTO services) {
		
		try {
			services = servicesService.save(services);
			return ResponseEntity.created(new URI("/services/"+services.getId().toString())).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	@PutMapping("/services/{serviceId}")
	public ResponseEntity<?> updateService(@PathVariable Long serviceId,  @RequestBody ServicesDTO services) {
		/*Optional<Services> servicesOpt= servicesService.findOne(serviceId);
		if(!servicesOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}*/
		
		 try {
			if(servicesService.updateService(services, serviceId)){
				 return new ResponseEntity<ServicesDTO>(services, HttpStatus.OK); 
			 }else{
				 return  ResponseEntity.badRequest().build(); 
			 }
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.notFound().build();
			
		}
		
	}
	
	@GetMapping("/services/main")
	public ResponseEntity<?> mainServices() {		
		 try {
			 List<Services> services= servicesService.findMainService();
			 return new ResponseEntity<List<Services>>(services, HttpStatus.OK); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
		
	}
	
	@GetMapping("/services/child/{parentId}")
	public ResponseEntity<?> findChildService(@PathVariable("parentId") Long serviceId) {		
		 try {
			 Set<Services> services= servicesService.findChildService(serviceId);
			 return new ResponseEntity<Set<Services>>(services, HttpStatus.OK); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		}
		
	}
	
	
	@GetMapping("/service/list")
	public ResponseEntity<Page<Services>> listServicesByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<Services> pages = servicesService.findServicesByPages(page, size);
			return ResponseEntity.ok(pages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
	
}
