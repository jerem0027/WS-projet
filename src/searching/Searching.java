package searching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import db.interaction.SQLiteConnection;

public class Searching {

	private SQLiteConnection db;

	public List<String> allTrains() {
		// Create the client resource  
		ClientResource resource = new ClientResource("http://localhost:8182/trains/allTrains");
		List<String> al = new ArrayList<String>();
		try {
			String str[] = resource.get().getText().split("\n");
			al = Arrays.asList(str);
			return al;
		} catch (ResourceException e) {
			al.add(e.toString());
			return al;
		} catch (IOException e) {
			al.add(e.toString());
			return al;
		} 
	}
	
	public List<String> city(String departur, String arrival){
		// Create the client resource  
		ClientResource resource = new ClientResource("http://localhost:8182/trains/allTrains/{"+ departur.toLowerCase() +"}/{"+ arrival.toLowerCase() +"}");
		List<String> al = new ArrayList<String>();
		try {
			String str[] = resource.get().getText().split("\n");
			al = Arrays.asList(str);
			return al;
		} catch (ResourceException e) {
			al.add(e.toString());
			return al;
		} catch (IOException e) {
			al.add(e.toString());
			return al;
		} 
	}
}
