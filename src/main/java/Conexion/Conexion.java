package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexion {

	 private static final String url_db = "jdbc:mysql://localhost:3306/tp_java";
	 private static final String username = "root";
	 private static final String password = "";
	 private Connection con;
	 
	 public Connection conectar() throws ClassNotFoundException {
		 
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 Connection con = DriverManager.getConnection(url_db,username,password);
			 System.out.println("OK");
		 }catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
		 
	 }
	public void close() throws SQLException {
		this.con.close();
		System.out.println("cerrado");
	}
}
