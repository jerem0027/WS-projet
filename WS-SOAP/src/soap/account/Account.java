package soap.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class Account {
	static private String baseURL = "http://localhost:8182";
	
	@SuppressWarnings("unused")
	private String connect(String name, String pwd) {
		String url = Account.baseURL + "/users/login";
		return this.userRequest(url, name, pwd); 
	}
	
	public String register(String name, String pwd) {
		String url = Account.baseURL + "/users/register";
		return this.userRequest(url, name, pwd);
	}
	
	public List<String> info(String name, String pwd) {
		String url = Account.baseURL + "/users/info";
		List<String> al = new ArrayList<String>();
		al = Arrays.asList(this.userRequest(url, name, pwd).split("\n"));
		return al;
	}
	
	private String userRequest(String url, String name, String pwd) {
		ClientResource ressource = new ClientResource(url);
		try {
			ressource.addQueryParameter("name", name);
			ressource.addQueryParameter("pwd", pwd);
			
			Representation response  = ressource.get();

			return response.getText();
		} catch (ResourceException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
