<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Company Dashboard</title>

<link rel="stylesheet" type="text/css" href="/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>


<script src="https://code.highcharts.com/highcharts.js"></script>


<script>
	$(document).ready(function() {
		$('#employeesTbl').DataTable();
		
		var data = 1;
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/companyChart",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				//console.log("SUCCESS: ", data);
				var responseArr = JSON.parse(JSON.stringify(data));
				
				var chartData = new Array();
				for (var key in responseArr) {
					  if (responseArr.hasOwnProperty(key)){
						  
						  var dateArr = key.split("-");
						  var utcData = Date.UTC(parseInt(dateArr[0]),parseInt(dateArr[1])-1,parseInt(dateArr[2]));
						  var rowData = new Array();
						  rowData.push(utcData);
						  rowData.push(parseInt(responseArr[key]));
						  chartData.push(rowData);
					  }
					   
				}
				
				
				series = myChart.get("employeeSeries");
				if(series) {
	                series.remove();
	            }
	                myChart.addSeries({
	                    id: "employeeSeries",
	                    name: "Employees",
	                    data: chartData
	                });
	            
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
		
	});
	
	function googleLogout(signin_from){
		if(signin_from == "G"){
			var newWindow = window.open('https://mail.google.com/mail/?logout&hl=fr','Disconnect from Google','width=100,height=50,menubar=no,status=no,location=no,toolbar=no,scrollbars=no,top=200,left=200');
			 setTimeout(function(){
			     if (newWindow) newWindow.close();
			     window.location="companyLogout";
			 },3000);			
		}else{
			window.location.href="/companyLogout";
		} 

	 }
	
	
	
	// highcharts
	//var emplData = new Araay();

	var myChart;
	document.addEventListener('DOMContentLoaded', function () {
        myChart = Highcharts.chart('container', {
            chart: {
                type: 'line'
            },
            title: {
                text: 'Employee creation dates'
            },
            xAxis: {
                type: 'datetime',
                    dateTimeLabelFormats:{ 
                        day: "%e-%b-%y",
                        month: "%b-%y"
		    }
            },
            yAxis: {
                title: {
                    text: 'Number of employees created'
                }
            },
            series: [{
            	id: 'employeeSeries',
            	name: 'Employees',
    data: []
    	
    
  }]
        });
    });

	
	
</script>

</head>
<body>
	<div class="jumbotron text-center">
		<c:if test="${not empty company_logo}">
    		<img id="company_logo" src="${company_logo}" width="300" />
		</c:if>
		
		<h1>${company_name}</h1> <br /> <h6>${email}</h6><br />
		<a href="/updateCompanyEntry">Update profile</a> &nbsp;&nbsp;
		<a href="createEmployeeEntry">Create Employee</a> &nbsp;&nbsp;
		<a href="generateCompanyPdfReport">Create Employee Report</a> &nbsp;&nbsp;
		<button type="button" onclick="googleLogout('${signin_from}')">Logout</button>
			
	</div>
	<!--  <form:form action="#" method="POST" modelAttribute="companyForm">
		<div class="jumbotron text-center">
			
		</div>
	</form:form> -->
	<br />
	<br />

	<div class="container">
		<div class="row">
	
		</div>
		<br />
		<hr /><h3>Employees</h3><hr /><br />
		<br />
		<table id="employeesTbl" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Name</th>
					<th>Username</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${employees}" var="item">	
    				<tr>
						<td>${item.employeeName}</td>
						<td>${item.username}</td>
					</tr>
				</c:forEach>				
			</tbody>
		</table>
		<br />
		<br />
		<br />
		<hr /><h3>Employees creation chart</h3><hr /><br />
		<div id="container" style="width:100%; height:400px;"></div>
	</div>
</body>
</html>