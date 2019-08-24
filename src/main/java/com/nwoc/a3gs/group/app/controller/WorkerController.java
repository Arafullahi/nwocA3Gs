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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nwoc.a3gs.group.app.dto.WorkersDTO;
import com.nwoc.a3gs.group.app.model.Workers;
import com.nwoc.a3gs.group.app.services.ServicesService;
import com.nwoc.a3gs.group.app.services.WorkerService;

@RestController
public class WorkerController {

	@Autowired
	private WorkerService workerService;
	private static final Logger LOGGER = LogManager.getLogger(WorkerController.class);
	
	@Autowired
	private ServicesService servicesService;
	
	@PostMapping(value="/services/workers",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addWorkers(@RequestBody WorkersDTO workersDTO) {
		try {
			Workers workers = workerService.save(workersDTO);
			return ResponseEntity.ok(workers);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
	
	
	@GetMapping(value="/services/worker/{worker_id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWorkerById(@PathVariable(value = "worker_id") Long worker_id) {
		try {
		Optional<Workers> workerOpt = workerService.findOne(worker_id);
		if (!workerOpt.isPresent()) {
			return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
		}
		return ResponseEntity.ok().body(workerOpt.isPresent());
	}
	catch(Exception e) {

		LOGGER.error(e.getMessage(), e);
		System.out.println(e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).build();

	}
}


	@PutMapping(value="/services/workers/{worker_id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateWorker(@PathVariable(value = "worker_id") Long worker_id, @RequestBody WorkersDTO workersDTO) {
		try {
		Workers workerUpdate = workerService.update(workersDTO, worker_id);
		return ResponseEntity.ok().body(workerUpdate);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@DeleteMapping(value="/services/workers/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteWorker(@PathVariable(value = "id") Long id) {
		try {
		Optional<Workers> workerOpt = workerService.findOne(id);
		if (!workerOpt.isPresent()) {
			return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
		}

		workerService.delete(workerOpt.get());
		return ResponseEntity.ok().body(workerOpt.get().getName() + "  Successfully Deleted");
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping(value="/services/workerslist",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Workers>> listWorkerByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<Workers> workerPages = workerService.findWorkerByPages(page, size);
			return ResponseEntity.ok(workerPages);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	@GetMapping(value="/services/workers/{serviceId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Workers>> findWorkersByServiceId(@PathVariable(value = "serviceId") Long serviceId) {
		try {

			List<Workers> workers = workerService.findWorkersByServiceId(serviceId);
			return ResponseEntity.ok(workers);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
}
