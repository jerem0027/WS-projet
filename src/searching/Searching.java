package searching;

import db.interaction.SQLiteConnection;

public class Searching {
	
	public String allTrains() {
		SQLiteConnection db = new SQLiteConnection();
		String sql = "SELECT * FROM train";
		
		return db.select(sql);
	}
	
	public String availableTrains(String departur, String arrival) {
		return "work in progress";
	}
	
	public String availableTrains(int dateDepartur, int DateArrival) {
		
		return "work in progress";
	}
	
	public String availableTrains(int nbTicket, String ticketClass) {
		
		return "work in progress";
	}
}
