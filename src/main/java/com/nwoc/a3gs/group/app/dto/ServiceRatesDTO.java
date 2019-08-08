package com.nwoc.a3gs.group.app.dto;

import java.util.List;

public class ServiceRatesDTO {
	
	private Long rate_id;
	private String description;
	private Double  rate;
	private String unit;
	private List<Long>  serviceId;
	
	public Long getRate_id() {
		return rate_id;
	}
	public void setRate_id(Long rate_id) {
		this.rate_id = rate_id;
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
	public List<Long> getServiceId() {
		return serviceId;
	}
	public void setServiceId(List<Long> serviceId) {
		this.serviceId = serviceId;
	}
	

}
