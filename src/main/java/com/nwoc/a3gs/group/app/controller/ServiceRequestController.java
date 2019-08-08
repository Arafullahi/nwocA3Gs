package com.nwoc.a3gs.group.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.nwoc.a3gs.group.app.dto.ServiceRatesDTO;
import com.nwoc.a3gs.group.app.dto.ServiceRequestsDTO;
import com.nwoc.a3gs.group.app.model.ServiceRates;
import com.nwoc.a3gs.group.app.model.ServiceRequests;
import com.nwoc.a3gs.group.app.model.ServiceStatus;
import com.nwoc.a3gs.group.app.model.Workers;
import com.nwoc.a3gs.group.app.repository.ServiceRequestRepository;
import com.nwoc.a3gs.group.app.services.ServiceRequestService;
import com.nwoc.a3gs.group.app.services.WorkerService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class ServiceRequestController {
	
	@Autowired
	ServiceRequestService serviceRequestService;
	@Autowired
	WorkerService workerService;

	private static final Logger LOGGER = LogManager.getLogger(ServiceRatesController.class);
	
	@PostMapping("/service/request")
	public ResponseEntity<?> createServiceRates(@Valid @RequestBody ServiceRequestsDTO serviceRatesDTO) {
		try {
			serviceRequestService.create(serviceRatesDTO);
			return ResponseEntity.ok("Service request created successfully.");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	//@GetMapping("/service/requests")
	public List<ServiceRequests> getAllSeviceRequest() {
		return serviceRequestService.findAll();
	}

	@GetMapping("/service/requests/{id}")
	public ResponseEntity<?> getServiceRequestById(@PathVariable(value = "id") Long id) {
		Optional<ServiceRequests> serviceRequests = serviceRequestService.findOne(id);
		if (!serviceRequests.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(serviceRequests.get());
	}
	
	

	@PutMapping("/service/request/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody ServiceRequestsDTO serviceRequestsDTO) {
		ServiceRequests serviceRequests = null;
		try {
			serviceRequests = serviceRequestService.update(serviceRequestsDTO, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(serviceRequests);
	}
	
	/*@DeleteMapping("/service/request/{id}")
	public ResponseEntity<?> deleteServiceRequest(@PathVariable(value = "id") Long id) {
		Optional<ServiceRequests> serviceRequests = serviceRequestService.findOne(id);
		if (!serviceRequests.isPresent()) {
			return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
		}

		serviceRequestService.delete(serviceRequests.get());
		return ResponseEntity.ok().body( "  Successfully Deleted");
	}*/
	
	@GetMapping("/service/requestlist")
	public ResponseEntity<Page<ServiceRequests>> listServiceRatesByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<ServiceRequests> reQuestPages = serviceRequestService.findServiceRequestByPages(page, size);
			return ResponseEntity.ok(reQuestPages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping("/service/request/{serviceRequId}/assign/{workerId}")
	public ResponseEntity<?> assignServiceToWorker(@PathVariable Long serviceRequestId,
			@PathVariable Long workerId) {
		try {
			Optional<ServiceRequests> serviceReqOpt= serviceRequestService.findOne(serviceRequestId);
			if(!serviceReqOpt.isPresent()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service Request not available");
			}
			if(serviceReqOpt.get().getServiceStatus().equals(ServiceStatus.SERVICE_COMPLETED)){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service Request completed");
			}
			
			Optional<Workers> workerOpt= workerService.findOne(workerId);
			if(!workerOpt.isPresent()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker not available");
			}
			
			ServiceRequests serviceRequests = serviceReqOpt.get();
			serviceRequests.setWorker(workerOpt.get());
			
			serviceRequestService.update(serviceRequests);
			return ResponseEntity.ok("Worker Assigned to Service request");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
