package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nwoc.a3gs.group.app.dto.ServiceRatesDTO;
import com.nwoc.a3gs.group.app.model.ServiceRates;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.repository.ServiceRatesRepository;

import javassist.NotFoundException;

@Service
public class ServiceRatesServiceImpl {
	
	@Autowired
	ServiceRatesRepository serviceRatesRepository;
	
	@Autowired
	ServicesService servicesService;
	
	@Transactional
	public ServiceRates create(ServiceRatesDTO serviceRatesDTO) {
		ServiceRates serviceRates = new ServiceRates();
		BeanUtils.copyProperties(serviceRatesDTO, serviceRates);
		List<Services> services= serviceRatesDTO.getServiceId().stream().map(x->servicesService.findOne(x).get()).collect(Collectors.toList());
		serviceRates.setServices(services);
		return serviceRatesRepository.save(serviceRates);
	}
	
	public List<ServiceRates> findAll() {
		return serviceRatesRepository.findAll();
	}
	
	public Optional<ServiceRates> findOne(Long id) {
		return serviceRatesRepository.findById(id);
	}
	
	public ServiceRates update(ServiceRatesDTO serviceRatesDTO, Long id) throws NotFoundException {
		Optional<ServiceRates> rateOpt =findOne(id);
		if(rateOpt.isPresent()){
			ServiceRates serRate = rateOpt.get();
			serRate.setDescription(serviceRatesDTO.getDescription());
			serRate.setRate(serviceRatesDTO.getRate());
			serRate.setUnit(serviceRatesDTO.getUnit());
			List<Services> services= serviceRatesDTO.getServiceId().stream().map(x->servicesService.findOne(x).get()).collect(Collectors.toList());
			serRate.setServices(services);
			return serviceRatesRepository.saveAndFlush(serRate);
		}else{
			throw new NotFoundException("User not found exception");
		}
		
	}
	
	public void delete(ServiceRates serviceRates) {
		serviceRatesRepository.delete(serviceRates);
	}

	public Page<ServiceRates> findServiceRatesByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return serviceRatesRepository.findAll(pageable);
	}

}
