package db.interaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class SQLiteConnection {
	
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/home/jeremie/eclipse-workspace/WS-projet/tea_ji_wi.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public String select(String sql){   
    	
    	String rtn = "";
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                rtn +=  rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("departure_station") + " | " +
                        rs.getString("departure_city") + " | " +
                        rs.getString("arrival_station") + " | " +
                        rs.getString("arrival_city") + " | " +
                        rs.getString("departure_date") + " | " +
                        rs.getString("arrival_date") + " | " +
                        rs.getString("nb_ticket_premier") + " | " +
                        rs.getString("nb_ticket_standard") + " | " +
                        rs.getString("nb_ticket_eco") + "\n";
            }
            return rtn;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
