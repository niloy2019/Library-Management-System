<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
 
  <style>
  	body{
  	    background-image: linear-gradient(rgba(0,0,0,0.7),rgba(0,0,0,0.7)),url("images/a.jpg");
  	    background-size: cover;
  	}
  	.a{
  		color: gray;
  		font-size: 20px;
  	}
  </style>

<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<body  class="col-sm-4" style="margin-left:450px;">
	
	<%  

		session.setAttribute("login", 0);	
		
	   
	%>
	<br><br><br><br>	
	<h1 style="color: green; ">Welcome to Library</h1>
	
	
	<form action="Login">
		<span class="a"> Username </span>   <input type="text"  required="required" name="username" class="form-control"> <br>
		<span class="a"> Password </span>  <input type="password" required="required" name="password" class="form-control"> <br>
		<button type="submit" class="btn btn-primary">Login</button>
	</form>
	
	<br>
	
	<form action="registration.html">
	
	<button type="submit" class="btn btn-success">Register</button>
	
	</form>

	<%
		
	 Object s=session.getAttribute("validate");
	 if(s!=null){
		 int d=(int)s;
		 if(d==1){
			 out.println("<h3>Wrong Username or Password Entered</h3>");
		 }else{
			 
		 }
	 }
	%>
</body>
</html>