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
        String a = "<a href='http://localhost:8182";
        String b = "'>";
        String c = "</a>";
        String br = "<br>";
		String list = a + "/trains/all" + b + "/trains/all"+c+br;
		list += a+ "/trains/from/{from}/to/{to}" + b +"/trains/from/{from}/to/{to}"+c+br;
		list += a+ "/trains/fromStation/{fromStation}/toStation/{toStation}" + b +"/trains/fromStation/{fromStation}/toStation/{toStation}"+c+br;
		list += a+ "/trains/dateD/{dateD}/dateA/{dateA}" + b +"/trains/dateD/{dateD}/dateA/{dateA}"+c+br;
		list += a+ "/trains/ndTicket/{ndTicket}/class/{classT}" + b +"/trains/ndTicket/{ndTicket}/class/{classT}"+c+br;
		return list;
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
    	return db.select(sql);
	}
	
	@Get
	public String checkSeat(String nbTicket, String classT) {
    	String sql = "SELECT * FROM train WHERE nb_ticket_"+ classT + ">= "+nbTicket;
    	return db.select(sql);
	}

	@Get
	public String checkStation(String fromStation, String toStation) {
    	String sql = "SELECT * FROM train WHERE departure_station = '" + fromStation + "' AND arrival_station = '" + toStation +"'";
		return db.select(sql);
	}
}