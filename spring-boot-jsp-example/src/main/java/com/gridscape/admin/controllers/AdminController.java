package com.gridscape.admin.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.gridscape.admin.model.Employee;
import com.gridscape.admin.model.RestTemplateCompany;

@Controller
public class AdminController {
	
	@RequestMapping("/")
	private ModelAndView homePage(ModelMap response, ModelAndView modelView, HttpSession httpSession, @ModelAttribute("empForm") Employee employee)
	{
		Object company_login = httpSession.getAttribute("company_login");
		Object emp_login = httpSession.getAttribute("emp_login");
		if(company_login == null && emp_login == null) {
		//	final String uri = "http://localhost:8080/getCompanies?security_key=api_security_1155";
		//    RestTemplate restTemplate = new RestTemplate();
		     
		//    RestTemplateCompany result = restTemplate.getForObject(uri, RestTemplateCompany.class);
		//    response.addAttribute(result);
		//    modelView.addObject(result);
		    modelView.addObject(employee);
			modelView.setViewName("admin");
		    return modelView;
						
		}else if(company_login != null) {
		    modelView.setViewName("redirect:/companyDashboardEntry");
		    return modelView;			
		}
		else if(emp_login != null) {
		    modelView.setViewName("redirect:/employeeDashboardEntry");
		    return modelView;			
		}
		return null;
	}

	@RequestMapping("/webServiceCallGetEmployes")
	private ModelAndView webServiceCallGetEmployes(@ModelAttribute("response") RestTemplateCompany result,ModelAndView modelView, @RequestParam("company_id") int companyId)
	{
	
			final String uri = "http://localhost:8080/callGetEmployees?security_key=api_security_1155&&company_id=" + companyId;
		    RestTemplate restTemplate = new RestTemplate();
		     
		    result = restTemplate.getForObject(uri, RestTemplateCompany.class);
		    modelView.addObject("response",result);
		    modelView.setViewName("webservice");
		  return modelView;
	}
	
	@RequestMapping("/index")
	private String homePage()
	{
		return "index.html";
	}
	
}
