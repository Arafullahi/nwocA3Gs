package com.nwoc.a3gs.group.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "candidates", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" })})
@EntityListeners(AuditingEntityListener.class)
public class Candidates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long candidate_id;
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String first_name;
	
	private String middle_name;
	
	private String last_name;
	
	@Size(max = 500)
	private String address;
	
	@NotBlank
	private String phone;
	
	@NotNull
	private Date dob;
	
	@NotNull
	private Integer pin;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	private String country;
	
	private String state;
	
	private String city;
	
	@NotBlank
	private String qualification;
	
	@NotBlank
	private String experience;
	
	private String intrested_work_area;
	
	@NotNull
	private String resume_file;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateddAt;

	public Candidates() {}
	
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
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
	public String getResume_file() {
		return resume_file;
	}

	public void setResume_file(String resume_file) {
		this.resume_file = resume_file;
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
	
}
