package com.nwoc.a3gs.group.app.dto;


import org.springframework.web.multipart.MultipartFile;

public class CandidatesDTO {
	
	
	private Long candidate_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String address;
	private String phone;
	private String dob;
	private Integer pin;
	private String email;
	private String country;
	private String state;
	private String city;
	private String qualification;
	private String experience;
	private String intrested_work_area;
	private MultipartFile files;
	private String resume_file;
	
	public Long getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(Long candidate_id) {
		this.candidate_id = candidate_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getIntrested_work_area() {
		return intrested_work_area;
	}
	public void setIntrested_work_area(String intrested_work_area) {
		this.intrested_work_area = intrested_work_area;
	}
	public MultipartFile getFiles() {
		return files;
	}
	public void setFiles(MultipartFile files) {
		this.files = files;
	}
	public String getResume_file() {
		return resume_file;
	}
	public void setResume_file(String resume_file) {
		this.resume_file = resume_file;
	}

}
