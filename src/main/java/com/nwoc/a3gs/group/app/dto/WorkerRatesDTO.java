package com.nwoc.a3gs.group.app.dto;

import java.util.List;

import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.model.Workers;

public class WorkerRatesDTO {
	

	private Long rate_id;
	private Services  serviceId;
	private Workers  workerId;
	private String description;
	private Double  rate;
	private String unit;
	
	
	public Long getRate_id() {
		return rate_id;
	}
	public void setRate_id(Long rate_id) {
		this.rate_id = rate_id;
	}
	public Services getServiceId() {
		return serviceId;
	}
	public void setServiceId(Services serviceId) {
		this.serviceId = serviceId;
	}
	public Workers getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Workers workerId) {
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
