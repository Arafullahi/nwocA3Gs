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

import com.nwoc.a3gs.group.app.dto.WorkerRatesDTO;
import com.nwoc.a3gs.group.app.model.WorkerRates;
import com.nwoc.a3gs.group.app.services.WorkerRatesServiceImpl;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class WorkerRatesController {
	
	@Autowired
	WorkerRatesServiceImpl workerRatesImpl;
	private static final Logger LOGGER = LogManager.getLogger(WorkerRatesController.class);

	@PostMapping(value="/worker/rates",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createWorkerRates(@RequestBody WorkerRatesDTO workerRatesDTO) {
		try {
			 WorkerRates workerRates = workerRatesImpl.create(workerRatesDTO);
			return ResponseEntity.ok(workerRates);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value="/worker/rates",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WorkerRates> getAllUsers() {
		try {
			return workerRatesImpl.findAll();
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return (List<WorkerRates>) ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}

	@GetMapping(value="/worker/rates/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWorkerRateById(@PathVariable(value = "id") Long id) {
		try {
			Optional<WorkerRates> workerRate = workerRatesImpl.findOne(id);
			if (!workerRate.isPresent()) {
				//return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				return ((BodyBuilder) ResponseEntity.notFound()).body("Worker Rate Not Found");
			}
			return ResponseEntity.ok().body(workerRate.get());
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}


	@PutMapping(value="/worker/rates/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateWorkerRates(@PathVariable(value = "id") Long id, @RequestBody WorkerRatesDTO workerRatesDTO) {
		WorkerRates workerRateUpdate = null;
		try {
			workerRateUpdate = workerRatesImpl.update(workerRatesDTO, id);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok().body(workerRateUpdate);
	}

	@DeleteMapping(value="/worker/rates/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteWorkerRates(@PathVariable(value = "id") Long id) {
		try {
			Optional<WorkerRates> workerRates = workerRatesImpl.findOne(id);
			if (!workerRates.isPresent()) {
				return ((BodyBuilder) ResponseEntity.notFound()).body("User Not Found");
				
			}

			workerRatesImpl.delete(workerRates.get());
			return ResponseEntity.ok().body(workerRates.get().getRate() + "  Successfully Deleted");
		}catch (Exception e) {
			
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		}
		
	}

	@GetMapping(value="/worker/rates/workerRatelist",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<WorkerRates>> listWorkerServiceRatesByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<WorkerRates> ratePages = workerRatesImpl.findWorkerRatesByPages(page, size);
			return ResponseEntity.ok(ratePages);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}


}
