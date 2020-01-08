import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/world?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
        String username = "root";
        String password = "admin";
        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM city";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            System.out.println(id);
        }

        Statement updateStatement = connection.createStatement();
        int updatesQuantity =
            updateStatement.executeUpdate("INSERT INTO city(Name, CountryCode, District, Population) VALUES ('ABC', 'POL', 'Lodzkie', 123)");
        System.out.println(updatesQuantity);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE CountryCode = ?");
        preparedStatement.setString(1, "POL");
        ResultSet cities = preparedStatement.executeQuery();
        while(cities.next()) {
            System.out.println(cities.getString("Name"));
        }

        connection.close();
    }
}
