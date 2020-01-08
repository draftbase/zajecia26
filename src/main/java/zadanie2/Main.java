package zadanie2;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Application application = new Application();
            application.run();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
