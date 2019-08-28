package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nwoc.a3gs.group.app.dto.ServicesDTO;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.repository.ServicesRepository;

import javassist.NotFoundException;

@Service
public class ServicesService {

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private ServicesRepository servicesRepository;

	@Transactional
	public ServicesDTO save(ServicesDTO servicesDTO) {
		Services services = new Services();
		BeanUtils.copyProperties(servicesDTO, services);
		MultipartFile file = servicesDTO.getFile();
		if (file != null) {
			String filePath = fileStorageService.storeFileInAPath(file);
			services.setImages(filePath);
			servicesDTO.setFile(null);
		}
		services = servicesRepository.save(services);
		BeanUtils.copyProperties(services, servicesDTO);
		return servicesDTO;
	}

	public List<Services> findAll() {
		return servicesRepository.findAll();
	}

	public Optional<Services> findOne(Long id) {
		return servicesRepository.findById(id);
	}

	public void delete(Services services) {
		servicesRepository.delete(services);
	}

	public ServicesDTO findById(Long serviceId) throws NotFoundException {
		ServicesDTO servicesDTO=null;
		Optional<Services> services= findOne(serviceId);
		if(services.isPresent()){
			servicesDTO= new ServicesDTO();
			BeanUtils.copyProperties(services, servicesDTO);
		}else{
			throw new NotFoundException("Not found exception");
		}
		return servicesDTO;
	}
	
	
	public boolean updateService(ServicesDTO servicesDTO, Long serviceId) throws NotFoundException {
		boolean isUpdated=false;
		Optional<Services> servicesOpt = findOne(serviceId);
		if(!servicesOpt.isPresent()){
			throw new NotFoundException("Service not found");
		}
		Services services=servicesOpt.get();
		BeanUtils.copyProperties(servicesDTO, services);
		services.setId(serviceId);
		MultipartFile file = servicesDTO.getFile();
		if (file != null) {
			String filePath = fileStorageService.storeFileInAPath(file);
			services.setImages(filePath);
			servicesDTO.setFile(null);
		}
		servicesRepository.save(services);
		isUpdated=true;
		return isUpdated;
	}
	
	public List<Services> findMainService() {
		return servicesRepository.findMainServices();
		
	}

	public Set<Services> findChildService(Long id) throws NotFoundException {
		Optional<Services> servicesOpt = findOne(id);
		if(!servicesOpt.isPresent()){
			throw new NotFoundException("Service not found");
		}
		return servicesOpt.get().getChildService();
	}

	public Page<Services> findServicesByPages(int page, int size) {
		Pageable pageable = new PageRequest(page, size);

		return servicesRepository.findAll(pageable);
	}
	
	public List<Services> findMainServicesAndName(String name, Long id) {
		return servicesRepository.findMainServicesAndName(name, id);
		
	}
	
	public List<Services> findMainServiceName(String name) {
		return servicesRepository.findMainServiceName(name);
		
	}
	
	public List<Services> findMainServiceById(Long id) {
		return servicesRepository.findMainServiceById(id);
		
	} 

}
