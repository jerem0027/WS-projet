package rest;

import java.util.ArrayList;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import rest.db.users.Users;


public class RouterApplication extends Application{
	private int currentUserID = -1 ;

	Restlet allFilter = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			// Print the user name of the requested orders
			String sql = "SELECT * FROM train";

			Form form = request.getResourceRef().getQueryAsForm();   
			Trains t = new Trains();

			String stationD = form.getFirstValue("stationD");
			String stationA = form.getFirstValue("stationA");
			String cityA = form.getFirstValue("cityA");
			String cityD = form.getFirstValue("cityD");
			String dateD = form.getFirstValue("dateD");
            String dateA = form.getFirstValue("dateA");

			ArrayList<String> array = new ArrayList<String>();
			String nul = null;
			if (stationD != nul) {
				array.add(" departure_station = '" + stationD + "' ");
			}

			if (stationA != nul)
				array.add(" arrival_station = '" + stationA + "' ");

			if (cityA != nul)
				array.add(" arrival_city = '" + cityA + "' ");

			if (cityD != nul)
				array.add(" departure_city = '" + cityD + "' ");

			if (dateD != nul)
				array.add(" departure_date LIKE '" + dateD + "%' ");
            if (dateA != nul)
				array.add(" arrival_date LIKE '" + dateD + "%' ");

			if (array.size() > 0) {
				sql += " WHERE ";
				for(String s:array)
					sql += s + "AND";
				sql = sql.substring(0, sql.length() - 3);
			}
			System.out.println(sql);
			response.setEntity(t.allFilter(sql), MediaType.TEXT_PLAIN);
		}
	};

	Restlet trainClass = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			Trains t = new Trains();
			response.setEntity(t.toString(), MediaType.TEXT_HTML);
		}
	};

	Restlet login = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			Users u = new Users();
			Form form = request.getResourceRef().getQueryAsForm();            

			String name = form.getFirstValue("name");
			String pwd = form.getFirstValue("pwd");

			setCurrentUserID(u.getUser(name, pwd));
			String msg = "";
			if(currentUserID == -1) {
				msg = "Wrong user or pwd";
			}else {
				msg = "Authenticated as " + name + ", user id is " + currentUserID;
			}            
			response.setEntity(msg, MediaType.TEXT_PLAIN);
		}
	};

	Restlet register = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			Users u = new Users();
			Form form = request.getResourceRef().getQueryAsForm();            

			String name = form.getFirstValue("name");
			String pwd = form.getFirstValue("pwd");

			setCurrentUserID(u.addUser(name, pwd));

			String msg = "";
			if(currentUserID == -1) {
				msg = "Cant create your accoun";
			}else {
				msg = "Account created for " + name + ", user id is " + currentUserID;
			}            
			response.setEntity(msg, MediaType.TEXT_PLAIN);
		}
	};

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new respective instance of resource.
		Router router = new Router(getContext());
		// Defines only two routes

		router.attach("/trains/all", allFilter);
		router.attach("/trains", trainClass);

		router.attach("/users", Users.class);
		router.attach("/users/login", login);
		router.attach("/users/register", register);
		return router;
	}

	private void setCurrentUserID(int id) {
		this.currentUserID = id;
		System.out.println(this.currentUserID + " - " + id);
	}

	public static void main(String[] args) throws Exception {
		// Create a new Restlet component and add a HTTP server connector to it  
		Component component = new Component();  
		component.getServers().add(Protocol.HTTP, 8182); 

		// Attach the application to the component and start it  
		component.getDefaultHost().attach(new RouterApplication());  
		component.start();
	}	
}