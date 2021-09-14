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
		String query = "INSERT INTO User (name, password) "
				+ "VALUES  ('" + username + "', '" + pwd + "')";
		ResultSet rs = db.insertData(query);
		int userId = -1;
		try {
			System.out.println(rs.toString());
			if(rs.next()){

				System.out.println(rs.toString());
				userId = rs.getInt(1);
				System.out.println(userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
		
	}
	
	@Get
	public String userTrains(int id) {		
		String sql = "SELECT * FROM Train JOIN Ticket on ticket.train_id = train.id "
				+ "WHERE Ticket.user_id = " + id;
		System.out.println(sql);
		return this.db.select(sql);
		
	}
	
}
