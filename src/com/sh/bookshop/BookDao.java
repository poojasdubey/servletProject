package com.sh.bookshop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class BookDao implements AutoCloseable {
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	public static final String DB_USER = "nilesh";
	public static final String DB_PASS = "nilesh";

	static {
		try {
			Class.forName(DB_DRIVER);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Connection con;
	private PreparedStatement stmt;

	public void open() throws Exception {
		con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}
	public void close() throws Exception {
		if(con!=null)
			con.close();
	}
	public List<String> getSubjects() throws Exception {
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String sql = "SELECT DISTINCT SUBJECT FROM BOOKS";
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String subject = rs.getString("SUBJECT");
				list.add(subject);
			} 
		} finally {
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
		}
		return list;
	}
	
	public List<Book> getBooksOfSubject(String subject) throws Exception {
		List<Book> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			String sql = "SELECT ID,NAME,AUTHOR,SUBJECT,PRICE FROM BOOKS WHERE SUBJECT=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, subject);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Book b = fromResultSet(rs);
				list.add(b);
			}
		}finally{
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
		}
		return list;
	}
	public Book getBook(int bookid) throws Exception {
		Book b = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT ID,NAME,AUTHOR,SUBJECT,PRICE FROM BOOKS WHERE ID=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, bookid);
			rs = stmt.executeQuery();
			if(rs.next()) 
				b = fromResultSet(rs);
		}finally{
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
		}
		return b;
	}
	public static Book fromResultSet(ResultSet rs) throws Exception {
		Book b = new Book();
		b.setId(rs.getInt("ID"));
		b.setName(rs.getString("NAME"));
		b.setAuthor(rs.getString("AUTHOR"));
		b.setSubject(rs.getString("SUBJECT"));
		b.setPrice(rs.getDouble("PRICE"));
		return b;
	}
}

