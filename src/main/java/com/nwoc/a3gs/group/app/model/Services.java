package com.nwoc.a3gs.group.app.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "services")
@EntityListeners(AuditingEntityListener.class)
public class Services {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentSeviceId", nullable = true)
	private Services parentSevice;
	@NotBlank
	private String name;
	private String description;
	private String images;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateddAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentSevice")
	private Set<Services> childService;
	@JsonIgnore
	 @ManyToMany(fetch = FetchType.LAZY,
	            cascade = {
	                CascadeType.PERSIST,
	                CascadeType.MERGE
	            },
	            mappedBy = "services")
	/*@OneToMany(mappedBy = "services", cascade = CascadeType.ALL, fetch = FetchType.LAZY)*/
	private Set<Workers> workers;
	
	public Services() {}
	
	public Services(Long serviceIs) {
		this.id=serviceIs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Services getParentSevice() {
		return parentSevice;
	}

	public void setParentSevice(Services parentSevice) {
		this.parentSevice = parentSevice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
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

	public Set<Services> getChildService() {
		return childService;
	}

	public void setChildService(Set<Services> childService) {
		this.childService = childService;
	}

	public void addChildService(Services service) {
		if (childService == null) {
			childService = new HashSet<Services>();
		}
		this.childService.add(service);

	}

	public void removeChildService(Services service) {
		if (childService != null && childService.contains(service)) {
			childService.remove(service);
		}
	}

}
