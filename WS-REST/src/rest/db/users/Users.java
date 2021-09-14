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

	@Get
	public String cancelTrain(int ticketId) {		
		String sql_select = "SELECT * FROM Ticket WHERE ticket_id = " + ticketId;

		System.out.println(sql_select);
		ResultSet rs = this.db.selectRows(sql_select);
		if(rs == null) {
			return "Cant find your ticket";
		}

		int trainID = -1;
		String type = "";
		try {
			while(rs.next()) {
				trainID = rs.getInt("train_id");
				type = rs.getString("type");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Cant find your ticket";
		}

		String sql_delete = "DELETE FROM Ticket WHERE ticket_id = " + ticketId;
		System.out.println(sql_delete);
		if(this.db.deleteQuery(sql_delete) != 1) {
			return "Cant delete your ticket";
		}

		String columnName = "nb_ticket_" + type.toLowerCase();
		String sql_update = "UPDATE Train SET " + columnName + " = " + columnName + " + 1 WHERE id = " + trainID;

		System.out.println(sql_update);
		return this.db.performUpdate(sql_update) == 1 ? "Ticket cancel successfully " : "Ticket remove error";

	}


}
