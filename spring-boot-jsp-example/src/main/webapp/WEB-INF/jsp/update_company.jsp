<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Company</title>
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
				
		
		
		if(submit == 1){
			$('#logoInput').prop("disabled", false);
			$('#createCompanyForm').submit();			
		}
	});
	

	
	$('#google_profile_pic_check').change(function () {
	    if (this.checked) {
	    	 $('#logoInput').prop("disabled", true);
	    	 $('#googleProfilePic').val(1);
	    }else{
	    	 $('#logoInput').prop("disabled", false);	    	
	    	 $('#googleProfilePic').val(0);
	    }
	});
	
 });
	
</script>

</head>
<body>
	<div class="jumbotron text-center app-header">
  		<h1>Update Comapany</h1>
  		<a href="/">Home</a>
	</div>

	<div class="container">
		<div class="row" class="create-company-area">
			<div class="col-sm-6 company-reg-area">
	<h3><u>Update Company</u></h3><br />
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
<form:form class="form-horizontal" action="/updateCompany" method="POST" modelAttribute="company" enctype="multipart/form-data" id="createCompanyForm">
  <form:hidden path="id" />
  <form:hidden path="profile_pic_mode" />
  <form:hidden path="imagePath" />
  <form:hidden path="googleProfilePic" id="googleProfilePic" />
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
      <input type="file" name="file" class="form-control" id="logoInput" /><br />
       <c:if test = "${company.signin_from eq 'G'}">
      		<input type="checkbox" id="google_profile_pic_check" />
       		<label class="control-label">Use google profile image</label>
       </c:if>
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <input type="button" value="Update Profile" class="btn btn-default" id="company_reg_btn" />
    </div>
  </div>
</form:form>
			</div>
		
			
		</div>
	</div>
</body>
</html>