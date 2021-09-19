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

	@Get
	public String userTrains(int id) {	
		String[] columns = {
				"ticket_id", "type", "train_id", "departure_station", "departure_city", "departure_date",
				"arrival_station", "arrival_city", "arrival_date"
		};
		String sql = "SELECT * FROM Train JOIN Ticket on ticket.train_id = train.id "
				+ "WHERE Ticket.user_id = " + id;
		ResultSet rs = this.db.performQuery(sql);
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("My Trains\n");
			for(String col : columns)
				sb.append(col + " | ");
			sb.append('\n');
			while(rs.next()) {
				for(String col : columns)
					sb.append(rs.getString(col) + " | ");
				sb.append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sb.toString();

	}




}
