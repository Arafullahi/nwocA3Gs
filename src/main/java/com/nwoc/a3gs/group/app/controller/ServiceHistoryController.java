package com.nwoc.a3gs.group.app.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nwoc.a3gs.group.app.dto.ServiceHistoryDTO;
import com.nwoc.a3gs.group.app.model.ServiceHistory;
import com.nwoc.a3gs.group.app.services.ServiceHistoryServiceImpl;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class ServiceHistoryController {
	
	@Autowired
	ServiceHistoryServiceImpl serviceHistoryServiceImpl;
	private static final Logger LOGGER = LogManager.getLogger(ServiceHistoryController.class);
	
	@PostMapping(value="/service/history",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createServiceHistory(@RequestBody ServiceHistoryDTO serviceHistoryDTO) {
		try {
			ServiceHistory serviceHistory = serviceHistoryServiceImpl.create(serviceHistoryDTO);
			return ResponseEntity.ok(serviceHistory);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/service/history",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceHistory> getAllHistory() {
		try {
			return serviceHistoryServiceImpl.findAll();
		} catch(Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return (List<ServiceHistory>) ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}
		
	}

	@GetMapping(value="/service/history/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHistoryById(@PathVariable(value = "id") Long id) {
		try {
			Optional<ServiceHistory> serviceHistory = serviceHistoryServiceImpl.findOne(id);
			if (!serviceHistory.isPresent()) {
				//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No History Found");
				return ((BodyBuilder) ResponseEntity.notFound()).body("Service History Not Found");
			}
			return ResponseEntity.ok().body(serviceHistory.get());
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}

	
	@PutMapping(value="/service/history/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateServiceHistory(@PathVariable(value = "id") Long id, @RequestBody ServiceHistoryDTO ServiceHistoryDTO) {
		ServiceHistory serviceHistory = null;
		try {
			serviceHistory = serviceHistoryServiceImpl.update(ServiceHistoryDTO, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(serviceHistory);
	}
	
	@DeleteMapping(value="/service/history/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteServiceHistory(@PathVariable(value = "id") Long id) {
		try {
			
			Optional<ServiceHistory> serviceHistory = serviceHistoryServiceImpl.findOne(id);
			if (!serviceHistory.isPresent()) {
				return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
			}

			serviceHistoryServiceImpl.delete(serviceHistory.get());
			return ResponseEntity.ok().body(serviceHistory.get().getRate() + "  Successfully Deleted");
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}
		
	}
	
	@GetMapping(value="/service/historylist",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ServiceHistory>> listServiceHistoryByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<ServiceHistory> ratePages = serviceHistoryServiceImpl.findServiceHistoryByPages(page, size);
			return ResponseEntity.ok(ratePages);
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}


}
