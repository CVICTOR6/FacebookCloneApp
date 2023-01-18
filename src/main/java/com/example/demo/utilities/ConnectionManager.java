package com.example.demo.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private static Connection con;
    public static Connection getConnection()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Facebook_db","root","Joy2Dworld");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facebookmimic?autoReconnect=true&useSSL=false","root","password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

}
