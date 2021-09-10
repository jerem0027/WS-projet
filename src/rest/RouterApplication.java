package rest;
 
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import rest.db.users.Users;

import org.restlet.resource.Resource;

 
public class RouterApplication extends Application{
	
	Restlet allTrains = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	    	Trains t = new Trains();
	        // Print the user name of the requested orders
	        response.setEntity(t.allTrains(), MediaType.TEXT_PLAIN);
	    }
	};
	
	Restlet city = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	        // Print the user name of the requested orders
	    	Trains t = new Trains();
	    	String departur = (String) request.getAttributes().get("departure");
	    	String arrival = (String) request.getAttributes().get("arrival");
	        response.setEntity(t.checkCity(departur, arrival), MediaType.TEXT_PLAIN);
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
		
		router.attach("/trains/allTrains", allTrains);
		router.attach("/trains/from/{departure}/to/{arrival}", city);
		router.attach("/trains", Trains.class);
		router.attach("/users", Users.class);
		return router;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Create a new Restlet component and add a HTTP server connector to it  
		Component component = new Component();  
		component.getServers().add(Protocol.HTTP, 8182); 
 
		// Attach the application to the component and start it  
		component.getDefaultHost().attach(new RouterApplication());  
		component.start();  
	}	
}