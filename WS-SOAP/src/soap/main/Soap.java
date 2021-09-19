package soap.main;
import java.sql.ResultSet;
import java.sql.SQLException;

import soap.db.SQLiteConnection;

public class Soap {
	protected static String baseURL = "http://localhost:8182";
	protected static SQLiteConnection db = new SQLiteConnection();
	
	protected int getUser(String username, String pwd) {
		String query = "SELECT * FROM User "
				+ "WHERE name = '" + username + "' "
				+ "AND password = '" + pwd + "'";

		ResultSet rs = db.performQuery(query);
		int userId = -1, nb = 0;
		try {
			while(rs.next()){
				nb += 1;
				userId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nb == 1 ? userId : -1;
	}
}
