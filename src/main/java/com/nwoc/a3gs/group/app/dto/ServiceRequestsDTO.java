package com.nwoc.a3gs.group.app.dto;



import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import com.nwoc.a3gs.group.app.model.ServiceStatus;
import com.nwoc.a3gs.group.app.model.Services;
import com.nwoc.a3gs.group.app.model.User;
import com.nwoc.a3gs.group.app.model.Workers;

import java.util.Date;

public class ServiceRequestsDTO {

	private Long id;
	 @NotNull
	private User customer;
	 @NotNull
	private Services service;
	
	private Workers worker;
	 @NotNull
	private Float hours;
	 @NotNull
	private Double rate;
	private String comment;
	private  String fullName;
	private  String phone;
	private  String email;
	private  String address;
	private Date requestDate;
	private String pickTime;
	
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

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getPickTime() {
		return pickTime;
	}

	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}
}
