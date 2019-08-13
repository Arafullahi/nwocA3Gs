package com.nwoc.a3gs.group.app.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;
import com.nwoc.a3gs.group.app.model.ServiceStatus;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.model.Workers;

public class ServiceRequestsDTO {

	private Long id;
	 @NotBlank
	private User customer;
	@NotBlank
	private Services service;
	
	private Workers worker;
	@NotBlank
	private Float hours;
	@NotBlank
	private Double rate;
	private String comment;

	private Date date;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60)
	private ServiceStatus serviceStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

	public Workers getWorker() {
		return worker;
	}

	public void setWorker(Workers worker) {
		this.worker = worker;
	}

	public Float getHours() {
		return hours;
	}

	public void setHours(Float hours) {
		this.hours = hours;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
