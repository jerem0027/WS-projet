package rest;
 
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.routing.Router;

import rest.db.SQLiteConnection;
import rest.db.users.Users;

 
public class RouterApplication extends Application{
	private SQLiteConnection db;
	private int currentUserID = -1 ;
	
	public RouterApplication(SQLiteConnection db) {
		this.db = db;
	}
	
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
	    	String from = (String) request.getAttributes().get("from");
	    	String to = (String) request.getAttributes().get("to");
	        response.setEntity(t.checkCity(from, to), MediaType.TEXT_PLAIN);
	    }
	};
	
	Restlet date = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	        // Print the user name of the requested orders
	    	Trains t = new Trains();
	    	String dateD = (String) request.getAttributes().get("dateD");
	    	String dateA = (String) request.getAttributes().get("dateA");
	        response.setEntity(t.checkDate(dateD, dateA), MediaType.TEXT_PLAIN);
	    }
	};
	
	Restlet seat = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	        // Print the user name of the requested orders
	    	Trains t = new Trains();
	    	String ndTicket = (String) request.getAttributes().get("ndTicket");
	    	String classT = (String) request.getAttributes().get("classT");
	        response.setEntity(t.checkSeat(ndTicket, classT), MediaType.TEXT_PLAIN);
	    }
	};
	
	Restlet station = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	        // Print the user name of the requested orders
	    	Trains t = new Trains();
	    	String fromStation = (String) request.getAttributes().get("fromStation");
	    	String toStation = (String) request.getAttributes().get("toStation");
	        response.setEntity(t.checkStation(fromStation, toStation), MediaType.TEXT_PLAIN);
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
	
	Restlet booking = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	    	Trains t = new Trains(getDB());
	    	Users u = new Users(getDB());
	    	
            int id = Integer.parseInt((String)request.getAttributes().get("id"));
            Form form = request.getResourceRef().getQueryAsForm();            
            
            String name = form.getFirstValue("name");
            String pwd = form.getFirstValue("pwd");
            int userID = u.getUser(name, pwd);
            if(userID == -1) {
            	response.setEntity("Wrong user or pwd", MediaType.TEXT_PLAIN);
            	return;
            }
            
            
            String type = form.getFirstValue("type");
            boolean flexible = form.getFirstValue("flexible") == "true";
            int places = t.availablePlace(id, type);
            if(places < 1) {
            	response.setEntity("false", MediaType.TEXT_PLAIN);
            	return;
            }
            if(t.bookTrain(id, type, flexible, userID))
            	response.setEntity("true", MediaType.TEXT_PLAIN);
            else
            	response.setEntity("false", MediaType.TEXT_PLAIN);
	    }
	};
	
	Restlet infos = new Restlet(getContext()) {
	    @Override
	    public void handle(Request request, Response response) {
	        // Print the user name of the requested orders
	    	Users u = new Users(getDB());
	    	
            Form form = request.getResourceRef().getQueryAsForm();            
            
            String name = form.getFirstValue("name");
            String pwd = form.getFirstValue("pwd");
            int userID = u.getUser(name, pwd);
            if(userID == -1) {
            	response.setEntity("Wrong user or pwd", MediaType.TEXT_PLAIN);
            	return;
            }  
            System.out.println(u.userTrains(userID));
	        response.setEntity(u.userTrains(userID), MediaType.TEXT_PLAIN);
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
		router.attach("/trains/from/{from}/to/{to}", city);
		router.attach("/trains/fromStation/{fromStation}/toStation/{toStation}", station);
		router.attach("/trains/dateD/{dateD}/dateA/{dateA}", date);
		router.attach("/trains/ndTicket/{ndTicket}/class/{classT}", seat);
		router.attach("/trains", trainClass);

		router.attach("/users/info", infos);
		router.attach("/users/login", login);
		router.attach("/users/register", register);
		
		router.attach("/booking/{id}", booking);
		
		
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