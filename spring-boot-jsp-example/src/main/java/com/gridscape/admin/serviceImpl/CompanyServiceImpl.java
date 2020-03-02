package com.gridscape.admin.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gridscape.admin.dao.CompanyDao;
import com.gridscape.admin.model.Company;
import com.gridscape.admin.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	CompanyDao companyDao;
	
	@Transactional
	public int createCompany(Company company) {
		int id = companyDao.createCompany(company);
		return id;
	}
	
	@Transactional
	public void updateCompany(Company company) {
		companyDao.updateCompany(company);
	}
	
	@Transactional
	public List<Company> getCompanyList() {
		List<Company> companyList = companyDao.getCompanyList();
		return companyList;
	}

	@Transactional
	public int getLatestCompanyId() {
		int id = companyDao.getLatestCompanyId();
		return id;
	}

	@Transactional
	public Company getCompany(int company_id) {
		Company cmpny = companyDao.getCompany(company_id);
		return cmpny;
	}
	
	@Transactional
	public Company authCompany(Company company) {
		Company cmpny = companyDao.authCompany(company);
		return cmpny;
	}
	
	@Transactional
	public int authNewCompanyUserName(String username) {
		int authUser = companyDao.authNewCompanyUserName(username);
		return authUser;
	}
	
	@Transactional
	public Map<String,String> companyChartDataForEmployees(int companyId) {
		Map<String,String> employeeMap = new HashMap<String,String>();
		employeeMap = companyDao.companyChartDataForEmployees(companyId);
		return employeeMap;
	}
}
