package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nwoc.a3gs.group.app.dto.ServiceHistoryDTO;
import com.nwoc.a3gs.group.app.model.ServiceHistory;
import com.nwoc.a3gs.group.app.repository.ServiceHistoryRepository;

import javassist.NotFoundException;

@Service
public class ServiceHistoryServiceImpl {
	
	@Autowired
	ServiceHistoryRepository serviceHistoryRepository;
	
	@Transactional
	public ServiceHistory create(ServiceHistoryDTO serviceHistoryDTO) {
		ServiceHistory serviceHistory = new ServiceHistory();
		BeanUtils.copyProperties(serviceHistoryDTO,serviceHistory);
		return serviceHistoryRepository.save(serviceHistory);
	}
	
	public List<ServiceHistory> findAll() {
		return serviceHistoryRepository.findAll();
	}
	
	public Optional<ServiceHistory> findOne(Long id) {
		return serviceHistoryRepository.findById(id);
	}
	
	public ServiceHistory update(ServiceHistoryDTO serviceHistoryDTO, Long id) throws NotFoundException {
		Optional<ServiceHistory> histOpt =findOne(id);
		if(histOpt.isPresent()){
			ServiceHistory serviceHistoryRate = histOpt.get();
			serviceHistoryRate.setDescription(serviceHistoryDTO.getDescription());
			serviceHistoryRate.setRate(serviceHistoryDTO.getRate());
			serviceHistoryRate.setHours(serviceHistoryDTO.getHours());
			serviceHistoryRate.setServiceRequests(serviceHistoryDTO.getServiceRequests());
			serviceHistoryRate.setWorker(serviceHistoryDTO.getWorker());
			return serviceHistoryRepository.saveAndFlush(serviceHistoryRate);
		}else{
			throw new NotFoundException("Service History Details Not found");
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
