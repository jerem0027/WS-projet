package rest.db.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import rest.db.SQLiteConnection;

public class Users extends ServerResource{

	private SQLiteConnection db;
	public Users() {
		this.db = new SQLiteConnection();
	}
	
	@Get
	public boolean isValidUser(String username, String pwd) {
		
		System.out.println("HELLO BOYS");
		System.out.println(username + " - " + pwd);
		String query = "SELECT COUNT(*) FROM User "
				+ "WHERE name = " + username + " "
				+ "AND password = " + pwd;
		
		ResultSet rs = db.performQuery(query);
		try {
			int x = rs.getInt(1);
			System.out.println("Size= " + x);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int x = 5;
		return false;
	}
}
