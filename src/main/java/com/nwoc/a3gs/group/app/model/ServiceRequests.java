package com.nwoc.a3gs.group.app.model;

import java.util.Date;

public class ServiceRequests {

	private Long id;
	private User customer;
	private Services service;
	private Workers worker;
	private Float hours;
	private Double rate;
	private String comment;
	private Date date;
	private Date updateDate;
	
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
