package com.gridscape.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.Table;
import javax.persistence.Transient;

  
@Entity  
@Table(name= "company")   
public class Company {    
  
@Id   
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(name = "company_name", nullable = false, length = 100)
private String companyName;

@Column(name = "image_path", nullable = false, length = 1000)
private String imagePath;

@Column(name = "username", nullable = false, length = 100)
private String username;

@Column(name = "password", nullable = false, length = 100)
private String password;

@Column(name = "email", nullable = false, length = 100)
private String email;

@Column(name = "signin_from", nullable = false, length = 1)
private String signin_from;

@Column(name = "profile_pic_mode", nullable = false, length = 1)
private String profile_pic_mode;

@Column(name = "employee_report_path", nullable = false, length = 100)
private String employee_report_path;

@Transient
int regFlag;

@Transient
int loginFlag;

@Transient
int usernameDuplicationFlag;

@Transient
int googleProfilePic;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getImagePath() {
	return imagePath;
}
public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public int getRegFlag() {
	return regFlag;
}
public void setRegFlag(int regFlag) {
	this.regFlag = regFlag;
}
public int getLoginFlag() {
	return loginFlag;
}
public void setLoginFlag(int loginFlag) {
	this.loginFlag = loginFlag;
}
public int getUsernameDuplicationFlag() {
	return usernameDuplicationFlag;
}
public void setUsernameDuplicationFlag(int usernameDuplicationFlag) {
	this.usernameDuplicationFlag = usernameDuplicationFlag;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getSignin_from() {
	return signin_from;
}
public void setSignin_from(String signin_from) {
	this.signin_from = signin_from;
}
public String getProfile_pic_mode() {
	return profile_pic_mode;
}
public void setProfile_pic_mode(String profile_pic_mode) {
	this.profile_pic_mode = profile_pic_mode;
}
public int getGoogleProfilePic() {
	return googleProfilePic;
}
public void setGoogleProfilePic(int googleProfilePic) {
	this.googleProfilePic = googleProfilePic;
}
public String getEmployee_report_path() {
	return employee_report_path;
}
public void setEmployee_report_path(String employee_report_path) {
	this.employee_report_path = employee_report_path;
}


}   