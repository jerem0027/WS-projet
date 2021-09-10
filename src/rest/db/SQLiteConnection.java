package rest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;



public class SQLiteConnection {
	
    private String url;
	
    private Connection connect() {
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	this.url = "jdbc:sqlite:" + System.getenv("DB_PATH");
    	System.out.println(this.url);
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public ArrayList<String> selectList(String sql) {
    	ArrayList<String> rtn = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                rtn.add(rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("departure_station") + " | " +
                        rs.getString("departure_city") + " | " +
                        rs.getString("arrival_station") + " | " +
                        rs.getString("arrival_city") + " | " +
                        rs.getString("departure_date") + " | " +
                        rs.getString("arrival_date") + " | " +
                        rs.getString("nb_ticket_first") + " | " +
                        rs.getString("nb_ticket_business") + " | " +
                        rs.getString("nb_ticket_standard"));
            }
            return rtn;
        } catch (SQLException e) {
        	rtn.add(e.getMessage());
            return rtn;
        }
    }
    
    public String select(String sql){   	
    	String rtn = "";
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
                        rs.getString("nb_ticket_first") + " | " +
                        rs.getString("nb_ticket_business") + " | " +
                        rs.getString("nb_ticket_standard") + "\n";
            }
            return rtn;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    
    public ResultSet performQuery(String sql) {
    	Connection conn = this.connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
}
