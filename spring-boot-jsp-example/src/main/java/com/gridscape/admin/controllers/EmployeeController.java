package com.gridscape.admin.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gridscape.admin.model.Employee;
import com.gridscape.admin.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping("/createEmployee")
	public String createEmployee(@ModelAttribute("empForm") Employee employee, HttpSession httpSession) {
		if(sessionSecurity(httpSession)) {
			return "forward:/";
		}
		int id = 0;
		int company_id = (Integer)httpSession.getAttribute("company_id");
		employee.setCompanyId(company_id);
		String password = employee.getPassword();
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			employee.setPassword(encryptedPassword);
			boolean authUserName = employeeService.authNewEmployeeUserName(employee.getUsername());
			if(authUserName) {
				java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				employee.setCreate_date(date);
				id = employeeService.createEmployee(employee);	
				httpSession.setAttribute("emp_login", 1);
				httpSession.setAttribute("emp_id", id);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(id == 0) {
			return "redirect:/createEmployeeEntry";				
		}else {
			return "redirect:/employeeDashboardEntry";						
		}
	}
	
	@RequestMapping("/createEmployeeEntry")
	public ModelAndView createEmployeeEntry(ModelAndView modelView, @ModelAttribute("empForm") Employee employee, HttpSession httpSession) {				
		//modelView.addObject("empForm",new Employee());
		
		if(sessionSecurity(httpSession)) {
			modelView.setViewName("forward:/");
			return modelView;
		}
		
		modelView.addObject("empForm",employee);
		modelView.setViewName("create_employee");
		return modelView;
	}
	
	@RequestMapping("/loginEmployee")
	public String loginEmployee(@ModelAttribute("empForm") Employee employee,RedirectAttributes redirectAttributes, HttpSession httpSession) {
		Employee emp = employeeService.authEmployee(employee);		
		if(emp != null) {
			httpSession.setAttribute("emp_login", 1);
			httpSession.setAttribute("emp_id", emp.getId());
			return "redirect:/employeeDashboardEntry";			
		}else {
			employee.setLoginFlag(2);
		    redirectAttributes.addFlashAttribute("empForm", employee);
			
			return "redirect:/createEmployeeEntry";						
		}
			
			
	}
	
	@RequestMapping("/employeeDashboardEntry")
	public String employeeDashboardEntry(HttpSession httpSession, ModelMap model) {				
		Object emp_login = httpSession.getAttribute("emp_login");
		if(emp_login == null) {
			return "redirect:/createEmployeeEntry";
		}else {
			int empId = (Integer) httpSession.getAttribute("emp_id");
			Employee emp = employeeService.getEmployee(empId);
			model.addAttribute("emp_name",emp.getEmployeeName());
			return "employee_dashboard";			
		}
	}

	@RequestMapping("/employeeLogout")
	public String employeeLogout(HttpServletRequest httpServletRequest) {
	    HttpSession session = httpServletRequest.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
		return "redirect:/";
	}

	@RequestMapping("/employeeLoginEntry")
	public ModelAndView employeeLogin(HttpServletRequest httpServletRequest,@ModelAttribute("empForm") Employee emp, ModelAndView modelView) {
		modelView.addObject(modelView);
		modelView.setViewName("login_employee");
		return modelView;
	}

	private boolean sessionSecurity(HttpSession httpSession) {
		Object company_login = httpSession.getAttribute("company_login");
		if (company_login == null) {
			return true;
		}else {
			return false;
		}
	}

}

