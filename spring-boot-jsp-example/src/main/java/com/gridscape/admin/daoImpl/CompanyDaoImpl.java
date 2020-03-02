package com.gridscape.admin.daoImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gridscape.admin.dao.CompanyDao;
import com.gridscape.admin.model.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	
	@Autowired
	SessionFactory sessionFactory;
		
	public int createCompany(Company company) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(company);
		return company.getId();
	}
	public void updateCompany(Company company) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(company);
	}
	public List<Company> getCompanyList() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Company> cmpny = session.createQuery("from Company").list();	
		return cmpny;
	}

	public Company getCompany(int company_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Company cmpny = (Company)session.createQuery("from Company c where c.id = :id").setInteger("id", company_id).uniqueResult();
		return cmpny;
	}
	
	public int getLatestCompanyId() {
		Session session = this.sessionFactory.getCurrentSession();
		int id = (Integer)session.createQuery("Select coalesce(max(cmp.id),'0') FROM Company cmp").uniqueResult();	
		return id;
	}

	public Company authCompany(Company company) {
		Session session = this.sessionFactory.getCurrentSession();
		String username = company.getUsername();
		String password = company.getPassword();

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());			
			Company cmpny = (Company)session.createQuery("from Company c where c.username = :username and c.password = :password").setString("username", username).setString("password", encryptedPassword).uniqueResult();			
			return cmpny;			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	public int authNewCompanyUserName(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Company cmpny = (Company)session.createQuery("from Company c where c.username = :username").setString("username", username).uniqueResult();
		if(cmpny == null)
			return 0;
		else
			return cmpny.getId();
	}

	public Map<String,String> companyChartDataForEmployees(int companyId) {
		Map<String,String> map=new HashMap<String,String>(); 
		Session session = this.sessionFactory.getCurrentSession();
		
		List employeeList = session.createQuery("Select emp.create_date, count(emp.id) from Employee emp group by emp.create_date").list();
		Iterator it = employeeList.iterator();
		while(it.hasNext())
		{
		Object[] obj = (Object[])it.next();
		
		map.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
		
		}
		return map;
	}

	
}

