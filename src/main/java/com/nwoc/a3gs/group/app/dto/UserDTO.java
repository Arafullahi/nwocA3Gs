package com.nwoc.a3gs.group.app.dto;

import java.util.HashSet;
import java.util.Set;


import javax.validation.constraints.Email;

import com.nwoc.a3gs.group.app.model.Role;

public class UserDTO {

    private String name;
    private String username;
    @Email
    private String email;
    private String password;
	private String phone;
	private Integer age;
	private String location;
    private Set<Role> roles = new HashSet<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
    


}
