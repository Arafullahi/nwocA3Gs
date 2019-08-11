package com.nwoc.a3gs.group.app.dto;


import javax.validation.constraints.NotNull;

import com.nwoc.a3gs.group.app.model.ServiceRequests;
import com.nwoc.a3gs.group.app.model.Workers;

public class ServiceHistoryDTO {

	private Long id;
	@NotNull(message = "Please enter Service Requests")
	private ServiceRequests serviceRequests;
	@NotNull(message = "Please enter worker")
	private Workers worker;
	
	private String description;
	@NotNull(message = "Please enter rate")
	private Double rate;
	@NotNull(message = "Please enter hours")
	private Float hours;

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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Float getHours() {
		return hours;
	}

	public void setHours(Float hours) {
		this.hours = hours;
	}
	
	

}
