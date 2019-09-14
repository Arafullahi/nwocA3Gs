package com.nwoc.a3gs.group.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordResetTokenDTO {
	
	 @NotBlank(message = "Email can't be blank")
	 @Size(max = 50)
	 @Email(message = "invalid email format")
     private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	 
	 

}
