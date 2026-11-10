
package com.studentmanagement.dao;

import com.studentmanagement.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.SQLException;

public class AdminDAO {

    public boolean validateAdmin(String username, String password) {

        String sql = "SELECT password FROM admins WHERE username = ?";

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    return encoder.matches(password, storedHash);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
