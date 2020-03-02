<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Company</title>
<link rel="stylesheet" type="text/css" href="/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script>

 $(document).ready(function(){
	$('#company_reg_btn').click(function(){
		var submit = 1;
		
		// company_name
		$("#company_name_err_msg").css("display","none");	

		if(!$("#company_name").val()){
			$("#company_name_err_msg").css("display","block");
			$("#company_name_err_msg").html("Please Enter Company Name");
			submit = 0;
		}
		
		else if($("#company_name").val().length <= 2){
			$("#company_name_err_msg").css("display","block");
			$("#company_name_err_msg").html("Please Enter Company Name more then 2 character");
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
			$('#createCompanyForm').submit();
	});
	
	
	$('#CompanyloginButton').click(function(){
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
			$('#companyLoginForm').submit();
		
	});
	
 });
	
</script>

</head>
<body>
	<div class="jumbotron text-center app-header">
  		<h1>Create Comapany</h1>
  		<a href="/">Home</a>
	</div>

	<div class="container">
		<div class="row" class="create-company-area">
			<div class="col-sm-6 company-reg-area">
	<h3><u>Company Registration</u></h3><br />
	<p id="regMsg"> </p>
	  <script>
  		if(${company.regFlag} == 3){
  			document.getElementById("regMsg").style.display="block";
  			document.getElementById("regMsg").innerHTML = "Username already exist! please try different username.";
  		}  	
  		
  		if(${company.regFlag} == 5){
  			document.getElementById("regMsg").style.display="block";
  			document.getElementById("regMsg").innerHTML = "Image file format is not allowe. Please upload a valid image.";
  		}  	
  </script>		
<form:form class="form-horizontal" action="/createCompany" method="POST" modelAttribute="company" enctype="multipart/form-data" id="createCompanyForm">
  <div class="form-group">
    <label class="control-label col-sm-4" for="email">Company Name : *</label>
    <div class="col-sm-8">
      <form:input path="companyName" placeholder="Employee Name" class="form-control" id="company_name" />
      <p id="company_name_err_msg" class="errMsg"></p>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-4" for="email">Company Email : *</label>
    <div class="col-sm-8">
      <form:input path="email" placeholder="Employee Name" class="form-control" id="company_name" />
      <p id="company_email_err_msg" class="errMsg"></p>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-4" for="email">Company Logo :</label>
    <div class="col-sm-8">
      <input type="file" name="file" class="form-control" />
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-4" for="email">Username : *</label>
    <div class="col-sm-8">
      <form:input path="username" placeholder="username" class="form-control" id="username" />
      <p id="username_err_msg" class="errMsg"></p>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-4" for="email">Password : *</label>
    <div class="col-sm-8">
      <form:password path="password" id="password" class="form-control" placeholder="password" />
      <p id="password_err_msg" class="errMsg"></p>      
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-4" for="email">Conform Password : *</label>
    <div class="col-sm-8">
      <input class="form-control" type="password" id="conform_password" placeholder="Conform Password" name="conformpass" />
      <p id="conform_password_err_msg" class="errMsg"></p> 
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <input type="button" value="Register" class="btn btn-default" id="company_reg_btn" />
    </div>
  </div>
</form:form>
			</div>


 
			<div class="col-sm-offset-1 col-sm-5 company-login-area">
	<h3><u>Company Login</u></h3><br />		
	<p id="loginFailMsg">Incorrect username / password. Login fail</p>
	  <script>
  		if(${company.loginFlag} == 2){
  			document.getElementById("loginFailMsg").style.display="block";
  		}  			
  </script>
<form:form class="form-horizontal" action="loginCompany" method="post" modelAttribute="companyForm" id="companyLoginForm">
  <div class="form-group">
    <label class="control-label col-sm-3" for="pwd">Username:</label>
    <div class="col-sm-7">
      <form:input path="username" placeholder="Username" class="form-control" id="login_username"  />
      <p id="login_username_err_msg" class="errMsg"></p>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-3" for="pwd">Password:</label>
    <div class="col-sm-7">
      <form:password path="password" placeholder="Password" class="form-control" id="login_password" />
      <p id="login_password_err_msg" class="errMsg"></p>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="button" id="CompanyloginButton" class="btn btn-default">Submit</button>
    </div>
  </div>
</form:form>
			</div>
			
			
			
		</div>
	</div>
</body>
</html>