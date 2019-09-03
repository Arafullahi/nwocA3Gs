package com.nwoc.a3gs.group.app.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nwoc.a3gs.group.app.controller.UserController;
import com.nwoc.a3gs.group.app.dto.CandidatesDTO;
import com.nwoc.a3gs.group.app.model.Candidates;
import com.nwoc.a3gs.group.app.repository.CandidatesRepository;

import javassist.NotFoundException;

@Service
public class CandidatesServiceImpl {
	
	private static final Logger LOGGER = LogManager.getLogger(CandidatesServiceImpl.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private CandidatesRepository candidatesRepository;
	
	@Transactional
	public boolean save(CandidatesDTO candidatesDTO) throws ParseException {
		boolean isSave = false;
		Candidates candidates= new Candidates();
		Date dateDOB=new SimpleDateFormat("dd-MM-yyyy").parse(candidatesDTO.getDob()); 
		BeanUtils.copyProperties(candidatesDTO, candidates);
		candidates.setDob(dateDOB);
		MultipartFile file = candidatesDTO.getFiles();
		if (file != null) {
			String filePath = fileStorageService.storeFileInAPath(file);
			candidates.setResume_file(filePath);
			candidatesDTO.setFiles(null);
			candidatesRepository.save(candidates);
			isSave = true;
			 LOGGER.info("Candidate  Creation Successfully");
		}
		else
		{
			 LOGGER.error("Candidates creation is failed");
			return isSave;
		}
		return isSave;
	}
	
	public List<Candidates> findAll() {
		return candidatesRepository.findAll();
	}
	
	public Optional<Candidates> findOne(Long candidate_id) {
		return candidatesRepository.findById(candidate_id);
	}
	
	public boolean update(CandidatesDTO candidatesDTO, Long candidate_id) throws NotFoundException, ParseException {
		boolean isUpdated=false;
		Date dateDOB=new SimpleDateFormat("dd-MM-yyyy").parse(candidatesDTO.getDob()); 
		Optional<Candidates> candidateOpt= findOne(candidate_id);
		if(!candidateOpt.isPresent()){
			throw new NotFoundException("Candidate not found");
		}
		Candidates candidates= candidateOpt.get();
		BeanUtils.copyProperties(candidatesDTO, candidates);
		candidates.setDob(dateDOB);
		candidates.setCandidate_id(candidate_id);
		MultipartFile file = candidatesDTO.getFiles();
		if (file != null) {
			String filePath = fileStorageService.storeFileInAPath(file);
			candidates.setResume_file(filePath);
			candidatesDTO.setFiles(null);
		}
		candidatesRepository.save(candidates);
		isUpdated=true;
		return isUpdated;
	}
	
	public void delete(Candidates candidates) {
		candidatesRepository.delete(candidates);
	}

	public Page<Candidates> findCandidatesByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);
		return candidatesRepository.findAll(pageable);
	}
}
