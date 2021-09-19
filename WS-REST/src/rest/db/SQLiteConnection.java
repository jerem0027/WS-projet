package rest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

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

	public String selectAll() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try (Connection conn = this.connect();
				Statement stmt  = conn.createStatement();
				ResultSet rs    = stmt.executeQuery("SELECT * FROM train")){

			json.put("summary", "id | name | departure_station | departure_city | arrival_station | arrival_city | departure_date | arrival_date | nb_ticket_first | nb_ticket_business | nb_ticket_standard");
			while (rs.next()) {
				JSONObject item = new JSONObject();
				item.put("id", rs.getInt("id"));
				item.put("name", rs.getString("name"));
				item.put("departure_station", rs.getString("departure_station"));
				item.put("departure_city", rs.getString("departure_city"));
				item.put("arrival_station", rs.getString("arrival_station"));
				item.put("arrival_city", rs.getString("arrival_city"));
				item.put("departure_date", rs.getString("departure_date"));
				item.put("arrival_date", rs.getString("arrival_date"));
				item.put("nb_ticket_first", rs.getString("nb_ticket_first"));
				item.put("nb_ticket_business", rs.getString("nb_ticket_business"));
				item.put("nb_ticket_standard", rs.getString("nb_ticket_standard"));
				array.put(item);
			}
			json.put("trains", array);           
			return json.toString();
		} catch (SQLException e) {
			return e.getMessage();
		}
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
