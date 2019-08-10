package com.nwoc.a3gs.group.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "workers_service_rates")
@EntityListeners(AuditingEntityListener.class)
public class WorkerRates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private  Long wrate_id;
	@NotNull(message = "Please enter Services")
	@OneToOne
	@JoinColumn(name="id")
	 private  Services services;
	@NotNull(message = "Please enter workers")
	@OneToOne
	@JoinColumn(name="worker_id")
	 private Workers workers;
    private  String description;
    @NotNull(message = "Please enter rate")
    private Double rate;
    @NotNull(message = "Please enter unit")
    private String unit;
    @JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateddAt;

	public Long getRate_id() {
		return wrate_id;
	}

	public void setRate_id(Long wrate_id) {
		this.wrate_id = wrate_id;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateddAt() {
		return updateddAt;
	}

	public void setUpdateddAt(Date updateddAt) {
		this.updateddAt = updateddAt;
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

	
	

}
