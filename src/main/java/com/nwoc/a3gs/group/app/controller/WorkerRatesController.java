package com.nwoc.a3gs.group.app.controller;

import java.util.List;
import java.util.Optional;

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

import com.nwoc.a3gs.group.app.model.WorkerRates;
import com.nwoc.a3gs.group.app.services.WorkerRatesServiceImpl;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class WorkerRatesController {
	
	@Autowired
	WorkerRatesServiceImpl workerRatesImpl;
	private static final Logger LOGGER = LogManager.getLogger(WorkerRatesController.class);

	@PostMapping("/worker/rates")
	public ResponseEntity<?> createWorkerRates(@RequestBody WorkerRates workerRates) {
		try {
			 workerRates = workerRatesImpl.create(workerRates);
			return ResponseEntity.ok(workerRates);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping("/worker/rates")
	public List<WorkerRates> getAllUsers() {
		return workerRatesImpl.findAll();
	}

	@GetMapping("/worker/rates/{id}")
	public ResponseEntity<?> getWorkerRateById(@PathVariable(value = "id") Long id) {
		Optional<WorkerRates> workerRate = workerRatesImpl.findOne(id);
		if (!workerRate.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(workerRate.get());
	}


	@PutMapping("/worker/rates/{id}")
	public ResponseEntity<?> updateWorkerRates(@PathVariable(value = "id") Long id, @RequestBody WorkerRates workerRates) {
		WorkerRates workerRateUpdate = null;
		try {
			workerRateUpdate = workerRatesImpl.update(workerRates, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(workerRateUpdate);
	}

	@DeleteMapping("/worker/rates/{id}")
	public ResponseEntity<?> deleteWorkerRates(@PathVariable(value = "id") Long id) {
		Optional<WorkerRates> workerRates = workerRatesImpl.findOne(id);
		if (!workerRates.isPresent()) {
			return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
			
		}

		workerRatesImpl.delete(workerRates.get());
		return ResponseEntity.ok().body(workerRates.get().getRate() + "  Successfully Deleted");
	}

	@GetMapping("/worker/rates/workerRatelist")
	public ResponseEntity<Page<WorkerRates>> listWorkerServiceRatesByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<WorkerRates> ratePages = workerRatesImpl.findWorkerRatesByPages(page, size);
			return ResponseEntity.ok(ratePages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}


}
