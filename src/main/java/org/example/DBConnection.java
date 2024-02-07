package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {



    static Connection connection;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "chiti@786");
            System.out.println("Connection Established");
        }
        catch (ClassNotFoundException cnf)
        {
            cnf.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return connection;
    }
}
