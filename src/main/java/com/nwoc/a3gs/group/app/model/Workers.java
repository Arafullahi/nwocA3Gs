package com.nwoc.a3gs.group.app.model;


import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "workers")
@EntityListeners(AuditingEntityListener.class)
public class Workers  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonAlias("worker_id")
	@Column(name = "worker_id")
	private Long workerId;
	
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
