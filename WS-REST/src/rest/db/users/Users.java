package rest.db.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import rest.db.SQLiteConnection;

public class Users extends ServerResource{

	private SQLiteConnection db;
	public Users() {
		this.db = new SQLiteConnection();
	}
	public Users(SQLiteConnection db) {
		this.db = db;
	}

	@Get
	public int getUser(String username, String pwd) {
		String query = "SELECT * FROM User "
				+ "WHERE name = '" + username + "' "
				+ "AND password = '" + pwd + "'";

		ResultSet rs = db.performQuery(query);
		int userId = -1, nb = 0;
		try {
			while(rs.next()){
				nb += 1;
				userId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nb == 1 ? userId : -1;
	}

	
	@Get
	public int addUser(String username, String pwd) {
		String exist_query = "SELECT * FROM User WHERE name = '" + username + "'";
		ResultSet rs_exist = this.db.selectRows(exist_query);
		
		try {
			if(rs_exist.next())
				return -2;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String query = "INSERT INTO User (name, password) "
				+ "VALUES  ('" + username + "', '" + pwd + "')";
		ResultSet rs = db.insertData(query);
		int userId = -1;
		try {
			if(rs.next()){
				userId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;

	}





}
