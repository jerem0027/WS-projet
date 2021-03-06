package rest;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import rest.db.SQLiteConnection;



public class Trains extends ServerResource {  
	private SQLiteConnection db;

	public Trains() {
		this.db = new SQLiteConnection();
	}	
	public Trains(SQLiteConnection db) {
		this.db = db;
	}

	@Get
	public String toString() {
		String a = "<a href='http://localhost:8182";
		String b = "'>";
		String c = "</a>";
		String br = "<br>";
		String list = a + "/trains/all" + b + "/trains/all?stationD=Gare%20du%20Nord&stationA=Gare%20de%20Lyon&cityA=Paris&cityD=Paris&dateD=2022-01-21&dateA=2022-01-21"+c+br;
		return list;
	}

	@Get
	public String selectAll() {
		return db.selectAll();
	}

	@Get
	public String selectSQL(String sql) {
		return db.select(sql);
	}

	@Get
	public int availablePlace(int id, String type) {
		String sql = "SELECT * FROM train WHERE id = " + id;

		ResultSet rows = db.selectRows(sql);
		String columnName = "nb_ticket_" + type;
		columnName = columnName.toLowerCase();

		try {
			if(rows.next()) {
				return rows.getInt(columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Post
	public boolean bookTrain(int trainId, String type, boolean flexible, int userID) {
		String columnName = "nb_ticket_" + type;
		String sql_update = "UPDATE Train SET " + columnName + " = " + columnName + " - 1 WHERE id = " + trainId;
		String sql_insert = "INSERT INTO Ticket (user_id, train_id, flexible, type)"
				+ "VALUES (" + userID + "," + trainId + ", " + (flexible ? 1 : 0) + ", '" + type.toUpperCase() + "')";

		boolean a = this.db.performUpdate(sql_update) == 1;
		boolean b = this.db.insertData(sql_insert) != null;
		System.out.println(a + " -- " + b);

		return a && b;
	}
	
	@Post
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