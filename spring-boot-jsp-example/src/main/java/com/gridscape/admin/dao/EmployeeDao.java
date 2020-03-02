package com.gridscape.admin.dao;

import java.util.List;

import com.gridscape.admin.model.Employee;

public interface EmployeeDao {
	public abstract int createEmployee(Employee employee);
	public abstract Employee authEmployee(Employee employee);	
	public boolean authNewEmployeeUserName(String username);
	public List<Employee> getEmployeeForCompany(int companyId);
	public Employee getEmployee(int empId);
}
