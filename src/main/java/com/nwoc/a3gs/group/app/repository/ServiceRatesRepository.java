package com.nwoc.a3gs.group.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nwoc.a3gs.group.app.model.ServiceRates;

import java.util.Optional;

public interface ServiceRatesRepository extends JpaRepository<ServiceRates, Long>, PagingAndSortingRepository<ServiceRates, Long>{

    Optional<ServiceRates> findByServices_Id(Long id);
}
