package soap.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Booking {


	public List<String> all(String stationD, String stationA, String cityA, String cityD, String dateD, String dateA) {
		ClientResource resource = new ClientResource("http://localhost:8182/trains/all");
		JSONObject json;
		try {
			json = new JSONObject(resource.get().getText());
			JSONArray array = json.getJSONArray("trains");
			JSONArray rtn = new JSONArray();
			String nul = null;

			for (int i=0; i<array.length(); i++) {
				JSONObject item = array.getJSONObject(i);

				if (stationD != nul)
					if (!item.getString("departure_station").contentEquals(stationD))
						continue;

				if (stationA != nul)
					if (!item.getString("arrival_station").contentEquals(stationA))
						continue;

				if (cityA != nul)
					if (!item.getString("arrival_city").contentEquals(cityA))
						continue;

				if (cityD != nul)
					if (!item.getString("departure_city").contentEquals(cityD))
						continue;


				if (dateD != nul)
					if (!item.getString("departure_date").contains(dateD))
						continue;

				if (dateA != nul)
					if (!item.getString("arrival_date").contains(dateA))	
						continue;

				rtn.put(item);
			}
			return jsonToList(rtn, json.getString("summary"));
		} catch (JSONException | ResourceException | IOException e) {
			List<String> lst = new ArrayList<String>();
			lst.add(e.toString());
			return lst;
		}		
	}

	private static List<String> jsonToList(JSONArray json, String sommary){
		List<String> lst = new ArrayList<String>();
		if (json.length() < 1) {
			lst.add("We are sorry, but there is no trains with the selected filters");
			return lst;
		}
		lst.add(sommary);
		for (int i=0; i<json.length(); i++) {
			JSONObject item = json.getJSONObject(i);
			lst.add(
				item.getInt("id") + " | " +
				item.getString("name")+ " | " +
				item.getString("departure_station")+ " | " +
				item.getString("departure_city")+ " | " +
				item.getString("arrival_station")+ " | " +
				item.getString("arrival_city")+ " | " +
				item.getString("departure_date")+ " | " +
				item.getString("arrival_date")+ " | " +
				item.getString("nb_ticket_first")+ " | " +
				item.getString("nb_ticket_business")+ " | " +
				item.getString("nb_ticket_standard")+"\n"
			);
		}
		return lst;
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
		} catch (ResourceException | IOException  e) {
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
