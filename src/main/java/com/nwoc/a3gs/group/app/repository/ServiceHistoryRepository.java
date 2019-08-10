package com.nwoc.a3gs.group.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nwoc.a3gs.group.app.model.ServiceHistory;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long>, PagingAndSortingRepository<ServiceHistory, Long>{

}
