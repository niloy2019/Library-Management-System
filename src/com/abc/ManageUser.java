package com.abc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ManageUser")
public class ManageUser extends HttpServlet {
	
	private Connection myCon=null;
	private Statement myStmt=null;
	private ResultSet resultSet=null;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
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
				
				
				
				PrintWriter out = response.getWriter();
				
				
				out.println("	<table border=\"0\" width=\"100%\">\r\n" + 
						"		<tr>\r\n" + 
						"			<td width=\"10%\">"+id+"</td>\r\n" + 
						"			<td width=\"10%\">"+name+"</td>\r\n" + 
						"			<td width=\"10%\">"+age+"</td>\r\n" + 
						"			<td width=\"30%\">"+email+"</td>\r\n" + 
						"			<td width=\"10%\">"+mobile+"</td>\r\n" + 
						"			<td width=\"10%\">"+pass+"</td>\r\n" + 
						"			<td> <a href=\"DeleteUser?id="+id+"\">  Delete User</a>  </td>" + 
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
	
	}

}
