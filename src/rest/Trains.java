package rest;

import java.util.ArrayList;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import db.interaction.SQLiteConnection;

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
	public ArrayList<String> allTrains() {
		return db.selectList("SELECT * FROM train");
	}
}