package soap.account;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import soap.main.Soap;

public class Account extends Soap {

	public String register(String name, String pwd) {		
		int userId = this.addUser(name, pwd);
		switch (userId) {
		case -1:
			return "Cant create your account";
		case -2:
			return "Username already exist";
		default:
			return "Account created for " + name + ", user id is " + userId;
		}
	}

	public List<String> info(String name, String pwd) {
		String url = Soap.baseURL + "/users/info/";
		List<String> al = new ArrayList<String>();

		int id = this.getUser(name, pwd);
		if(id == -1) {
			al.add("Wrong user or password");
			return al;
		}
		url += id;

		al = Arrays.asList(this.userRequest(url).split("\n"));
		return al;
	}

	private String userRequest(String url) {
		ClientResource ressource = new ClientResource(url);
		try {			
			Representation response  = ressource.get();

			return response.getText();
		} catch (ResourceException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}


	private int addUser(String name, String pwd) {
		String exist_query = "SELECT * FROM User WHERE name = '" + name + "'";
		ResultSet rs_exist = Soap.db.selectRows(exist_query);
		try {
			if(rs_exist.next())
				return -2;
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
			return -2;
		}

		String query = 
				"INSERT INTO User (name, password) "
						+ "VALUES  ('" + name + "', '" + pwd + "')";
		ResultSet rs = db.insertData(query);
		int userId = -1;
		try {
			if(rs.next())
				userId = rs.getInt(1);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;

	}
}
