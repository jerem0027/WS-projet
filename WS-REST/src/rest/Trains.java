package rest;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.restlet.resource.Get;
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
	public String allFilter(String sql) {
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
	
	@Get
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
}