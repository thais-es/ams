package spa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connect {

	public static Connection seConnecter() {
		try {
			Class.forName("org.postgresql.Driver");
			String dataBase = "etd";
			String url = "jdbc:postgresql://pedago.univ-avignon.fr:5432/" + dataBase;
			Properties props = new Properties();
			props.setProperty("user", "uapv2501798");
			props.setProperty("password", "Tekno");
			Connection connection = DriverManager.getConnection(url, props);
			System.out.println("connection réussie");
			return connection;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static void main(String[] args) {
		seConnecter();
	}
}
