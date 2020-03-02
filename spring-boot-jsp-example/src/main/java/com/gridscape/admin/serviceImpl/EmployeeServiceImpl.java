package com.gridscape.admin.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gridscape.admin.dao.EmployeeDao;
import com.gridscape.admin.model.Employee;
import com.gridscape.admin.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Transactional
	public int createEmployee(Employee employee) {
		int id = employeeDao.createEmployee(employee);
		return id;
	}
	
	@Transactional
	public Employee authEmployee(Employee employee) {
		Employee emp = employeeDao.authEmployee(employee);
		return emp;
	}

	@Transactional
	public boolean authNewEmployeeUserName(String username) {
		boolean authUser = employeeDao.authNewEmployeeUserName(username);
		return authUser;
	}
	
	@Transactional
	public List<Employee> getEmployeeForCompany(int companyId){
		List<Employee> employee = employeeDao.getEmployeeForCompany(companyId);
		return employee;
	}
	
	@Transactional
	public Employee getEmployee(int empId) {
		Employee emp = employeeDao.getEmployee(empId);
		return emp;
	}
}
