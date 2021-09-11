package soap.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


public class Booking {

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

	public List<String> date(String dateD, String dateA){
		// Create the client resource  
		ClientResource resource = new ClientResource("http://localhost:8182/trains/dateD/"+ dateD +"/dateA/"+ dateA);
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

	public List<String> city(String from, String to){
		// Create the client resource  
		ClientResource resource = new ClientResource("http://localhost:8182/trains/from/"+ from +"/to/"+ to);
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
