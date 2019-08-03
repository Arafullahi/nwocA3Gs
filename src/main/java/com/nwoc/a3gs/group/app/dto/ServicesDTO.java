package com.nwoc.a3gs.group.app.dto;

import org.springframework.web.multipart.MultipartFile;

import com.nwoc.a3gs.group.app.model.Services;

public class ServicesDTO {

	private Long id;
	private String name;
	private String description;
	private String images;
	private MultipartFile file;
	private Services parentSevice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Services getParentSevice() {
		return parentSevice;
	}

	public void setParentSevice(Services parentSevice) {
		this.parentSevice = parentSevice;
	}

}
