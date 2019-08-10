package com.nwoc.a3gs.group.app.dto;


import javax.validation.constraints.NotNull;

import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.model.Workers;

public class WorkerRatesDTO {
	

    private  Long wrate_id;
	@NotNull(message = "Please enter Services")
	 private  Services services;
	@NotNull(message = "Please enter workers")
	 private Workers workers;
    private  String description;
    @NotNull(message = "Please enter rate")
    private Double rate;
    @NotNull(message = "Please enter unit")
    private String unit;
	public Long getWrate_id() {
		return wrate_id;
	}
	public void setWrate_id(Long wrate_id) {
		this.wrate_id = wrate_id;
	}
	public Services getServices() {
		return services;
	}
	public void setServices(Services services) {
		this.services = services;
	}
	public Workers getWorkers() {
		return workers;
	}
	public void setWorkers(Workers workers) {
		this.workers = workers;
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
