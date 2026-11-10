
package com.studentmanagement.dao;

import com.studentmanagement.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import com.studentmanagement.model.Attendance;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import com.studentmanagement.model.AttendanceSummary;
import java.util.List;
import java.util.ArrayList;
public class AttendanceDAO {

    public void markAttendance(
            String rollNumber,
            String attendanceDate,
            String status) throws SQLException {

        String sql = "INSERT INTO attendance " +
                "(roll_number, attendance_date, status) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE status = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, rollNumber);
            statement.setDate(2, Date.valueOf(attendanceDate));
            statement.setString(3, status);
            statement.setString(4, status);

            statement.executeUpdate();
        }
    }

    public List<Attendance> getAttendanceHistory() throws SQLException {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = "SELECT a.roll_number, s.name, " +
                "a.attendance_date, a.status " +
                "FROM attendance a " +
                "JOIN students s ON a.roll_number = s.roll_number " +
                "ORDER BY a.attendance_date DESC, a.roll_number";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Attendance attendance = new Attendance(
                        resultSet.getString("roll_number"),
                        resultSet.getString("name"),
                        resultSet.getDate("attendance_date").toString(),
                        resultSet.getString("status")
                );

                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }

    public double getAttendancePercentage(String rollNumber)
            throws SQLException {

        String sql = "SELECT COUNT(*) AS total, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present " +
                "FROM attendance WHERE roll_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, rollNumber);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    int total = rs.getInt("total");
                    int present = rs.getInt("present");

                    if (total == 0) {
                        return 0.0;
                    }

                    return (present * 100.0) / total;
                }
            }
        }

        return 0.0;
    }

    public List<AttendanceSummary> getAttendanceSummary()
            throws SQLException {

        List<AttendanceSummary> summaries = new ArrayList<>();

        String sql = "SELECT s.roll_number, s.name, " +
                "COUNT(a.attendance_id) AS total_days, " +
                "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS present_days " +
                "FROM students s " +
                "LEFT JOIN attendance a ON s.roll_number = a.roll_number " +
                "GROUP BY s.roll_number, s.name " +
                "ORDER BY s.roll_number";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                summaries.add(new AttendanceSummary(
                        rs.getString("roll_number"),
                        rs.getString("name"),
                        rs.getInt("total_days"),
                        rs.getInt("present_days")
                ));
            }
        }

        return summaries;
    }

    public double getOverallAttendancePercentage() throws SQLException {

        String sql = "SELECT " +
                "COUNT(*) AS total, " +
                "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) AS present " +
                "FROM attendance";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                int present = rs.getInt("present");

                if (total == 0) {
                    return 0.0;
                }

                return (present * 100.0) / total;
            }
        }

        return 0.0;
    }


}

