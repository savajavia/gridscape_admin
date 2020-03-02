package com.gridscape.admin.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.gridscape.admin.model.Company;
import com.gridscape.admin.model.Employee;
import com.gridscape.admin.service.CompanyService;
import com.gridscape.admin.service.EmployeeService;
import com.gridscape.admin.util.GeneratePdfReport;

@Controller
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
    private JavaMailSender javaMailSender;
	
	// Save the uploaded file to this folder
	private static String UPLOADED_FOLDER = "C://upload//";
	private static String REPORT_FOLDER = "C://reports//";

	@PostMapping("/createCompany")
	public String createCompany(@RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute("companyForm") Company company, RedirectAttributes redirectAttributes,
			HttpSession httpSession) {

		int insertedID = 0;
		if (!file.isEmpty()) {
			try {
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());

				if (!(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")
						|| extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("bmp")
						|| extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("webp"))) {
					company.setRegFlag(5);
					redirectAttributes.addFlashAttribute("companyForm", company);
					return "redirect:/createCompanyEntry";
				}

				int id = companyService.getLatestCompanyId();
				id++;
				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				String uploadDir = UPLOADED_FOLDER + id + "//";
				String fileName = new Timestamp(System.currentTimeMillis()).getTime() + file.getOriginalFilename();
				Path path = Paths.get(uploadDir + fileName);

				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdir();
				}

				Files.write(path, bytes);
				company.setImagePath(id + "//" + fileName);

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			company.setImagePath("");
		}

		String password = company.getPassword();
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			company.setPassword(encryptedPassword);
			int authCompanyUserName = companyService.authNewCompanyUserName(company.getUsername());
			if (authCompanyUserName == 0) {
				company.setSignin_from("A");
				company.setProfile_pic_mode("A");
				insertedID = companyService.createCompany(company);
				httpSession.setAttribute("company_login", 1);
				httpSession.setAttribute("company_id", insertedID);
			} else {
				// duplicate username
				company.setRegFlag(3);
				redirectAttributes.addFlashAttribute("companyForm", company);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (insertedID == 0) {
			return "redirect:/createCompanyEntry";
		} else {
			company.setLoginFlag(1);
			redirectAttributes.addFlashAttribute("companyForm", company);
			return "redirect:/companyDashboardEntry";
		}
	}

	@GetMapping("/createCompanyWithGoogle")
	public String createCompany(@RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute("companyForm") Company company, RedirectAttributes redirectAttributes,
			HttpSession httpSession, Principal principal, ModelMap modelMap)
			throws JsonProcessingException, ParseException {

		ObjectMapper mapper = new ObjectMapper();
		String principalString = mapper.writeValueAsString(principal);
		modelMap.addAttribute("principal", principalString);
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(principalString);
		JSONObject userAuthentication = (JSONObject) json.get("userAuthentication");
		JSONObject details = (JSONObject) userAuthentication.get("details");

		String name = (String) details.get("name");
		String email = (String) details.get("email");
		String id = (String) details.get("id");
		String picture = (String) details.get("picture");

		company.setCompanyName(name);
		company.setUsername(id);
		String password = "";
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(id.getBytes());
			password = new String(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		company.setPassword(password);
		company.setImagePath(picture);
		company.setEmail(email);
		company.setSignin_from("G");
		company.setProfile_pic_mode("G");
		int authCompanyUserName = companyService.authNewCompanyUserName(company.getUsername());
		int insertedID = 0;
		if (authCompanyUserName == 0) {
			insertedID = companyService.createCompany(company);
			httpSession.setAttribute("company_id", insertedID);
			sendEmail(email,id, id,name);
		} else {
			httpSession.setAttribute("company_id", authCompanyUserName);
		}
		httpSession.setAttribute("company_login", 1);

		redirectAttributes.addFlashAttribute("companyForm", company);
		return "redirect:/companyDashboardEntry";
	}

	@RequestMapping("/createCompanyEntry")
	public ModelAndView createCompanyEntry(ModelAndView modelView, @ModelAttribute("companyForm") Company company) {
		modelView.addObject(company);
		modelView.setViewName("create_company");
		return modelView;
	}

	@RequestMapping("/loginCompany")
	public ModelAndView loginCompany(ModelAndView modelView, @ModelAttribute("companyForm") Company company,
			RedirectAttributes redirectAttributes, HttpSession httpSession) {
		Company cmpny = companyService.authCompany(company);
		if (cmpny != null) {
			httpSession.setAttribute("company_login", 1);
			httpSession.setAttribute("company_id", cmpny.getId());
			company.setLoginFlag(0);
			// redirectAttributes.addFlashAttribute("companyForm", company);
			company.setCompanyName(cmpny.getCompanyName());
			// modelView.addObject(company);
			modelView.setViewName("redirect:/companyDashboardEntry");
			return modelView;
		} else {
			company.setLoginFlag(2);
			redirectAttributes.addFlashAttribute("companyForm", company);
			modelView.setViewName("redirect:/createCompanyEntry");
		}
		return modelView;
	}

	@RequestMapping("/companyDashboardEntry")
	public ModelAndView companyDashboardEntry(ModelAndView modelView, @ModelAttribute("companyForm") Company company,
			HttpSession httpSession, ModelMap model) {

		Object company_login = httpSession.getAttribute("company_login");
		if (company_login == null) {
			modelView.setViewName("redirect:/createCompanyEntry");
			return modelView;
		} else {
			int company_id = (Integer) httpSession.getAttribute("company_id");
			Company cmpny = companyService.getCompany(company_id);

			String encodstring = "";
			if (cmpny.getProfile_pic_mode().equalsIgnoreCase("A")) {
				if (!cmpny.getImagePath().trim().isEmpty()) {
					String filePath = UPLOADED_FOLDER + cmpny.getImagePath();
					File f = new File(filePath);
					encodstring = encodeFileToBase64Binary(f);
					String encodeAppendString = "data:image/" + FilenameUtils.getExtension(filePath) + ";base64,";
					encodstring = encodeAppendString + encodstring;
				}
				model.addAttribute("company_logo", encodstring);
			}else {
				model.addAttribute("company_logo", cmpny.getImagePath());
			}

			List<Employee> employee = employeeService.getEmployeeForCompany(company_id);
			
			model.addAttribute("employee_report_path",cmpny.getEmployee_report_path());
			model.addAttribute("company_name", cmpny.getCompanyName());
			model.addAttribute("email", cmpny.getEmail());
			model.addAttribute("signin_from",cmpny.getSignin_from());
			model.addAttribute("employees", employee);
			
			// modelView.addObject(company);
			modelView.setViewName("company_dashboard");
			return modelView;
		}
	}

	@RequestMapping("/updateCompanyEntry")
	public ModelAndView updateCompanyEntry(ModelAndView modelView, @ModelAttribute("companyForm") Company company,
			HttpSession httpSession) {

		Object company_login = httpSession.getAttribute("company_login");
		if (company_login == null) {
			modelView.setViewName("redirect:/createCompanyEntry");
			return modelView;
		} else {
			int company_id = (Integer) httpSession.getAttribute("company_id");
			company = companyService.getCompany(company_id);
			
			modelView.addObject(company);
			modelView.setViewName("update_company");
			return modelView;
		}	
	}
	
	@RequestMapping("/updateCompany")
	public String updateCompany( @ModelAttribute("companyForm") Company company,
			HttpSession httpSession, @RequestParam(value = "file", required = false) MultipartFile file,RedirectAttributes redirectAttributes,Principal principal) throws JsonProcessingException, ParseException {

		Object company_login = httpSession.getAttribute("company_login");
		if (company_login == null) {
			return "redirect:/createCompanyEntry";
		} else {
			int company_id = company.getId();
			Company cmpny = companyService.getCompany(company_id);
			
			if(!company.getCompanyName().isEmpty()) {
				cmpny.setCompanyName(company.getCompanyName());
			}
			if(!company.getEmail().isEmpty()) {
				cmpny.setEmail(company.getEmail());
			}
			if(!company.getEmail().isEmpty()) {
				cmpny.setEmail(company.getEmail());
			}
			if (!file.isEmpty()) {
				try {
					String extension = FilenameUtils.getExtension(file.getOriginalFilename());

					if (!(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")
							|| extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("bmp")
							|| extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("webp"))) {
						company.setRegFlag(5);
						redirectAttributes.addFlashAttribute("companyForm", company);
						return "redirect:/updateCompanyEntry";
					}

					int id = company.getId();
					id++;
					
					// Get the file and save it somewhere
					byte[] bytes = file.getBytes();
					String uploadDir = UPLOADED_FOLDER + id + "//";
					String fileName = new Timestamp(System.currentTimeMillis()).getTime() + file.getOriginalFilename();
					String path = Paths.get(uploadDir + fileName).toString();

					File directory = new File(uploadDir);
					if (!directory.exists()) {
						directory.mkdir();
					}
					
					boolean close = false;
					FileOutputStream fos = new FileOutputStream(path);
					try {
						
						// Writes bytes from the specified byte array to this file output stream 
						fos.write(bytes);
						
					}catch(Exception e) {
						
					}finally {
						fos.close();
						fos = null;
				        System.gc();
					}
					cmpny.setImagePath(id + "//" + fileName);
					cmpny.setProfile_pic_mode("A");
					
					if(company.getProfile_pic_mode().equalsIgnoreCase("A") && !company.getImagePath().isEmpty()) {		
					// delete image
					
					//DELETE ELEMENT FROM FOLDER
					Path imagesPath = Paths.get(UPLOADED_FOLDER + company.getImagePath());

					try {
					    Files.delete(imagesPath);
					} catch (IOException e) {
					    e.printStackTrace();
					}
				}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else {
				if(company.getGoogleProfilePic() == 1) {
					ObjectMapper mapper = new ObjectMapper();
					String principalString = mapper.writeValueAsString(principal);
					//modelMap.addAttribute("principal", principalString);
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(principalString);
					JSONObject userAuthentication = (JSONObject) json.get("userAuthentication");
					JSONObject details = (JSONObject) userAuthentication.get("details");
					String picture = (String) details.get("picture");
					cmpny.setImagePath(picture);
					cmpny.setProfile_pic_mode("G");
				}
			}
			companyService.updateCompany(cmpny);
			return "redirect:/companyDashboardEntry";
		}	
	}

	
	@RequestMapping("/companyLogout")
	public String companyLogout(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

	@RequestMapping("/loginCompanyEntry")
	public String loginCompanyEntry(HttpServletRequest httpServletRequest, @ModelAttribute("companyForm") Company company) {
		return "login_company";
	}

	private static String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedfile;
	}
	
	@RequestMapping("/generateCompanyPdfReport")
	public String generateCompanyPdfReport(HttpSession httpSession, HttpServletResponse response) throws Exception {
		Object company_login = httpSession.getAttribute("company_login");
		if (company_login == null) {
			return "redirect:/";
		} else {
			int companyId = (Integer) httpSession.getAttribute("company_id");
			Company company = companyService.getCompany(companyId);
			List<Employee> employee = employeeService.getEmployeeForCompany(companyId);
			
			
			
			String uploadDir = REPORT_FOLDER + companyId + "//";
			String fileName = new Timestamp(System.currentTimeMillis()).getTime() + String.valueOf(companyId) + ".pdf";
			Path path = Paths.get(uploadDir + fileName);
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdir();
			} 
			
			String imagePath = "";
			if(company.getProfile_pic_mode().equalsIgnoreCase("A"))
				imagePath = UPLOADED_FOLDER + company.getImagePath();
			else
				imagePath = company.getImagePath(); 
			
			ByteArrayOutputStream out = GeneratePdfReport.generatePdfReport(company,employee,imagePath,path.toString());
			//company.setEmployee_report_path("" + companyId + "/" + fileName);
			//companyService.updateCompany(company);
	
			  response.setHeader("Expires", "0");
	            response.setHeader("Cache-Control",
	                "must-revalidate, post-check=0, pre-check=0");
	            response.setHeader("Pragma", "public");
	            // setting the content type
	            response.setContentType("application/pdf");
	            response.setHeader("Content-Disposition","attachment; filename=report.pdf");
	            // the contentlength
	            response.setContentLength(out.size());
	            // write ByteArrayOutputStream to the ServletOutputStream
	             OutputStream os = response.getOutputStream();
	             out.writeTo(os);
	             os.flush();
	             os.close();
	
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/companyChart")
	public Map<String,String> getSearchResultViaAjax(@RequestBody int companyId) {
		Map<String,String> map=new HashMap<String,String>(); 
		map = companyService.companyChartDataForEmployees(companyId);
		return map;
	}
	
	private boolean sessionSecurity(HttpSession httpSession) {
		Object company_login = httpSession.getAttribute("company_login");
		if (company_login == null) {
			return true;
		} else {
			return false;
		}
	}
	
	private void sendEmail(String to,String username, String password,String companyName) {

        SimpleMailMessage msg = new SimpleMailMessage();
        
        String subject = "credential for company account : " + companyName ;
        String text = "Hi " + companyName + ",\n You have sucessfully sign in with google and you can use below credentials." +
        "\n\n username : " + username + "\n password : " + password;
        
        msg.setTo(to);
        
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);

    }
}
