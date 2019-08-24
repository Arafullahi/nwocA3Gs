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

import com.nwoc.a3gs.group.app.dto.ServiceCommentsDTO;
import com.nwoc.a3gs.group.app.model.ServiceComments;
import com.nwoc.a3gs.group.app.services.ServiceCommentsServiceImpl;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class ServiceCommentsController {
	
	@Autowired
	ServiceCommentsServiceImpl serviceCommentsServiceImpl;
	private static final Logger LOGGER = LogManager.getLogger(ServiceCommentsController.class);
	
	@PostMapping(value="/service/comment",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createServiceComment(@RequestBody ServiceCommentsDTO serviceCommentsDTO) {
		try {
			ServiceComments serviceComments = serviceCommentsServiceImpl.create(serviceCommentsDTO);
			return ResponseEntity.ok(serviceComments);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/service/comment",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceComments> getAllComment() {
		try {
			return serviceCommentsServiceImpl.findAll();
		} catch(Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return (List<ServiceComments>) ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}
		
	}

	@GetMapping(value="/service/comment/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCommentById(@PathVariable(value = "id") Long id) {
		try {
			Optional<ServiceComments> serviceComments = serviceCommentsServiceImpl.findOne(id);
			if (!serviceComments.isPresent()) {
				return ((BodyBuilder) ResponseEntity.notFound()).body("Service Comments Not Found");
			}
			return ResponseEntity.ok().body(serviceComments.get());
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}

	
	@PutMapping(value="/service/comment/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateServiceComment(@PathVariable(value = "id") Long id, @RequestBody ServiceCommentsDTO serviceCommentsDTO) {
		ServiceComments serviceComments = null;
		try {
			serviceComments = serviceCommentsServiceImpl.update(serviceCommentsDTO, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(serviceComments);
	}
	
	@DeleteMapping(value="/service/comment/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteServiceComment(@PathVariable(value = "id") Long id) {
		try {
			
			Optional<ServiceComments> serviceComments = serviceCommentsServiceImpl.findOne(id);
			if (!serviceComments.isPresent()) {
				return ((BodyBuilder) ResponseEntity.notFound()).body("Service Comment Not Found");
			}

			serviceCommentsServiceImpl.delete(serviceComments.get());
			return ResponseEntity.ok().body(serviceComments.get().getId() + "  Successfully Deleted");
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}
		
	}
	
	@GetMapping(value="/service/commentList",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ServiceComments>> listServiceCommentByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<ServiceComments> serviceComments = serviceCommentsServiceImpl.findServiceCommentByPages(page, size);
			return ResponseEntity.ok(serviceComments);
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}


	

}
