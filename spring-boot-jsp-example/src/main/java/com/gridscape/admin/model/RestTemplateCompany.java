package com.gridscape.admin.model;

import java.util.ArrayList;
import java.util.List;

public class RestTemplateCompany {
	
	String companyName;
	List<RestTemplateEmployee> restResponseEmployee = new ArrayList<>();

	public List<RestTemplateEmployee> getRestResponseEmployee() {
		return restResponseEmployee;
	}

	public void setRestResponseEmployee(List<RestTemplateEmployee> restResponseEmployee) {
		this.restResponseEmployee = restResponseEmployee;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
