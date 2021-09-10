package searching;

import java.util.ArrayList;

import db.interaction.SQLiteConnection;

public class Searching {
	
	private SQLiteConnection db;
	
	public Searching() {
		this.db = new SQLiteConnection();
	}
	
	public ArrayList<String> allTrains() {
		return db.selectList("SELECT * FROM train");
	}
	
	public String availableTrains(String departur, String arrival) {
		return db.select("SELECT * FROM train WHERE departure_station =" + departur.toLowerCase() + " arrival_city = " + arrival.toLowerCase());
	}
	
	public String availableTrains(int dateDepartur, int DateArrival) {
		/* TOFIX */
		return db.select("SELECT * FROM train WHERE departure_date =" + dateDepartur + " arrival_date = " + DateArrival);
	}
	
	public String availableTrains(int nbTicket, String ticketClass) {
		/* class: First, Business or Standard */
		return db.select("SELECT * FROM train WHERE nb_ticket_"+ ticketClass.toLowerCase() +">=" + nbTicket);
	}
}
