package com.abc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/IssueBooks")
public class IssueBooks extends HttpServlet {
	

	
	
	private Connection myCon=null;
	private ResultSet resultSet=null;
	private Statement myStmt=null;
	private PreparedStatement myStmt2=null;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		PrintWriter out = resp.getWriter();
		
		int id = Integer.parseInt(req.getParameter("id"));
		System.out.println("ISSUE BOOKS"+id);
		HttpSession session = req.getSession();
		int userId = (int)session.getAttribute("id");

		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String password = bundle.getString("password");
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			myCon = DriverManager.getConnection(url, user, password);
			
			myStmt = myCon.createStatement();
			
			//Selecting all he details of book which User want to Issue
			
			resultSet = myStmt.executeQuery("select * from `book` where `isbn`= "+id);
			
			resultSet.next();
				
			int isbn = resultSet.getInt(1);
			String book = resultSet.getString(2);
			String author = resultSet.getString(3);
			String rating = resultSet.getString(5);
			System.out.println(isbn+" "+book+" "+author+" "+rating);
			

//			If Book is not Availiable in Library,User cannot issue the Books
			
			if(BookIsNotAvailiable(id)) {
				session.setAttribute("msg", 3);
				resp.sendRedirect("Booking_details.jsp");
			}
			
			//If User is already Having that Book , Then He cannot issue that Book Again
			
			else if(checkIfBookExist(id,userId) ) {
				session.setAttribute("msg", 1);
				resp.sendRedirect("Booking_details.jsp");
				
			}
			
			//If User Has Already Issued More than 5 Books , Then He is not allowedto issue any book
			
			else if(checkCountOfBook(id,userId)) {
				
				session.setAttribute("msg", 2);
				resp.sendRedirect("Booking_details.jsp");
				
			}
			
			//If Above Two Conditions are false, Then user is Allowed to Issue the BOOK
			
			else {
				session.setAttribute("msg", 0);
				issueBook(isbn,book,author,rating,userId);
				
				resp.sendRedirect("Booking_details.jsp");
			}
			
			System.out.println("ISSUE BOOKS2");
			
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
	
//Method to Check If Book is Present in Library or Not
	
private boolean BookIsNotAvailiable(int id) {
		

	int count=0;;
	try {
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String password = bundle.getString("password");
		
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		myCon = DriverManager.getConnection(url, user, password);
		
		myStmt = myCon.createStatement();
		
		resultSet = myStmt.executeQuery("select `availiable_books` from `book` where `isbn`= "+id);
		
		resultSet.next();

	    int availiableBooks = resultSet.getInt(1);
			
		System.out.println("Availiable Books in Library : "+availiableBooks);
			
			if(availiableBooks<=0) {
				count++;
				System.out.println("THIS BOOK IS CURRENTLY NOT AVAILIABLE IN LIBARARY");
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
	
	if(count==0) {
		return false;
	}else {
		return true;
	}
	
	
	}


//	Method to check how many books user have,User cannot issue more than 5 Book from Library

	private boolean checkCountOfBook(int id, int userId) {
		
		int count=0;;
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
			String url = bundle.getString("url");
			String user = bundle.getString("user");
			String password = bundle.getString("password");
			
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			myCon = DriverManager.getConnection(url, user, password);
			
			myStmt = myCon.createStatement();
			
			resultSet = myStmt.executeQuery("select count(*) from `issue_details` where `user_id`= "+userId);
			
			resultSet.next();

		    int noOfBooks = resultSet.getInt(1);
				
			System.out.println("Number of Books User have : "+noOfBooks);
				
				if(noOfBooks>=5) {
					count++;
					System.out.println("Number of Book more than 5");
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
		
		if(count==0) {
			return false;
		}else {
			return true;
		}
		

	}
	
	
	
	
	
	
	
//	Method to Check if the book is already exist to User,which book he i trying to Issue

	private boolean checkIfBookExist(int id, int userId) {
		
		int count=0;;
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
			String url = bundle.getString("url");
			String user = bundle.getString("user");
			String password = bundle.getString("password");
			
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			myCon = DriverManager.getConnection(url, user, password);
			
			
			myStmt = myCon.createStatement();
			
			resultSet = myStmt.executeQuery("select `book_isbn` from `issue_details` where `user_id`= "+userId);
			
			while(resultSet.next()) {

				int isbn = resultSet.getInt(1);
				
				System.out.println(isbn);
				
				if(isbn==id) {
					count++;
					System.out.println("BOOK ALREADY EXISTS");
				}
			}
			System.out.println("ISSUE BOOKS2");
			
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
		
		
		
		
		
		if(count==0) {
			return false;
		}else {
			return true;
		}
		
	}

	
	
	
	//Method to Issue a Book from Library
	
	private void issueBook(int isbn, String book, String author, String rating, int userId) {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//Getting current date
		Calendar cal = Calendar.getInstance();
		//Displaying current date in the desired format
		String issue_date = sdf.format(cal.getTime());
		System.out.println("Current Date: "+sdf.format(cal.getTime()));
		   
		//Number of Days to add
	        cal.add(Calendar.DAY_OF_MONTH, 7);  
		//Date after adding the days to the current date
		String returnDate = sdf.format(cal.getTime());  
		//Displaying the new Date after addition of Days to current date
		System.out.println("Date after Addition: "+returnDate);
		
		
		updateBookCount(isbn);
		
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String pass = bundle.getString("password");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			myCon = DriverManager.getConnection(url, user, pass);
			
			String sql = "insert into issue_details (`user_id`,`book_isbn`,`book_name`,`author`,`rating`,`issue_date`,`return_date`) values(?,?,?,?,?,?,?)";
			
			myStmt2 = myCon.prepareStatement(sql);
			
			myStmt2.setInt(1, userId);
			myStmt2.setInt(2, isbn);
			myStmt2.setString(3, book);
			myStmt2.setString(4, author);
			myStmt2.setString(5, rating);
			myStmt2.setString(6, issue_date);
			myStmt2.setString(7, returnDate);
			
			int i = myStmt2.executeUpdate();
			
			System.out.println("INSERTION SUCCESSSFULL");
			
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
		
		
		
	}


	
	
//	Method to update the count of books in Book Table when One Book is Issued
	
	private void updateBookCount(int isbn) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.utilities.mysqlinfo");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String pass = bundle.getString("password");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			myCon = DriverManager.getConnection(url, user, pass);
			
			String sql = "UPDATE `book` SET `availiable_books` =`availiable_books`- '1' WHERE (`isbn` = ?)";
			
			myStmt2 = myCon.prepareStatement(sql);
			
			myStmt2.setInt(1, isbn);
			
			myStmt2.executeUpdate();
			
			System.out.println("COUNT OF BOOKS IN BOOK DATABASE DECREASED BY 1");
			
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
		
		
	}

}
