package com.gridscape.admin;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootJspExampleApplication  extends SpringBootServletInitializer {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(SpringBootJspExampleApplication.class);
//	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootJspExampleApplication.class, args);
	}
	
	 @RequestMapping(value = "/user")
	   public Principal user(Principal principal) {		
		 return principal;
	 }

}