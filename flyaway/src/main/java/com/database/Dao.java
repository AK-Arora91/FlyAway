package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dao {

	public Connection con=null;
	public Statement st=null;
	
	public Dao() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/flyaway","root","ankit");
		st=con.createStatement();
	}

	public List<String[]> getAvailableFlights(String f, String t, String d) {
		
		List<String[]> flights=new ArrayList<>();
		String query="select * from flights where from='"+f+"' and to='"+t+"' and date='"+d+"'";
		try {
			ResultSet rs=st.executeQuery(query);
			
			if(rs.next()) {
				String[] flight=new String[3];
				flight[0]=rs.getString("name");
				flight[1]=rs.getString("time");
				flight[2]=rs.getString("price");
				flights.add(flight);
			}
			
			return flights;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public HashMap<String, String> checkUser(String email, String password) {
		
		HashMap<String,String> user=null;
		String query="select * from user where email='"+email+"' and password='"+password+"'";
		try {
			ResultSet rs=st.executeQuery(query);
			if(rs.next()) {
				user=new HashMap<>();
				user.put("name", rs.getString("name"));
				user.put("email",rs.getString("email"));
				user.put("phno",rs.getString("phno"));
				user.put("adno",rs.getString("adno"));
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	public boolean insertUser(HashMap<String, String> user) {

		String query="insert into user values('"+user.get("email")+"','"+user.get("password")+"','"+user.get("name")+"','"+user.get("phno")+"','"+user.get("adno")+"')";                   
		try {
			st.execute(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
