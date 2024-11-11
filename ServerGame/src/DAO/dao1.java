/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.*;
/**
 *
 * @author Admin
 */
public class dao1 {
    protected Connection con;

    public dao1() {
        String jdbcURL = "jdbc:mysql://localhost:3306/cotam";
        String jdbcUsername = "root";
        String jdbcPassword = ""; //please change information to connect to DB
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed");
        }
    }
}