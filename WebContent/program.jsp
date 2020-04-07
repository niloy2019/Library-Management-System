<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.io.*,java.sql.*,javax.servlet.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  
  <style>
		body{
	  	    background-image: linear-gradient(rgba(0,0,0,0.7),rgba(0,0,0,0.7)),url("images/a.jpg");
	  	    background-size: cover;
	  	}
  </style>


<body>
<br>
	<table width="100%">
		<tr  style="text-align:center;">
			
			<td width="25%">
				<form action="admin.html">
				
					<button type="submit" class="btn btn-primary"> Home</button>
				
				</form>
		    </td>
		    
			<td width="25%">
				<form action="program.jsp">
				
					<button type="submit" class="btn btn-primary"> Manage User </button>
				
				</form>
		    </td>

		     <td width="25%" style="text-align:center;">
				<form action="book.jsp">
					
					<button type="submit" class="btn btn-primary"> Manage Books</button>
				
				</form>
			</td>
			
		     <td width="25%" style="text-align:center;">
				<form action="Home.jsp">
					
					<button type="submit" class="btn btn-primary"> Log out</button>
				
				</form>
			</td>
		</tr>
	</table>
 
	
	<h2 style="color: green">User Information</h2>
	
	<table width="100%" class="table  table-hover table-striped">
		<tr>
			<th width="5%">Id</th>
			<th width="10%">Name</th>
			<th width="10%">Age</th>
			<th width="20%">Email</th>
			<th width="10%">Mobile</th>
			<th width="10%">Password</th>
			<th width="10%">Delete User</th>
		</tr>
	</table>
	
	 <form action="Validate">
   
	   <table width="100%" class="table  table-hover table-striped">
			<tr>
				<th width="5%">Add User</th>
				<td width="10%"> <input type="text" required name="name" class="form-control" >  </td>
				<td width="10%"> <input type="text" required name="age" class="form-control" >   </td>
				<td width="20%"> <input type="email" required name="email" class="form-control" ></td>
				<td width="10%"> <input type="number" required name="mobile" class="form-control" ></td>
				<td width="10%"> <input type="password" required name="password" class="form-control" ></td>
				<td width="10%"> <input type="submit" class="btn btn-success"></td>
			</tr>
		</table>
		
	</form>	

	<%!	
		private Connection myCon=null;
		private Statement myStmt=null;
		private ResultSet resultSet=null;
	%>

	<%  
	session.setAttribute("login", 1);
	ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
	String url = bundle.getString("url");
	String user = bundle.getString("user");
	String password = bundle.getString("password");
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		myCon = DriverManager.getConnection(url, user, password);
		myStmt = myCon.createStatement();
		resultSet = myStmt.executeQuery("select * from `user`");
		
		while(resultSet.next()) {
			
			String id = resultSet.getString(1);
			String name = resultSet.getString(2);
			String age = resultSet.getString(3);
			String email = resultSet.getString(4);
			String mobile = resultSet.getString(5);
			String pass = resultSet.getString(6);
			
			
			
			//PrintWriter out = response.getWriter();
			
			
			out.println("	<table border=\"1\" width=\"100%\" class=\"table table-bordered table-hover table-striped \" style=\"border-collapse: collapse;\" >\r\n" + 
					"		<tr>\r\n" + 
					"			<td width=\"5%\">"+id+"</td>\r\n" + 
					"			<td width=\"10%\">"+name+"</td>\r\n" + 
					"			<td width=\"10%\">"+age+"</td>\r\n" + 
					"			<td width=\"20%\">"+email+"</td>\r\n" + 
					"			<td width=\"10%\">"+mobile+"</td>\r\n" + 
					"			<td width=\"10%\">"+pass+"</td>\r\n" + 
					"			<td width=\"10%\"> <a href=\"DeleteUser?id="+id+"\">  <button type=\"submit\" class=\"btn btn-danger\"> Delete User </button></a>  </td>" + 
					"		</tr>\r\n" + 
					"	</table>\r\n" + 
					"");
		}
		
		
//		
		
	} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		e.printStackTrace();
	}finally {
		
		try {
			if(myCon!=null) {
					myCon.close();
			}
			
			if(myStmt!=null) {
				myStmt.close();
			}
			
			if(resultSet!=null) {
				resultSet.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

   %>
   
  




</body>
</html>