<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.io.*,java.sql.*,javax.servlet.*" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
  	body{
  	    background-image: linear-gradient(rgba(0,0,0,0.7),rgba(0,0,0,0.7)),url("images/a.jpg");
  	    background-size: cover;
  	}
  	.a{
  		font-size: 18px;
  		color:white;
  		padding-bottom: 10px;
  	}
  	.b{
  		font-size: 18px;
  		color:white;
  	}
  	.c{
  		font-size: 18px;
  		color:white;
  	}
  </style>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  
<body  >
	
	
	<% 
	  int id=(int)session.getAttribute("id");
	  session.setAttribute("msg", 10);
	  session.setAttribute("return", 0);
      System.out.println( id); %>

		<br>
	<table width="100%">
		<tr  style="text-align:center;">
			
			<td width="25%">
				<form action="user.jsp?id=<%=id%>">
				
					<button type="submit" class="btn btn-primary"> Home</button>
				
				</form>
		    </td>
		    
			<td width="25%">
				<form action="Booking_details.jsp?id=<%=id%>">
				
					<button type="submit" class="btn btn-primary"> Issue Book </button>
				
				</form>
		    </td>

		     <td width="25%" style="text-align:center;">
				<form action="MyAccount.jsp">
					
					<button type="submit" class="btn btn-primary"> My Account</button>
				
				</form>
			</td>
			
		     <td width="25%" style="text-align:center;">
				<form action="Home.jsp">
					
					<button type="submit" class="btn btn-primary">Log out</button>
				
				</form>
			</td>
		</tr>
	</table>
	
	
	
	
	<br>
	<h1  style="color: green;"> Welcome to Happy Library</h1> <br>
	<h2 style="color: green;">Your Information</h2>
	

	<%!	
		private Connection myCon=null;
		private Statement myStmt=null;
		private ResultSet resultSet=null;
	%>

	<% 
	
	ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
	String url = bundle.getString("url");
	String user = bundle.getString("user");
	String password = bundle.getString("password");
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		myCon = DriverManager.getConnection(url, user, password);
		
		String sql="select * from `user` where `id`= ?";
		PreparedStatement myStmt=myCon.prepareStatement(sql);

		myStmt.setInt(1, id);
		
		resultSet=myStmt.executeQuery();
		
		while(resultSet.next()) {
			
			String name = resultSet.getString(2);
			String age = resultSet.getString(3);
			String email = resultSet.getString(4);
			String mobile = resultSet.getString(5);
			
			out.println("	<table  width=\"100%\" >\r\n" + 
					"			<tr width=\"100%\"> <td class=\"a\" width=\"12%\">  User ID    </td>  <td width=\"8%\" class=\"b\"> : </td>   <td class=\"c\">    " + id     + "      </td> </tr>  \r\n" + 
					"			<tr> <td class=\"a\">  Name       </td>  <td class=\"b\"> : </td>   <td class=\"c\"> 	" + name   + "  	</td> </tr>  \r\n" + 
					"			<tr> <td class=\"a\">  Age        </td>  <td class=\"b\"> : </td>   <td class=\"c\"> 	" + age    + "      </td> </tr>  \r\n" + 
					"			<tr> <td class=\"a\">  Email      </td>  <td class=\"b\"> : </td>   <td class=\"c\"> 	" + email  + "      </td> </tr>  \r\n" + 
					"			<tr> <td class=\"a\">  Mobile     </td>  <td class=\"b\"> : </td>   <td class=\"c\"> 	" + mobile + "      </td> </tr>  \r\n" + 
					"	</table>\r\n" + 
					"");
		}
		
//		<button type=\"submit\" class=\"btn btn-primary\"> Issue Book </button>
		
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