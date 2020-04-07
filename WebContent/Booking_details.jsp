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
  	    background-repeat:  repeat;
  	}
  	
  </style>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<body>
	
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
	

	
	<h2 style="color: green;">Book Information</h2>
	
	<%
	 int status=(int)session.getAttribute("msg"); 
	  if(status==3){
		    out.println("	<h3 style=\"  color: red; background-color:white \">* This Book is currently not Availiable in Library </h3>");
	  }else if(status==1){
	    	out.println("   <h3 style=\"  color: red; background-color:white \">* Cannot Issue The Same Book Twice </h3>");
	  }else if(status==2){
			out.println("   <h3 style=\"  color: red; background-color:white \">* Cannot Issue More Than 5 Book !! Return at least 1 Book to Issue a New Book ! </h3>");
	  }else if(status==0){
			out.println(" 	<h3 style=\"  color: green; background-color:white\"> Book Issued Successfully  !!!</h3>");
	  }else{
		  
	  }
	%>
	
	<table width="100%" class="table  table-hover table-striped">
		<tr>
			<th width="5%">ISBN</th>
			<th width="20%">Book Name</th>
			<th width="20%">Author Name</th>
			<th width="10%">Availiable_Books</th>
			<th width="10%">Rating</th>
			<th width="8%">Issue Book</th>
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
		resultSet = myStmt.executeQuery("select * from `book`");
		
		while(resultSet.next()) {
			int book_id = resultSet.getInt(1);
			String book = resultSet.getString(2);
			String author = resultSet.getString(3);
			String availiable_books = resultSet.getString(4);
			String rating = resultSet.getString(5);
			
			out.println("<table  border=\"0\" style=\"background-color:white; \" width=\"100%\" class=\"table table-bordered table-hover table-striped \"  >\r\n" + 
					"		<tr>\r\n" + 
					"			<td width=\"5%\">"+book_id+"</td>\r\n" + 
					"			<td width=\"20%\">"+book+"</td>\r\n" + 
					"			<td width=\"20%\">"+author+"</td>\r\n" + 
					"			<td width=\"10%\">"+availiable_books+"</td>\r\n" + 
					"			<td width=\"10%\">"+rating+"</td>\r\n" + 
					"			<td width=\"8%\"> <a href=\"IssueBooks?id="+book_id+"\"> <button type=\"submit\" class=\"btn btn-success\"> Issue Book </button></a>  </td>" + 
					"		</tr>\r\n" + 
					"	</table> \r\n" + 
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
	
	
	
	 
	  session.setAttribute("msg", 10);
	
   %>
   
</body>
</html>