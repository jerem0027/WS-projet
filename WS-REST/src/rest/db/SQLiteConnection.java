package rest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteConnection {
	
    private String url;
    private Connection conn;
    
    public SQLiteConnection() {
    	try {
			Class.forName("org.sqlite.JDBC");
		} 
    	catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    	this.url = "jdbc:sqlite:" + System.getenv("DB_PATH");
    	System.out.println(this.url);
    	
        // SQLite connection string
        Connection conn = null;
        try {
            this.conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
    private Connection connect() {
    	if(this.conn != null) {
    		return this.conn;
    	}
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
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
            
            rtn.add("id | name | departure_station | departure_city | arrival_station | arrival_city | departure_date | arrival_date | nb_ticket_first | nb_ticket_business | nb_ticket_standard\n");
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
    
    public String insert(String sql) {
    	return "TODO";
    }
    
    public String select(String sql){   	
    	String rtn = "";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            rtn += "id | name | departure_station | departure_city | arrival_station | arrival_city | departure_date | arrival_date | nb_ticket_first | nb_ticket_business | nb_ticket_standard\n";

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
    
    public ResultSet selectRows(String sql){   	
        try {
        	Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) { 
        	e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet performQuery(String sql) {
    	Connection conn = this.connect();
    	Statement stmt = null;
		try {
			System.out.println(sql);
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public int performUpdate(String sql) {
    	Connection conn = this.connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
    }
    
    public ResultSet insertData(String sql) {
    	Connection conn = this.connect();
    	PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.execute();
			return stmt.getGeneratedKeys();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    public boolean insertOnly(String sql) {
    	Connection conn = this.connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.execute(sql);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    
    public int deleteQuery(String sql) {
    	Connection conn = this.connect();
    	Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.executeUpdate(sql);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
    }
    
}
