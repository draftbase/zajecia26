package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmployeeDao {

    private static final String URL = "jdbc:mysql://localhost:3306/company?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    private Connection connection;

    public EmployeeDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Employee employee) {
        try {
            PreparedStatement insertStatement =
                insertStatement = connection.prepareStatement("INSERT INTO employees(firstName, lastName, salary) VALUES (?, ?, ?)");
            insertStatement.setString(1, employee.getFirstName());
            insertStatement.setString(2, employee.getLastName());
            insertStatement.setDouble(3, employee.getSalary());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Employee> read(long id) {
        try {
            PreparedStatement queryStatement =
                connection.prepareStatement("SELECT id, firstName, lastName, salary FROM employees WHERE id = ?");
            queryStatement.setLong(1, id);
            ResultSet resultSet = queryStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setSalary(resultSet.getDouble("salary"));
                return Optional.of(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
