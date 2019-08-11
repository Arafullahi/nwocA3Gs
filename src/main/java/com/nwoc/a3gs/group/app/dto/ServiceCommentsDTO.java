package com.nwoc.a3gs.group.app.dto;


import javax.validation.constraints.NotNull;


import com.nwoc.a3gs.group.app.model.ServiceRequests;
import com.nwoc.a3gs.group.app.model.Workers;

public class ServiceCommentsDTO {
	
	private Long id;
	
	@NotNull
	private ServiceRequests serviceRequests;
	
	@NotNull
	private Workers worker;
	
	private String description;
	
	private Integer starRate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceRequests getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(ServiceRequests serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public Workers getWorker() {
		return worker;
	}

	public void setWorker(Workers worker) {
		this.worker = worker;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStarRate() {
		return starRate;
	}

	public void setStarRate(Integer starRate) {
		this.starRate = starRate;
	}
	
}
