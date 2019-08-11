package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nwoc.a3gs.group.app.dto.ServiceRequestsDTO;
import com.nwoc.a3gs.group.app.model.ServiceRequests;
import com.nwoc.a3gs.group.app.model.ServiceStatus;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.repository.ServiceRequestRepository;
import com.nwoc.a3gs.group.app.repository.UserRepository;

import javassist.NotFoundException;

@Service
public class ServiceRequestService {
	
	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	@Autowired
	UserRepository userRepository;

	public ServiceRequests create(ServiceRequestsDTO serviceRatesDTO) throws NotFoundException {
		
		ServiceRequests serviceRequests = new ServiceRequests();
		BeanUtils.copyProperties(serviceRatesDTO, serviceRequests);
		Optional<User> userOpt= userRepository.findByUsername(serviceRatesDTO.getCustomer().getUsername());
		if(!userOpt.isPresent()){
			throw new NotFoundException("User not found");
		}
		serviceRequests.setCustomer(userOpt.get());
		serviceRequests= serviceRequestRepository.save(serviceRequests);
		return serviceRequests;
	}


	public List<ServiceRequests> findAll() {
		return serviceRequestRepository.findAll();
	}


	public Optional<ServiceRequests> findOne(Long id) {
		return serviceRequestRepository.findById(id);
	}


	public void delete(ServiceRequests serviceRequests) {
		serviceRequestRepository.delete(serviceRequests);
		
	}


	public Page<ServiceRequests> findServiceRequestByPages(int page, int size) {
		Pageable pageable = new PageRequest(page, size);

		return serviceRequestRepository.findAll(pageable);
	}


	public ServiceRequests update(ServiceRequestsDTO serviceRequestsDTO, Long id) throws NotFoundException {
		Optional<ServiceRequests> serviceReqsOPt =findOne(id);
		if(serviceReqsOPt.isPresent()){
			ServiceRequests serviceRequests = serviceReqsOPt.get();
			BeanUtils.copyProperties(serviceRequestsDTO, serviceRequests);
			
			return serviceRequestRepository.saveAndFlush(serviceRequests);
		}else{
			throw new NotFoundException("Service Request not found exception");
		}
	}


	public void update(ServiceRequests serviceRequests) {
		serviceRequestRepository.saveAndFlush(serviceRequests);
		
	}


	public List<ServiceRequests> listServiceRequestsByUsername(String username) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findByCustomer_Username(username);
	}


	public Page<ServiceRequests> findCompletedServiceRequestByPages(int page, int size) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findByServiceStatus(ServiceStatus.SERVICE_COMPLETED);
	}
	
	public Page<ServiceRequests> findPayedServiceRequestByPages(int page, int size) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findByServiceStatus(ServiceStatus.AMOUNT_PAYED);
	}
	
	public Page<ServiceRequests> findNotPayedServiceRequestByPages(int page, int size) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findByServiceStatus(ServiceStatus.SERVICE_REQUESTED);
	}

	
	

}
