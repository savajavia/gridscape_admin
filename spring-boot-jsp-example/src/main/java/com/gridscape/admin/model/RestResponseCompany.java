package com.gridscape.admin.model;

import java.util.ArrayList;
import java.util.List;

public class RestResponseCompany {
	
	String companyName;
	List<RestResponseEmployee> restResponseEmployee = new ArrayList<>();
	
	public List<RestResponseEmployee> getRestResponseEmployee() {
		return restResponseEmployee;
	}

	public void setRestResponseEmployee(List<RestResponseEmployee> restResponseEmployee) {
		this.restResponseEmployee = restResponseEmployee;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
