<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Company Login</title>
<link rel="stylesheet" type="text/css" href="/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script> 
  
</head>
<body>
	<div class="jumbotron text-center">
  		<h1>Company Login</h1>
  		<a href="createCompanyEntry">Create Company</a>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-sm-10">
	
<form:form class="form-horizontal" action="loginCompany" method="post" modelAttribute="companyForm">
  <div class="form-group">
    <label class="control-label col-sm-2" for="pwd">Username:</label>
    <div class="col-sm-5">
      <form:input path="username" placeholder="Username" class="form-control"  />
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="pwd">Password:</label>
    <div class="col-sm-5">
      <form:password path="password" placeholder="Password" class="form-control"  />
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Submit</button>
    </div>
  </div>
</form:form>
			</div>
		</div>
	</div>
</body>
</html>