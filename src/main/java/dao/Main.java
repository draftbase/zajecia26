package dao;

public class Main {

    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = new Employee("Marcin", "ABC", 123.2);

        employeeDao.save(employee);

        employeeDao.read(1L)
            .ifPresent(emp -> System.out.println(emp));

        employeeDao.close();
    }
}
