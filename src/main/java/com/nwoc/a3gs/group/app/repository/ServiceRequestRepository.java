package com.nwoc.a3gs.group.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nwoc.a3gs.group.app.model.ServiceRequests;
import com.nwoc.a3gs.group.app.model.ServiceStatus;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequests, Long>, PagingAndSortingRepository<ServiceRequests, Long> {

	List<ServiceRequests> findByCustomer_Username(String username);

	Page<ServiceRequests> findByServiceStatus(Pageable page ,ServiceStatus serviceStatus);

}
