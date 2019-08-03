package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nwoc.a3gs.group.app.dto.WorkersDTO;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.model.Workers;
import com.nwoc.a3gs.group.app.repository.WorkerRepository;

import javassist.NotFoundException;

@Service
public class WorkerService {
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@Autowired
	ServicesService servicesService;
	
	@Transactional
	public Workers save(WorkersDTO workersDTO) {
		Workers workers= new Workers();
		BeanUtils.copyProperties(workersDTO, workers);
		List<Services> services= workersDTO.getServiceIds().stream().map(x->servicesService.findOne(x).get()).collect(Collectors.toList());
		workers.setServices(services);
		return workerRepository.save(workers);
	}

	
	public Optional<Workers> findOne(Long worker_id) {
		return workerRepository.findById(worker_id);
	}

	public Workers update(WorkersDTO workersDTO, Long worker_id) throws NotFoundException {
		Optional<Workers> wrkerOpt= findOne(worker_id);
		if(!wrkerOpt.isPresent()){
			throw new NotFoundException("Worker not found");
		}
		Workers wrker= wrkerOpt.get();
		wrker.setName(workersDTO.getName());
		wrker.setEmail(workersDTO.getEmail());
		wrker.setPhone(workersDTO.getPhone());
		wrker.setLocation(workersDTO.getLocation());
		wrker.setUpdateddAt(wrker.getUpdateddAt());
		List<Services> services= workersDTO.getServiceIds().stream().map(x->servicesService.findOne(x).get()).collect(Collectors.toList());
		wrker.setServices(services);
		BeanUtils.copyProperties(workersDTO, wrker);
		return workerRepository.save(wrker);
	}

	public void delete(Workers worker) {
		workerRepository.delete(worker);
	}

	public Page<Workers> findWorkerByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return workerRepository.findAll(pageable);
	}
}
