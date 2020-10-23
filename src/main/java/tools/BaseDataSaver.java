package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDataSaver {
    public static void save(int x, double y, int r, String result) throws ClassNotFoundException, SQLException {
        String userName = "postgres";
        String password1 = "123";
        String connectionUrl = "jdbc:postgresql://localhost:5432/postgres";
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(connectionUrl, userName, password1);
//        System.out.println("Connected");
        Statement statement = connection.createStatement();
        String query = "INSERT INTO results (x,y,r,result) values (" + x + ", " +
                y + ", " + r + ", '" +
                result + "')";
        statement.executeUpdate(query);
    }
}
