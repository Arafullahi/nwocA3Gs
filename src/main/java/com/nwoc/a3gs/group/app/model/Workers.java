package com.nwoc.a3gs.group.app.model;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "workers")
@EntityListeners(AuditingEntityListener.class)
public class Workers  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long worker_id;
	
	@NotBlank
	private String name;
	
	private String phone;
	
	@Email
	private String email;
	
	private String location;
	@JsonIgnore
	
	
	@ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
@JoinTable(name = "workers_services",
    joinColumns = { @JoinColumn(name = "worker_id") },
    inverseJoinColumns = { @JoinColumn(name = "id") })
	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	/*@JoinTable(name = "workers_services",
	joinColumns = @JoinColumn( name = "worker_id", referencedColumnName="worker_id", nullable = false), 
	inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName="id" , nullable = false))
	//@OnDelete(action = OnDeleteAction.CASCADE)
	*/
	private List<Services> services;
	
	public Workers() {}
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateddAt;

	public Long getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(Long worker_id) {
		this.worker_id = worker_id;
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

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
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
