package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.nwoc.a3gs.group.app.model.WorkerRates;
import com.nwoc.a3gs.group.app.repository.WorkerRatesRepository;

import javassist.NotFoundException;

@Service
public class WorkerRatesServiceImpl {
	
	@Autowired
	WorkerRatesRepository workerRatesRepository;

	@Autowired
	ServicesService servicesService;
	
	@Autowired
	WorkerService workerService;

	@Transactional
	public WorkerRates create(WorkerRates workerRates) {
		//WorkerRates workerRates = new WorkerRates();
		//BeanUtils.copyProperties(workerRatesDTO, workerRates);
		return workerRatesRepository.save(workerRates);
	}

	public List<WorkerRates> findAll() {
		return workerRatesRepository.findAll();
	}

	public Optional<WorkerRates> findOne(Long id) {
		return workerRatesRepository.findById(id);
	}

	public WorkerRates update(WorkerRates workerRates, Long id) throws NotFoundException {
		Optional<WorkerRates> rateOpt =findOne(id);
		if(rateOpt.isPresent()){
			WorkerRates serRate = rateOpt.get();
			serRate.setDescription(workerRates.getDescription());
			serRate.setRate(workerRates.getRate());
			serRate.setUnit(workerRates.getUnit());
		    serRate.setServices(workerRates.getServices());
			serRate.setWorkers(workerRates.getWorkers());
			return workerRatesRepository.saveAndFlush(serRate);
		}else{
			throw new NotFoundException("User not found exception");
		}

	}

	public void delete(WorkerRates workerRates) {
		workerRatesRepository.delete(workerRates);
	}

	public Page<WorkerRates> findWorkerRatesByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return workerRatesRepository.findAll(pageable);
	}



}
