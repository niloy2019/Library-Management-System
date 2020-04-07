package com.abc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {

	private Connection myCon;
	private PreparedStatement myStmt;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		StringBuffer s = request.getRequestURL();
		System.out.println(s);
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String password = bundle.getString("password");
		
		try {
			String id1 = request.getParameter("id");
			int id = Integer.parseInt(id1);
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			myCon = DriverManager.getConnection(url, user, password);
			String sql = "delete from `user` where `id`= ?";
			myStmt = myCon.prepareStatement(sql);
			myStmt.setInt(1, id);
			int rowsAffected = myStmt.executeUpdate();
			System.out.println(rowsAffected +" Rows Affected");
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
		
		response.sendRedirect("program.jsp");
	}

}
