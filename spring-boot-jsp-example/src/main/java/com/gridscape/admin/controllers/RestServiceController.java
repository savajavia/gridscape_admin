package com.gridscape.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gridscape.admin.model.Company;
import com.gridscape.admin.model.Employee;
import com.gridscape.admin.model.RestResponseCompany;
import com.gridscape.admin.model.RestResponseEmployee;
import com.gridscape.admin.service.CompanyService;
import com.gridscape.admin.service.EmployeeService;

@RestController
public class RestServiceController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	CompanyService companyService;
	
	 @RequestMapping(value = "/callGetEmployees")
	   public RestResponseCompany getProduct(@RequestParam(value = "security_key") String apiKey,@RequestParam(value = "company_id") int company_id) {
		 
		 String apiSecurity = "api_security_1155";
	     if(apiSecurity.equals(apiKey)) {
	    	 RestResponseCompany restCompany = new RestResponseCompany();
	    	 List<Employee> employeeList = employeeService.getEmployeeForCompany(company_id); 
	    	 Company cmpny = companyService.getCompany(company_id);
	    	 
	    	 if(employeeList != null && cmpny != null) {
	    		 restCompany.setCompanyName(cmpny.getCompanyName());
	    		 RestResponseEmployee restEmployee;
	    	 for (Employee employee : employeeList) {
	    		 restEmployee = new RestResponseEmployee();
	    		 restEmployee.setEmployee_id(employee.getId());
	    		 restEmployee.setEmployee_name(employee.getEmployeeName());
	    		 restEmployee.setUsername(employee.getUsername());
	    		 restCompany.getRestResponseEmployee().add(restEmployee);	    		 
	    	 }
	    	 }
	    	 return restCompany;
	   }else {
		   return null;
	   }
	 }
}
