package zadanie2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    private Scanner scanner;
    private Connection connection;

    public void run() throws SQLException, ClassNotFoundException {
        initialize();
        System.out.println("Podaj nazwę miasta");
        String cityName = scanner.nextLine();

        System.out.println("Podaj nową liczbę ludności");
        int population = scanner.nextInt();
        scanner.nextLine();

        PreparedStatement updateStatement = connection.prepareStatement("UPDATE city SET Population = ? WHERE Name = ?");
        updateStatement.setInt(1, population);
        updateStatement.setString(2, cityName);
        int updateQuantity = updateStatement.executeUpdate();

        PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM city WHERE Name = ?");
        queryStatement.setString(1, cityName);
        ResultSet results = queryStatement.executeQuery();
        if (results.next()) {
            String name = results.getString("Name");
            int resultPopulation = results.getInt("Population");
            System.out.println("Name: " + name + " population: " + resultPopulation);
        }

        connection.close();
    }

    private void initialize() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/world?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
        String username = "root";
        String password = "admin";
        this.connection = DriverManager.getConnection(url, username, password);
        this.scanner = new Scanner(System.in);
    }
}
