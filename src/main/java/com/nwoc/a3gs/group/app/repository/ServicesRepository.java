package com.nwoc.a3gs.group.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nwoc.a3gs.group.app.model.Services;

public interface ServicesRepository extends JpaRepository<Services, Long> , PagingAndSortingRepository<Services	, Long>{
	 @Query("SELECT s FROM Services s LEFT JOIN s.childService  c WHERE c.id is null")
	   public List<Services> findMainServices();
	 

}
