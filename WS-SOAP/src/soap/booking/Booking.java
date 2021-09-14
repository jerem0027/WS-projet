package soap.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;


public class Booking {
	ClientResource resource = new ClientResource("http://localhost:8182/trains/all");

	public List<String> all(String stationD, String stationA, String cityA, String cityD, String dateD, String dateA) {
		resource.addQueryParameter("stationD", stationD);
		resource.addQueryParameter("stationA", stationA);
		resource.addQueryParameter("cityA", cityA);
		resource.addQueryParameter("cityD", cityD);
		resource.addQueryParameter("dateD", dateD);
        resource.addQueryParameter("dateA", dateA);
		List<String> al = new ArrayList<String>();
		try {
			if(resource.get().getSize() == 0) {
				al.add("We are sorry, but there is no trains with the selected filters");
				return al;
			}
			al = Arrays.asList(resource.get().getText().split("\n"));
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
