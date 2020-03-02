<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Login</title>
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
  		<h1>Employee Login</h1>
  		<a href="/">Home</a>
	</div>

	<div class="container">
		<div class="row">
			
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
			
			
		</div>
	</div>
</body>
</html>