package com.nwoc.a3gs.group.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.web.multipart.MultipartFile;



public class WorkersDTO {
	@JsonAlias("worker_id")
	private Long workerId;
	private String name;
	private String phone;
	private String email;
	private String location;
	private MultipartFile file;
	private List<Long> serviceIds;
	
	
	public Long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public List<Long> getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(List<Long> serviceIds) {
		this.serviceIds = serviceIds;
	}
	
	

}
