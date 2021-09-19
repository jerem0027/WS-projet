package soap.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {

    private String url;
    private static Connection conn;
    
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
	
    public ResultSet selectRows(String sql){   	
        try {
             Statement stmt  = this.conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) { 
        	e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet insertData(String sql) {
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
    
    public ResultSet performQuery(String sql) {
    	Statement stmt = null;
		try {
			System.out.println(sql);
			stmt = this.conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
}
