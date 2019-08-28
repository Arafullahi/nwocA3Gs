package com.nwoc.a3gs.group.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.nwoc.a3gs.group.app.model.Services;

public interface ServicesRepository extends JpaRepository<Services, Long> , PagingAndSortingRepository<Services	, Long>{
	 @Query("SELECT s FROM Services s  WHERE s.parentSevice is null")
	   public List<Services> findMainServices();
	 
	 @Query("SELECT s FROM Services s  WHERE s.parentSevice is not null and s.parentSevice.id =:id and s.name like %:name%")
	   public List<Services> findMainServicesAndName(@Param("name") String name,  @Param("id") Long id);
	 
	 @Query("SELECT s FROM Services s  WHERE s.parentSevice is not null and s.name like %:name%")
	   public List<Services> findMainServiceName(@Param("name") String name);
	 
	 @Query("SELECT s FROM Services s  WHERE s.parentSevice is not null and s.parentSevice.id =:id")
	   public List<Services> findMainServiceById(@Param("id") Long id);

	 

}
