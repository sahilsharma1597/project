<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <script>
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }

    </script>
    <link type="text/css" href="<c:url value='/assets/css/font-awesome.min.css' />" rel="stylesheet" />
    <link type="text/css" href="<c:url value='/assets/css/styles.css' />" rel="stylesheet" />
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	<link href="//fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	
</head>
<style>
body {
	padding: 0;
	margin: 0;
	background: #fff;
	min-height: 100vh;
    	background: linear-gradient(to left, #f5f5f5 80%, #fff 20%);
	font-family: 'Raleway', sans-serif;
}
.w3_info {
    flex-basis: 50%;
	-webkit-flex-basis: 45%;
    box-sizing: border-box;
	padding: 3em 4em;
    /* -webkit-box-shadow: 1px 0px 15px 0px rgba(0,0,0,0.2);
    -moz-box-shadow: 1px 0px 15px 0px rgba(0,0,0,0.2);
    box-shadow: 1px 0px 15px 0px rgba(0,0,0,0.2); */
}

input.a
{
 	background-color: #3BABD1; /* Green */
	color: white;
  	padding: 18px 32px;
  	text-align: center;
  	text-decoration: none;
  	display: inline-block;
  	font-size: 16px;
	width: 210px;
	margin-top:29px;
	border-radius:25px;
	webkit-transition-duration: 0.4s; /* Safari */
 	transition-duration: 0.4s;
}
input.a:hover {
  background-color: #3BABD1; /* Green */
  color: white;
  text-align: center;
  font-size: 18px;
}


</style>
<body>

<div class="signupform">
	<div class="containers">
		<div class="agile_info">
			<div class="w3land_form">
				<div class="left_grid_info">
					<img src="<c:url value='/assets/img/profile.png' />" alt="" height="200"  width="180" style="margin-top:80px; max-width:100%;height:auto;" />
					<font size="5vw"><h2 style="margin-top:25px; font-family:Palatino Linotype;">Hello ${name} !!!!</h2>
					<h4 style="margin-top:25px; font-family:Palatino Linotype;">ID : ${id }</h4></font>
					<p><input class="a" style="margin-top:70px; background-color:#4CAF50; pointer-events: none;" type="submit" disabled value="New Candidate"/></p>
					<p><input class="a" type="submit" onclick="location.href='View.jsp'" value="Existing Candidate"/></p>
					<p><input class="a" type="submit" onclick="location.href='ViewAll.jsp'" value="View All Candidates"/></p>
				</div>
			</div>
			<div class="w3_info">
				<h2>Enter the Candidate Details</h2>
				<form action="cand" method="post" autocomplete="off" modelAttribute="cand">
					<label >Candidate ID</label>
					<span class="fa fa-info-circle" title="Candidate ID must comprise of 6 digits" aria-hidden="true"></span>
					<div style="margin-top:15px; margin-bottom:30px" class="input-group" >
						<span class="fa fa-credit-card" aria-hidden="true"></span>
						<input type="text" pattern="^([0-9]{6})$" name="cand_id"placeholder="Enter EmployeeID" required=""> 
					</div>
					
					<label >Candidate Name</label>
					<div style="margin-top:15px; margin-bottom:30px" class="input-group" >
						<span class="fa fa-pencil" aria-hidden="true"></span>
						<input type="text" name="cand_name" placeholder="Enter Candidate Name" required=""> 
					</div>
					<label for="domain">Domain</label>
					<div style="margin-top:15px; margin-bottom:30px" class="input-group" >
						<span class="fa fa-sitemap" aria-hidden="true"></span>
    						<select id="category" name="domain">
  					  	  <option value="java">JAVA</option>
  						  <option value="dotnet">.NET</option>
   						</select>
					</div>
					<label >Experience</label>
					<div style="margin-top:15px; margin-bottom:30px" class="input-group" >
						<span class="fa fa-briefcase" aria-hidden="true"></span>
						<input type="number" name="experience" min="0" placeholder="Enter Experience" required=""> 
					</div>
					
					
					<button class="btn btn-success btn-block" type="submit">Upload CV and Submit</button >      ${message}          
				</form>
				
			</div>
		</div>
	</div>
</div>
	
</body>
</html>