package com.studentmanagement;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("Database connected successfully!");
            }

            con.close();

        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
