package com.abc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateBook
 */
@WebServlet("/UpdateBook")
public class UpdateBook extends HttpServlet {

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

			String sql = "UPDATE `book` SET `availiable_books` = `availiable_books`+1  WHERE (`isbn` = ?);";
		
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
		
		response.sendRedirect("book.jsp");
	}

}
