package soap.clients;

import java.rmi.RemoteException;

import soap.booking.ClientBooking;
import soap.account.ClientAccount;

public class Clients {
	
	public static void main(String[] args) throws RemoteException {
		ClientAccount account = new ClientAccount("franc", "demo");
		account.runClient();
		
		ClientBooking booking = new ClientBooking();
		booking.runClient();
	}
}
