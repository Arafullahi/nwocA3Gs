package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;

import com.nwoc.a3gs.group.app.model.*;
import com.nwoc.a3gs.group.app.repository.*;
import javafx.concurrent.Worker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nwoc.a3gs.group.app.dto.ServiceRequestsDTO;

import javassist.NotFoundException;

@Service
public class ServiceRequestService {
	
	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ServicesRepository servicesRepository;
	@Autowired
	WorkerRepository workerRepository;
	@Autowired
	ServiceRatesRepository serviceRatesRepository;
	@Autowired
	WorkerRatesRepository workerRatesRepository;

	public ServiceRequests create(ServiceRequestsDTO serviceRequestsDTO) throws NotFoundException {
		
		ServiceRequests serviceRequests = new ServiceRequests();
		BeanUtils.copyProperties(serviceRequestsDTO, serviceRequests);
		Optional<User> userOpt= userRepository.findByUsername(serviceRequestsDTO.getCustomer().getUsername());

		if(!userOpt.isPresent()){
			throw new NotFoundException("User not found");
		}
		Optional<Services> services = servicesRepository.findById(serviceRequestsDTO.getService().getId());
		if(!services.isPresent()){
			throw new NotFoundException("User not found");
		}

		if(serviceRequestsDTO.getWorker()!=null && serviceRequestsDTO.getWorker().getWorker_id()!=null){
			Long workerId=serviceRequestsDTO.getWorker().getWorker_id();
			Optional<Workers> worker= workerRepository.findById(workerId);
			if(!worker.isPresent()){
				throw new NotFoundException("Worker Not found");
			}
			Optional<WorkerRates> workerRates = workerRatesRepository.findByServices_IdAndWorkers_Id(serviceRequestsDTO.getService().getId(),workerId);
			if(workerRates.isPresent()){
				serviceRequests.setRate(workerRates.get().getRate()*serviceRequests.getHours());
			}
			serviceRequests.setWorker(worker.get());
		}else{
			Optional<ServiceRates> serviceRates = serviceRatesRepository.findByServices_Id(serviceRequestsDTO.getService().getId());
			if(serviceRates.isPresent()){
				serviceRequests.setRate(serviceRates.get().getRate()*serviceRequests.getHours());
			}
		}
		serviceRequests.setService(services.get());
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
		Optional<User> userOpt= userRepository.findByUsername(serviceRequestsDTO.getCustomer().getUsername());
		if(!userOpt.isPresent()){
			throw new NotFoundException("User not found");
		}
		if(serviceReqsOPt.isPresent()){
			ServiceRequests serviceRequests = serviceReqsOPt.get();
			BeanUtils.copyProperties(serviceRequestsDTO, serviceRequests);	
			serviceRequests.setCustomer(userOpt.get());
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
		Pageable pageable = new PageRequest(page, size);
		return serviceRequestRepository.findByServiceStatus(pageable,ServiceStatus.SERVICE_COMPLETED);
	}
	
	public Page<ServiceRequests> findPayedServiceRequestByPages(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return serviceRequestRepository.findByServiceStatus(pageable,ServiceStatus.AMOUNT_PAYED);
	}
	
	public Page<ServiceRequests> findNotPayedServiceRequestByPages(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return serviceRequestRepository.findByServiceStatus(pageable,ServiceStatus.SERVICE_REQUESTED);
	}

	
	

}
