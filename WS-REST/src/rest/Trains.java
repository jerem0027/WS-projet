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
	public String checkCity(String departur, String arrival) {
    	String sql = "SELECT * FROM Train WHERE departure_station =" + departur.toLowerCase() + " arrival_city = " + arrival.toLowerCase();
    	String data = "SELECT * FROM train WHERE departure_city = '" + departur + "' AND arrival_city = '" + arrival+"'";
    	System.out.println(data);
    	return db.select(data);
	}
}