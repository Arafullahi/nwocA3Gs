package com.nwoc.a3gs.group.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nwoc.a3gs.group.app.dto.CandidatesDTO;
import com.nwoc.a3gs.group.app.model.Candidates;
import com.nwoc.a3gs.group.app.services.CandidatesServiceImpl;

@RestController
@RequestMapping("/api")
public class CandidatesController {
	
	@Autowired
	CandidatesServiceImpl candidateService;
	private static final Logger LOGGER = LogManager.getLogger(CandidatesController.class);
	
	@PostMapping(value="/candidates/create",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCandidate(@ModelAttribute @Valid CandidatesDTO candidatesDTO) {
		try {
			if(candidateService.save(candidatesDTO)) {		
				return ResponseEntity.ok("Candidate Insertion successfully.");	
			}
              else {
            	  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Candidates Registration Failed.");
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping(value="/candidates/all", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Candidates> getAllCandidates() {
		return candidateService.findAll();
	}
	
	@GetMapping(value="/candidates/{candidate_id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCandidateById(@PathVariable(value = "candidate_id") Long candidate_id) {
		try {
		Optional<Candidates> candidateOpt = candidateService.findOne(candidate_id);
		if (!candidateOpt.isPresent()) {
			return ((BodyBuilder) ResponseEntity.notFound()).body("Candidate Not Found");
		}
		return ResponseEntity.ok().body(candidateOpt);
	}
	catch(Exception e) {

		LOGGER.error(e.getMessage(), e);
		System.out.println(e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).build();

	}
}
	
	@PutMapping(value="/candidates/{candidate_id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCandidate(@ModelAttribute @Valid CandidatesDTO candidatesDTO, @PathVariable(value = "candidate_id") Long candidate_id) {
		try {
		if(candidateService.update(candidatesDTO, candidate_id))
		{
			return ResponseEntity.ok("Candidate Updation successfully.");	
		}
		else
		{
			 return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Candidates Updation Failed.");
		}
		
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@DeleteMapping(value="/candidates/{candidate_id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCandidate(@PathVariable(value = "candidate_id") Long candidate_id) {
		try {
		Optional<Candidates> candidateOpt = candidateService.findOne(candidate_id);
		if (!candidateOpt.isPresent()) {
			return ((BodyBuilder) ResponseEntity.notFound()).body("Candidate  Not Found");
		}

		candidateService.delete(candidateOpt.get());
		return ResponseEntity.ok().body(candidateOpt.get().getFirst_name() + "  Successfully Deleted");
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping(value="/candidates/list",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Candidates>> listCandidatesByPages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {

			Page<Candidates> candidatePages = candidateService.findCandidatesByPages(page, size);
			return ResponseEntity.ok(candidatePages);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
