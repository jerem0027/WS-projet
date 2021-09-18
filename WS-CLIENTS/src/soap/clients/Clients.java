package soap.clients;

import java.rmi.RemoteException;

import soap.booking.ClientBooking;
import soap.account.ClientAccount;

public class Clients {
	
	public static void main(String[] args) throws RemoteException {
		String name = "testing";
		String pwd = "demo";
		ClientAccount account = new ClientAccount(name, pwd);
		account.register();
		account.getInfo();
		
		ClientBooking booking = new ClientBooking(name, pwd);
		booking.runClientGet();
		
		booking.runClientBooking();
		
		account.getInfo();
		
		booking.removeTrain(account.idBook);
		
		account.getInfo();
		booking.allerRetour("Paris", "Paris", "2021-11-28", "2023-02-28");
	}
}
