<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Employee</title>
<link rel="stylesheet" type="text/css" href="/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

 <script>
 $(document).ready(function(){
	$('#EmployeeRegSubmitBtn').click(function(){
		var submit = 1;
		
		// company_name
		$("#employee_name_err_msg").css("display","none");	

		if(!$("#employee_name").val()){
			$("#employee_name_err_msg").css("display","block");
			$("#employee_name_err_msg").html("Please Enter Company Name");
			submit = 0;
		}
		
		else if($("#employee_name").val().length <= 2){
			$("#employee_name_err_msg").css("display","block");
			$("#employee_name_err_msg").html("Please Enter Company Name more then 2 character");
			submit = 0;
		}
				
		//username
		$("#username_err_msg").css("display","none");
		if(!$("#username").val()){
			$("#username_err_msg").css("display","block");
			$("#username_err_msg").html("Please Enter Username");
			submit = 0;
		}
		else if($("#username").val().match("[^A-Za-z0-9]+")){
			$("#username_err_msg").css("display","block");
			$("#username_err_msg").html("Please Enter only alphanumeric username");
			submit = 0;
		}else if($("#username").val().length <= 5){
			$("#username_err_msg").css("display","block");
			$("#username_err_msg").html("Please Enter username more then 5 character");
			submit = 0;
		}
		
		//password
		$("#password_err_msg").css("display","none");
		if(!$("#password").val()){
			$("#password_err_msg").css("display","block");
			$("#password_err_msg").html("Password is required");
			submit = 0;
		}else if($("#password").val().length <= 5){
			$("#password_err_msg").css("display","block");
			$("#password_err_msg").html("Please Enter password more then 5 character");
			submit = 0;
		}
		
		// conform password
		$("#conform_password_err_msg").css("display","none");
		if(!$("#conform_password").val()){
			$("#conform_password_err_msg").css("display","block");
			$("#conform_password_err_msg").html("Conform Password is required");
			submit = 0;
		}
		else if(!($("#conform_password").val() === $("#password").val())){
			$("#conform_password_err_msg").css("display","block");
			$("#conform_password_err_msg").html("Password and Conform Password must same");
			submit = 0;
		}
		
		if(submit == 1)
			$('#EmployeeRegForm').submit();
	});
	
	
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
			$("#login_password_err_msg").html("Please Enter Password");
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
		<h1>Create Employee</h1>
		<a href="companyDashboardEntry">Company Dashboard</a>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-sm-6 company-reg-area">
				<h3>
					<u>Employee Registration</u>
				</h3>
				<br />
				<form:form class="form-horizontal" action="createEmployee"
					method="post" modelAttribute="empForm" id="EmployeeRegForm">
					<div class="form-group">
						<label class="control-label col-sm-4" for="email">Employee
							Name:</label>
						<div class="col-sm-8">
							<form:input path="employeeName" placeholder="Employee Name"
								class="form-control" id="employee_name" />
								<p id="employee_name_err_msg" class="errMsg"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="pwd">Username:</label>
						<div class="col-sm-8">
							<form:input path="username" placeholder="Username"
								class="form-control" id="username" />
							<p id="username_err_msg" class="errMsg"></p>	
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="pwd">Password:</label>
						<div class="col-sm-8">
							<form:password path="password" class="form-control" id="password"
								placeholder="Password" />
								<p id="password_err_msg" class="errMsg"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="pwd">Conform
							Password:</label>
						<div class="col-sm-8">
							<input class="form-control" type="password" id="conform_password"
								placeholder="Conform Password" />
								<p id="conform_password_err_msg" class="errMsg"></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-default" id="EmployeeRegSubmitBtn">Submit</button>
						</div>
					</div>
				</form:form>
			</div>

			<!--  
			<div class="col-sm-offset-1 col-sm-5 company-login-area">
				
				<h3>
					<u>Employee Login</u>
				</h3>
				<p id="loginFailMsg">Incorrect username / password. Login fail</p>
				<script>
  					if(${empForm.loginFlag} == 2){
  						document.getElementById("loginFailMsg").style.display="block";
  					}  			
  				</script>
				<br />
				<form:form class="form-horizontal" action="loginEmployee"
					method="post" modelAttribute="empForm" id="EmployeeLoginForm">
					<div class="form-group">
						<label class="control-label col-sm-3" for="pwd">Username:</label>
						<div class="col-sm-7">
							<form:input path="username" placeholder="Username"
								class="form-control" id="login_username" />
							<p id="login_username_err_msg" class="errMsg"></p>	
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3" for="pwd">Password:</label>
						<div class="col-sm-7">
							<form:password path="password" placeholder="Password"
								class="form-control" id="login_password" />
							<p id="login_password_err_msg" class="errMsg"></p>									
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-default" id="EmployeeLoginButton">Submit</button>
						</div>
					</div>
				</form:form>

			</div>
			-->

		</div>
	</div>
</body>
</html>