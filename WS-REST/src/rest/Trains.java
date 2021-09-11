package rest;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import rest.db.SQLiteConnection;



public class Trains extends ServerResource {  
	private SQLiteConnection db;
	
	public Trains() {
		this.db = new SQLiteConnection();
	}
	
	@Get
	public String toString() {
		return "Test";
	}
	
	@Get
	public String allTrains() {
		return db.select("SELECT * FROM train");
	}
	
	@Get
	public String checkCity(String from, String to) {
    	String sql = "SELECT * FROM train WHERE departure_city = '" + from + "' AND arrival_city = '" + to+"'";
    	return db.select(sql);
	}
	
	@Get
	public String checkDate(String dateD, String dateA) {
    	String sql = "SELECT * FROM train WHERE departure_date LIKE '" + dateD + "%' AND arrival_date LIKE '" + dateA+"%'";
		System.out.println(sql);
    	return db.select(sql);
	}
	
	@Get
	public String checkSeat(String nbTicket, String classT) {
    	String sql = "SELECT * FROM train WHERE nb_ticket_"+ classT + ">= "+nbTicket;
		System.out.println(sql);
    	return db.select(sql);
	}
}