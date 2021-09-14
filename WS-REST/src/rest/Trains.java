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
		String list = a + "/trains/all" + b + "/trains/all?stationD=Gare%20du%20Nord&stationA=Gare%20de%20Lyon&cityA=Paris&cityD=Paris&dateD=2022-01-21&dateA=2022-01-21"+c+br;
        return list;
	}
	@Get
	public String allFilter(String sql) {
    	return db.select(sql);
	}
}