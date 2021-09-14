package soap.booking;

import java.rmi.RemoteException;
import soap.booking.BookingStub.All;

public class ClientBooking {

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
        book.all(s).get_return();
        display(book.all(s).get_return());
    }
    
    public void display(String[] tab) {
    	for (String e:tab)
			System.out.println(e);
    }

	public void runClient() throws RemoteException {
		System.out.println("Client -- Get all trains - URL: http://localhost:8080/WS-SOAP-Project/services/Booking/all\n");
		this.getAllTrains();
        System.out.println();
        System.out.println();
        
        System.out.println("Client -- Get trains by filters :");
        System.out.println("- ville depart = Paris");
        System.out.println("- ville arrivé = Lyon");
        System.out.println("URL: http://localhost:8080/WS-SOAP-Project/services/Booking/all?cityD=Paris&cityA=Lyon\n");
        this.getFromFilter(null, null, "Paris", "Lyon", null, null);
        System.out.println();
        System.out.println();
        
        System.out.println("Client -- Get trains by filters :");
        System.out.println("- ville depart = Paris");
        System.out.println("- ville arrivé = Lyon");
        System.out.println("- station depart = Gare Montparnasse");
        System.out.println("- date arrivé = 2023-06-04");
        System.out.println("URL: http://localhost:8080/WS-SOAP-Project/services/Booking/all?stationD=Gare%20Montparnasse&cityD=Paris&cityA=Lyon&dateD=2023-06-04\n");
        getFromFilter("Gare Montparnasse", null, "Paris", "Lyon", "2023-06-04", null);
        System.out.println();
        System.out.println();
        
        System.out.println("");
	}

}
