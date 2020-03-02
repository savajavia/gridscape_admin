package com.gridscape.admin.dao;

import java.util.List;
import java.util.Map;

import com.gridscape.admin.model.Company;

public interface CompanyDao {
	public int createCompany(Company company);
	public List<Company> getCompanyList();
	public Company getCompany(int companyId);
	public int getLatestCompanyId();
	public Company authCompany(Company company);
	public int authNewCompanyUserName(String username);
	public void updateCompany(Company company);
	public Map<String,String> companyChartDataForEmployees(int companyId);
}
