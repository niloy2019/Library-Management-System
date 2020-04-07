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
 * Servlet implementation class AddBook
 */
@WebServlet("/AddBook")
public class AddBook extends HttpServlet {
	
	private Connection myCon=null;
	private PreparedStatement myStmt=null;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Add Book called");
		
		String book = req.getParameter("book");
		String author = req.getParameter("author");
		String availiable_book = req.getParameter("availiable_book");
		String rating = req.getParameter("rating");
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String pass = bundle.getString("password");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			myCon = DriverManager.getConnection(url, user, pass);
			
			String sql = "insert into book(`book`,`author`,`availiable_books`,`rating`) values(?,?,?,?)";
			
			myStmt = myCon.prepareStatement(sql);
			
			myStmt.setString(1, book);
			myStmt.setString(2, author);
			myStmt.setString(3, availiable_book);
			myStmt.setString(4, rating);
			
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
		
		resp.sendRedirect("book.jsp");
	}
}
