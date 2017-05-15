package com.sh.bookshop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao implements AutoCloseable {
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

	public boolean validate(String username, String password) throws Exception {
		ResultSet rs = null;
		String sql = "SELECT USERNAME, PASSWORD FROM LOGIN WHERE USERNAME=? AND PASSWORD=?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next())
				return true;
		} finally {
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
		}
		return false;
	}
}
