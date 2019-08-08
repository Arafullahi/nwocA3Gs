package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nwoc.a3gs.group.app.model.ServiceHistory;
import com.nwoc.a3gs.group.app.repository.ServiceHistoryRepository;

import javassist.NotFoundException;

@Service
public class ServiceHistoryServiceImpl {
	
	@Autowired
	ServiceHistoryRepository serviceHistoryRepository;
	
	@Transactional
	public ServiceHistory create(ServiceHistory serviceHistory) {
		return serviceHistoryRepository.save(serviceHistory);
	}
	
	public List<ServiceHistory> findAll() {
		return serviceHistoryRepository.findAll();
	}
	
	public Optional<ServiceHistory> findOne(Long id) {
		return serviceHistoryRepository.findById(id);
	}
	
	public ServiceHistory update(ServiceHistory serviceHistory, Long id) throws NotFoundException {
		Optional<ServiceHistory> histOpt =findOne(id);
		if(histOpt.isPresent()){
			ServiceHistory serviceHistoryRate = histOpt.get();
			serviceHistoryRate.setDescription(serviceHistory.getDescription());
			serviceHistoryRate.setRate(serviceHistory.getRate());
			serviceHistoryRate.setHours(serviceHistory.getHours());
			serviceHistoryRate.setDate(serviceHistory.getDate());
			serviceHistoryRate.setServiceRequests(serviceHistory.getServiceRequests());
			serviceHistoryRate.setWorker(serviceHistory.getWorker());
			return serviceHistoryRepository.saveAndFlush(serviceHistoryRate);
		}else{
			throw new NotFoundException("User not found exception");
		}
		
	}
	
	public void delete(ServiceHistory serviceHistory) {
		serviceHistoryRepository.delete(serviceHistory);
	}

	public Page<ServiceHistory> findServiceHistoryByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return serviceHistoryRepository.findAll(pageable);
	}


}
