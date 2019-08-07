package com.nwoc.a3gs.group.app.dto;

import java.util.List;

public class WorkerRatesDTO {
	

	private Long id;
	private List<Long>  serviceId;
	private List<Long>  workerId;
	private String description;
	private Double  rate;
	private String unit;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Long> getServiceId() {
		return serviceId;
	}
	public void setServiceId(List<Long> serviceId) {
		this.serviceId = serviceId;
	}
	public List<Long> getWorkerId() {
		return workerId;
	}
	public void setWorkerId(List<Long> workerId) {
		this.workerId = workerId;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
