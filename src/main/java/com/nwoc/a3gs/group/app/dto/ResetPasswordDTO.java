package com.nwoc.a3gs.group.app.dto;

public class ResetPasswordDTO {
	
	private String userName;
	private String oldpassWord;
	private String newPassword;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldpassWord() {
		return oldpassWord;
	}
	public void setOldpassWord(String oldpassWord) {
		this.oldpassWord = oldpassWord;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

}