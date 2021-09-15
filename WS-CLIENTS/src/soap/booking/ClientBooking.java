package soap.booking;

import java.rmi.RemoteException;
import soap.booking.BookingStub.All;
import soap.booking.BookingStub.Booking;
import soap.booking.BookingStub.Unbook ;
import soap.account.ClientAccount;

public class ClientBooking {
	
	String name;
	String pwd;
	
	public ClientBooking(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}

    public void getAllTrains() throws RemoteException{
        BookingStub book = new BookingStub();
		All s = new All();
		display(book.all(s).get_return());
    }

    public void getFromFilter(String stationD, String stationA, String cityD, String cityA, String dateD, String dateA) throws RemoteException{
        BookingStub book = new BookingStub();
		All s = new All();
		s.setStationD(stationD);
		s.setStationA(stationA);
		s.setCityA(cityA);
		s.setCityD(cityD);
		s.setDateD(dateD);
        s.setDateA(dateA);
        display(book.all(s).get_return());
    }
    
    public void bookTrain(int id, String type, boolean flexible) throws RemoteException{
        BookingStub book = new BookingStub();
		Booking b = new Booking();
		b.setId(id);
		b.setType(type);
		b.setFlexible(flexible);
		b.setName(this.name);
		b.setPwd(this.pwd);
        System.out.println(book.booking(b).get_return());
    }
    
    public void removeTrain(int id) throws RemoteException{
        BookingStub book = new BookingStub();
		Unbook u = new Unbook();
		u.setName(this.name);
		u.setPwd(this.pwd);
		u.setTicketID(id);
        System.out.println(book.unbook(u).get_return());
    }
    
    
    public void display(String[] tab) {
    	for (String e:tab)
			System.out.println(e);
    }

	public void runClientGet() throws RemoteException {
		System.out.println("Client -- Get all trains - URL: http://localhost:8080/WS-SOAP-Project/services/Booking/all\n");
		this.getAllTrains();
        System.out.println();
        System.out.println();
        
        System.out.println("Client -- Get trains by filters :");
        System.out.println("- ville depart = Paris");
        System.out.println("- ville arrivé = Paris");
        System.out.println("URL: http://localhost:8080/WS-SOAP-Project/services/Booking/all?cityD=Paris&cityA=Paris\n");
        this.getFromFilter(null, null, "Paris", "Paris", null, null);
        System.out.println();
        System.out.println();
        
        System.out.println("Client -- Get trains by filters :");
        System.out.println("- ville depart = Paris");
        System.out.println("- ville arrivé = Paris");
        System.out.println("- station depart = Gare Montparnasse");
        System.out.println("URL: http://localhost:8080/WS-SOAP-Project/services/Booking/all?stationD=Gare%20Montparnasse&cityD=Paris&cityA=Paris\n");
        getFromFilter("Gare Montparnasse", null, "Paris", "Paris", null, null);
        System.out.println();
        System.out.println();
        
        System.out.println("");
	}
	
	public void runClientBooking() throws RemoteException {
		System.out.println("Client -- Book trains :");
        System.out.println("- ID train = 12");
        System.out.println("- type = First");
        System.out.println("- flexible = true");
        System.out.println("URL: http://localhost:8080/WS-SOAP-Project/services/Booking/booking?/booking?id=12&flexible=true&type=first&name="+this.name+"&pwd="+this.pwd+"\n");
        bookTrain(12, "First", true);
        bookTrain(13, "Business", true);
        bookTrain(17, "Standard", true);
        bookTrain(72, "First", true);
	}

}
