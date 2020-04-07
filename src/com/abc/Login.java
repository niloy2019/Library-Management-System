package com.abc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;


@WebServlet("/Login")
public class Login extends HttpServlet {

	
	private Connection myCon=null;
	private Statement myStmt=null;
	private ResultSet resultSet=null;
    private  int count=0;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Login Page");
	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String adminUsername = "admin";
		String adminPassword = "admin123";
		
		request.setAttribute("id", 1);
		HttpSession session = request.getSession();
		session.setAttribute("validate", 0);
		
		if(username.equals(adminUsername) && password.equals(adminPassword)) {
			count++;
			response.sendRedirect("admin.html");
		}else {
			
			
			ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
			
			String url = bundle.getString("url");
			String user = bundle.getString("user");
			String pass = bundle.getString("password");
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				myCon = DriverManager.getConnection(url,user,pass);
				myStmt = myCon.createStatement();
				String sql = "select `name`,`password`,`id` from user";
				resultSet = myStmt.executeQuery(sql);
				
				while(resultSet.next()) {
					String username1 = resultSet.getString(1);
					String password1= resultSet.getString(2);
					int id= resultSet.getInt(3);
					if(username1.equals(username) && password1.equals(password)) {
//						response.sendRedirect("User");
						PrintWriter out = response.getWriter();
						count++;
						request.setAttribute("id", id);
						session.setAttribute("id", id);
						request.getRequestDispatcher("user.jsp").forward(request, response);
//						response.sendRedirect("user.jsp?id="+id);
					}
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

		if(count==0) {
			System.out.println("CONTROL CAME TO LOGIN.JAVA");
			session.setAttribute("validate", 1);
			response.sendRedirect("Home.jsp");
		}
		
	}

}
