package com.abc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Validate")
public class Validate extends HttpServlet {

	
	private Connection myCon=null;
	private PreparedStatement myStmt=null;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String password = req.getParameter("password");
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String pass = bundle.getString("password");
		
		HttpSession session = req.getSession();
		int login =(int) session.getAttribute("login");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			myCon = DriverManager.getConnection(url, user, pass);
			
			String sql = "insert into user(`name`,`age`,`email`,`mobile`,`password`) values(?,?,?,?,?)";
			
			myStmt = myCon.prepareStatement(sql);
			
			myStmt.setString(1, name);
			myStmt.setString(2, age);
			myStmt.setString(3, email);
			myStmt.setString(4, mobile);
			myStmt.setString(5, password);
			
			int i = myStmt.executeUpdate();
			
			
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
					
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(login==0) {
			resp.sendRedirect("Home.jsp");
		}else {
			resp.sendRedirect("program.jsp");
		}
	}
}
