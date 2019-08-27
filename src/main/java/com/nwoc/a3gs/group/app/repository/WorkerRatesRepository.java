package com.nwoc.a3gs.group.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.nwoc.a3gs.group.app.model.WorkerRates;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerRatesRepository extends JpaRepository<WorkerRates, Long> ,PagingAndSortingRepository<WorkerRates, Long> {

    Optional<WorkerRates> findByServices_IdAndWorkers_WorkerId(Long servicesId, Long worker_id);
}
