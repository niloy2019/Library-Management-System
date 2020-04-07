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
import javax.servlet.http.HttpSession;

@WebServlet("/ReturnBook")
public class ReturnBook extends HttpServlet {
	
	private Connection myCon=null;
	private PreparedStatement myStmt2=null;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		int bookID = Integer.parseInt(req.getParameter("id"));
		
		int userID = (int)session.getAttribute("id");
		
		//Method to increase book count in book databse while it returned
		updateBookCountInBookTable(bookID);
		
		//Method to Delete the Book from User when it is returned to Library
		removeBookFromUser(userID,bookID);
		
		HttpSession sesson = req.getSession();
		
		session.setAttribute("return", 1);
		resp.sendRedirect("MyAccount.jsp");
	}
	
	
	
	
	

	private void removeBookFromUser(int userID, int bookID) {

		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String pass = bundle.getString("password");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			myCon = DriverManager.getConnection(url, user, pass);
			
			String sql = "DELETE FROM `issue_details` WHERE `user_id` = ? && `book_isbn`=? ";
			
			myStmt2 = myCon.prepareStatement(sql);
			
			myStmt2.setInt(1, userID);
			myStmt2.setInt(2, bookID);
			
			myStmt2.executeUpdate();
			
			System.out.println("1 BOOK RETURNED TO LIBRARY");
			
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				    if(myCon!=null) {
						myCon.close();
					}
					
					if(myStmt2!=null) {
						myStmt2.close();
					}
					
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
	}

	
	
	
	
	
	
	public void updateBookCountInBookTable(int bookID) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String pass = bundle.getString("password");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			myCon = DriverManager.getConnection(url, user, pass);
			
			String sql = "UPDATE `book` SET `availiable_books` =`availiable_books`+ '1' WHERE (`isbn` = ?)";
			
			myStmt2 = myCon.prepareStatement(sql);
			
			myStmt2.setInt(1, bookID);
			
			myStmt2.executeUpdate();
			
			
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				    if(myCon!=null) {
						myCon.close();
					}
					
					if(myStmt2!=null) {
						myStmt2.close();
					}
					
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
