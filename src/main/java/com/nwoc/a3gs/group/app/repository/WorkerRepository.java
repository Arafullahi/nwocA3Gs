package com.nwoc.a3gs.group.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nwoc.a3gs.group.app.model.Workers;

public interface WorkerRepository extends JpaRepository<Workers, Long> {

	List<Workers> findByServices_id(Long serviceId);

}
