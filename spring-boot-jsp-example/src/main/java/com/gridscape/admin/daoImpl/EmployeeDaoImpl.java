package com.gridscape.admin.daoImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.gridscape.admin.dao.EmployeeDao;
import com.gridscape.admin.model.Employee;

@Repository  
public class EmployeeDaoImpl implements EmployeeDao {

	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	public int createEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		int id = (Integer)session.save(employee);
		return id;
	}
	
	public Employee authEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		String username = employee.getUsername();
		String password = employee.getPassword();

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			String encryptedPassword = new String(messageDigest.digest());
			
			Employee emp = (Employee)session.createQuery("from Employee e where e.username = :username and e.password = :password").setString("username", username).setString("password", encryptedPassword).uniqueResult();
			
			return emp;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public boolean authNewEmployeeUserName(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee employee = (Employee)session.createQuery("from Employee e where e.username = :username").setString("username", username).uniqueResult();
		if(employee == null)
			return true;
		else
			return false;
	}

	public List<Employee> getEmployeeForCompany(int companyId){
		Session session = this.sessionFactory.getCurrentSession();
		List<Employee> employee = session.createQuery("from Employee e where e.companyId = :companyId").setInteger("companyId",companyId).list();
		return employee;
	}
	
	public Employee getEmployee(int empId) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee emp = (Employee)session.createQuery("from Employee e where e.id = :empId").setInteger("empId",empId).uniqueResult();
		return emp;
	}
}
