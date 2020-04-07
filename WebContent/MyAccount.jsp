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
  
<body >
	
	<%int id=(int)session.getAttribute("id"); %>
	
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
	
	<h2 style="color: green;">Booking Information</h2>
	
	<table width="100%" class="table  table-hover table-striped">
		<tr>
			<th width="5%">Book Id</th>
			<th width="25%">Book Name</th>
			<th width="25%">Author</th>
			<th width="10%">Rating</th>
			<th width="12.5%">Issue Date</th>
			<th width="12.5%">Return Date</th>
			<th width="10%">Return Book</th>
		</tr>
	</table>

	
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
		myStmt = myCon.createStatement();
		resultSet = myStmt.executeQuery("select `book_isbn`,`book_name`,`author`,`rating`,`issue_date`,`return_date` from `issue_details` where `user_id`="+id);
		
		while(resultSet.next()) {
			int isbn = resultSet.getInt(1);
			String bookName = resultSet.getString(2);
			String author = resultSet.getString(3);
			String rating = resultSet.getString(4);
			String issueDate = resultSet.getString(5);
			String returnDate = resultSet.getString(6);
			
			
			
			
			
			out.println("	<table border=\"1\" width=\"100%\" class=\"table table-bordered table-hover table-striped \" style=\"border-collapse: collapse;\" >\r\n" + 
					"		<tr>\r\n" + 
					"			<td width=\"5%\">"+isbn+"</td>\r\n" + 
					"			<td width=\"25%\">"+bookName+"</td>\r\n" + 
					"			<td width=\"25%\">"+author+"</td>\r\n" + 
					"			<td width=\"10%\">"+rating+"</td>\r\n" + 
					"			<td width=\"12.5%\">"+issueDate+"</td>\r\n" + 
					"			<td width=\"12.5%\">"+returnDate+"</td>\r\n" + 
					"			<td width=\"10%\"> <a href=\"ReturnBook?id="+isbn+"\">  <button type=\"submit\" class=\"btn btn-danger\"> Return Book </button></a>  </td>" + 
					"		</tr>\r\n" + 
					"	</table>\r\n" + 
					"");
		}
		
		
		
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
	
	int returnStatus=(int)session.getAttribute("return");
	if(returnStatus==1){
		out.println("<h3 style=\"  color: green; background-color:white \"> Book Returned Successfully !!! </h3>");
		session.setAttribute("return", 0);
	}else{
		
	}
	
   %>
	
</body>
</html>