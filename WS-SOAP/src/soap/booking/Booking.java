package soap.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


public class Booking {
	

	public List<String> all(String stationD, String stationA, String cityA, String cityD, String dateD, String dateA) {
		ClientResource resource = new ClientResource("http://localhost:8182/trains/all");
		resource.addQueryParameter("stationD", stationD);
		resource.addQueryParameter("stationA", stationA);
		resource.addQueryParameter("cityA", cityA);
		resource.addQueryParameter("cityD", cityD);
		resource.addQueryParameter("dateD", dateD);
        resource.addQueryParameter("dateA", dateA);
		List<String> al = new ArrayList<String>();
		try {
			al = Arrays.asList(resource.get().getText().split("\n"));
			if(al.size() == 1) {
				al = new ArrayList<String>();
				al.add("We are sorry, but there is no trains with the selected filters");
				return al;
			}
			
			return al;
		} catch (ResourceException e) {
			al.add(e.toString());
			return al;
		} catch (IOException e) {
			al.add(e.toString());
			return al;
		}
	}

    public List<String> allerRetour(String cityD, String cityA, String dateD, String dateR) {
    	ClientResource resource = new ClientResource("http://localhost:8182/trains/allerRetour/"+cityD+"/"+cityA+"/"+dateD+"/"+dateR);
		List<String> al = new ArrayList<String>();
		try {
			al = Arrays.asList(resource.get().getText().split("\n"));
			if(al.size() == 1) {
				al = new ArrayList<String>();
				al.add("We are sorry, but there is no trains with the selected filters");
				return al;
			}
			return al;
		} catch (ResourceException e) {
			al.add(e.toString());
			return al;
		} catch (IOException e) {
			al.add(e.toString());
			return al;
		}
	}

	public String booking(int id, String type, boolean flexible, String name, String pwd){		
		ClientResource resource = new ClientResource("http://localhost:8182/booking/");
	
		Form form = new Form();
		form.add("id", Integer.toString(id));
		form.add("type", type);
		form.add("flexible", Boolean.toString(flexible));
		form.add("userId", Integer.toString(1));
		
		
		String response = "";
		try {
			response = resource.post(form).getText();
		} catch (ResourceException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
		return response.equals("true") ? "Successful reservation" : response.equals("false") ? "Reservation error or the train " + id + " is not available" : response; 
	}
	
	public String unbook(int ticketID,  String name, String pwd){
		ClientResource resource = new ClientResource("http://localhost:8182/booking/cancel/");	
		
		Form form = new Form();
		form.add("ticketId", Integer.toString(ticketID));

		try {
			return resource.post(form).getText();
		} catch (ResourceException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
