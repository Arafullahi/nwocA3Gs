package com.nwoc.a3gs.group.app.dto;

import java.util.List;

import com.nwoc.a3gs.group.app.model.Services;

public class WorkersDTO {
	private String name;
	private String phone;
	private String email;
	private String location;
	private List<Long> serviceIds;
	private Services services;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Services getServices() {
		return services;
	}
	public void setServices(Services services) {
		this.services = services;
	}
	public List<Long> getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(List<Long> serviceIds) {
		this.serviceIds = serviceIds;
	}
	
	

}
