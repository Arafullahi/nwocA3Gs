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

import com.nwoc.a3gs.group.app.dto.ServiceCommentsDTO;
import com.nwoc.a3gs.group.app.model.ServiceComments;
import com.nwoc.a3gs.group.app.repository.ServiceCommentsRepository;

import javassist.NotFoundException;

@Service
public class ServiceCommentsServiceImpl {
	
	@Autowired
	ServiceCommentsRepository serviceCommentsRepository;
	
	@Transactional
	public ServiceComments create(ServiceCommentsDTO serviceCommentsDTO) {
		ServiceComments serviceComments = new ServiceComments();
		BeanUtils.copyProperties(serviceCommentsDTO,serviceComments);
		return serviceCommentsRepository.save(serviceComments);
	}
	
	public List<ServiceComments> findAll() {
		return serviceCommentsRepository.findAll();
	}
	
	public Optional<ServiceComments> findOne(Long id) {
		return serviceCommentsRepository.findById(id);
	}
	
	public ServiceComments update(ServiceCommentsDTO serviceCommentsDTO, Long id) throws NotFoundException {
		Optional<ServiceComments> msgOpt =findOne(id);
		if(msgOpt.isPresent()){
			ServiceComments serviceComment = msgOpt.get();
			serviceComment.setDescription(serviceCommentsDTO.getDescription());
			serviceComment.setServiceRequests(serviceCommentsDTO.getServiceRequests());
			serviceComment.setWorker(serviceCommentsDTO.getWorker());
			serviceComment.setStarRate(serviceCommentsDTO.getStarRate());
			return serviceCommentsRepository.saveAndFlush(serviceComment);
		}else{
			throw new NotFoundException("Service Comment Details Not found");
		}
		
	}
	
	public void delete(ServiceComments serviceComments) {
		serviceCommentsRepository.delete(serviceComments);
	}

	public Page<ServiceComments> findServiceCommentByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return serviceCommentsRepository.findAll(pageable);
	}



}
