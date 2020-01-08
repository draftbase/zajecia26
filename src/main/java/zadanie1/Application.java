package zadanie1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Application {

    private Connection connection;
    private Scanner scanner;

    public void run() throws ClassNotFoundException, SQLException {
        initialize();

        while (true) {
            System.out.println("1. Miasta Polski");
            System.out.println("2. Miasta dla kodu kraju");
            System.out.println("0. Koniec");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    getCities("POL");
                    break;
                case "2":
                    System.out.println("Wpisz kod kraju");
                    String countryCode = scanner.nextLine();
                    getCities(countryCode);
                    break;
                case "0":
                    close();
                    return;
                default:
                    System.out.println("Nie mam takiej opcji");
            }
            System.out.println();
        }
    }

    private void initialize() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/world?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
        String username = "root";
        String password = "admin";
        this.connection = DriverManager.getConnection(url, username, password);
        this.scanner = new Scanner(System.in);
    }

    private void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Błąd podczas zamykania połączenia. " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getCities(String code) throws SQLException {
        String query = "SELECT * FROM city WHERE CountryCode = '" + code + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String cityName = resultSet.getString("Name");
            System.out.println(cityName);
        }
    }
}
