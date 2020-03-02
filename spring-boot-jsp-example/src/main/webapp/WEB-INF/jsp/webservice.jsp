<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Web service</title>

<link rel="stylesheet" type="text/css" href="/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script> 
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#employeesTbl').DataTable();
	});
</script>
	
</head>
<body>
	<div class="jumbotron text-center">
  		<h1>Employee data from web service</h1>
  	</div>
	
	<div class="container">
		<div class="row">
			<form:form modelAttribute="response" method="get">
				<h1>${response.companyName}</h1>
				<br />
				
		<table id="employeesTbl" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Employee Id</th>
					<th>Employee Name</th>
					<th>Username</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${response.restResponseEmployee}" var="item" varStatus="status">	
    				<tr>
						<td>${item.employee_id}</td>
						<td>${item.employee_name}</td>
						<td>${item.username}</td>					
					</tr>
				</c:forEach>				
			</tbody>
		</table>	
				
			</form:form>
				
		
		</div>
	</div>
	
			
	
</body>
</html>