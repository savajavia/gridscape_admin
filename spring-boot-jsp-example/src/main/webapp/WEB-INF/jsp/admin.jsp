<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Company Dashboard</title>

<link rel="stylesheet" type="text/css" href="/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script> 

 <script>
 $(document).ready(function(){
	 $('#EmployeeLoginButton').click(function(){
			var submit = 1;
			$("#login_username_err_msg").css("display","none");
			
			if(!$("#login_username").val()){
				$("#login_username_err_msg").css("display","block");
				$("#login_username_err_msg").html("Please Enter Username");
				submit = 0;
			}
			if(!$("#login_password").val()){
				$("#login_password_err_msg").css("display","block");
				$("#login_password_err_msg").html("Please Enter Username");
				submit = 0;
			}
			if(submit == 1)
				$('#EmployeeLoginForm').submit();
			
		});
		
	 });	 
 
 
 </script>
</head>
<body>
	<div class="jumbotron text-center">
  		<h1>Admin</h1>
  		<a href="createCompanyEntry">Create / Login Company</a> &nbsp;&nbsp;
  		<a href="employeeLoginEntry">Employee Login</a>  <br />
  		<a href="createCompanyWithGoogle">Signin with google</a>		
	</div>
	
	<div class="container">
		<div class="row">
			
				
		
		</div>
	</div>
	
			
	
</body>
</html>