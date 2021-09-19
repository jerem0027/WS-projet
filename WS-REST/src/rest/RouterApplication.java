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

import rest.db.SQLiteConnection;


public class RouterApplication extends Application{
	private SQLiteConnection db;
	private int currentUserID = -1 ;


	public RouterApplication(SQLiteConnection db) {
		this.db = db;
	}

	Restlet allFilter = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			// Print the user name of the requested orders
			Trains t = new Trains();
			response.setEntity(t.selectAll(), MediaType.TEXT_PLAIN);

		}
	};

	Restlet allerRetour = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			// Print the user name of the requested orders

			String res = "\nTrains Aller\n\n";
			Trains t = new Trains();
			Trains t2 = new Trains();

			String aller = (String) request.getAttributes().get("aller");
			String retour = (String) request.getAttributes().get("retour");
			String dateD = (String) request.getAttributes().get("dateD");
			String dateR = (String) request.getAttributes().get("dateR");

			String sql = "SELECT * FROM train WHERE departure_city = '"+aller+"' AND arrival_city = '"+retour+"' AND departure_date LIKE '" + dateD + "%'";
			res += t.selectSQL(sql);
			System.out.println(sql);
			res += "\nTrains Retour\n\n";
			sql = "SELECT * FROM train WHERE departure_city = '"+retour+"' AND arrival_city = '"+aller+"' AND departure_date LIKE '" + dateR + "%'";
			res += t2.selectSQL(sql);
			System.out.println(sql);
			response.setEntity(res, MediaType.TEXT_PLAIN);
		}
	};

	Restlet trainClass = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			Trains t = new Trains();
			response.setEntity(t.toString(), MediaType.TEXT_HTML);
		}
	};

	Restlet booking = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			Trains t = new Trains(getDB());

			Form form = new Form(request.getEntity());;            
			System.out.println(form.toString());

			int id = Integer.parseInt(form.getFirstValue("id"));  
			String type = form.getFirstValue("type");  
			boolean flexible = form.getFirstValue("flexible") == "true";  
			int userId = Integer.parseInt(form.getFirstValue("userId"));  


			int places = t.availablePlace(id, type);
			if(places < 1) {
				response.setEntity("false", MediaType.TEXT_PLAIN);
				return;
			}
			if(t.bookTrain(id, type, flexible, userId))
				response.setEntity("true", MediaType.TEXT_PLAIN);
			else
				response.setEntity("false", MediaType.TEXT_PLAIN);
		}
	};

	Restlet infos = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	        // Print the user name of the requested orders
	    	Trains t = new Trains(getDB());
	    	int uid = Integer.parseInt((String) request.getAttributes().get("id"));
	        response.setEntity(t.userTrains(uid), MediaType.TEXT_PLAIN);
	    }
	};

	Restlet cancel = new Restlet(getContext()) {
		@Override
		public void handle(Request request, Response response) {
			Trains t = new Trains(getDB());

			Form form = new Form(request.getEntity());;            
			System.out.println(form.toString());


			int ticketID = Integer.parseInt(form.getFirstValue("ticketId"));

			response.setEntity(t.cancelTrain(ticketID), MediaType.TEXT_PLAIN);
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
		router.attach("/trains/allerRetour/{aller}/{retour}/{dateD}/{dateR}", allerRetour);

		router.attach("/users/info/{id}", infos);

		router.attach("/booking/", booking);
		router.attach("/booking/cancel/", cancel);

		return router;
	}

	private void setCurrentUserID(int id) {
		this.currentUserID = id;
		System.out.println(this.currentUserID + " - " + id);
	}

	private SQLiteConnection getDB() {
		return this.db;
	}

	public static void main(String[] args) throws Exception {
		// Create a new Restlet component and add a HTTP server connector to it  
		Component component = new Component();  
		component.getServers().add(Protocol.HTTP, 8182); 

		// Attach the application to the component and start it  
		SQLiteConnection db = new SQLiteConnection();
		component.getDefaultHost().attach(new RouterApplication(db));  
		component.start();
	}	
}