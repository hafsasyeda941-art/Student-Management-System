
package com.studentmanagement.dao;

import com.studentmanagement.DBConnection;
import com.studentmanagement.model.Marks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.studentmanagement.model.StudentResult;

public class MarksDAO {

    public void saveMarks(Marks marks) throws SQLException {

        if (marks.getSubject() == null ||
                marks.getSubject().isBlank() ||
                marks.getMaxMarks() <= 0 ||
                marks.getMarksObtained() < 0 ||
                marks.getMarksObtained() > marks.getMaxMarks()) {
            throw new IllegalArgumentException("Invalid marks details.");
        }

        String sql = "INSERT INTO marks " +
                "(roll_number, subject, marks_obtained, max_marks) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "marks_obtained = VALUES(marks_obtained), " +
                "max_marks = VALUES(max_marks)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, marks.getRollNumber());
            statement.setString(2, marks.getSubject());
            statement.setDouble(3, marks.getMarksObtained());
            statement.setDouble(4, marks.getMaxMarks());

            statement.executeUpdate();
        }
    }

    public List<Marks> getAllMarks() throws SQLException {

        List<Marks> marksList = new ArrayList<>();

        String sql = "SELECT roll_number, subject, " +
                "marks_obtained, max_marks " +
                "FROM marks ORDER BY roll_number, subject";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                Marks marks = new Marks(
                        rs.getString("roll_number"),
                        rs.getString("subject"),
                        rs.getDouble("marks_obtained"),
                        rs.getDouble("max_marks")
                );

                marksList.add(marks);
            }
        }

        return marksList;

    }

    public List<StudentResult> getStudentResults() throws SQLException {

        List<StudentResult> results = new ArrayList<>();

        String sql = "SELECT s.roll_number, s.name, " +
                "COALESCE(SUM(m.marks_obtained), 0) AS total_obtained, " +
                "COALESCE(SUM(m.max_marks), 0) AS total_maximum " +
                "FROM students s " +
                "LEFT JOIN marks m ON s.roll_number = m.roll_number " +
                "GROUP BY s.roll_number, s.name " +
                "ORDER BY s.roll_number";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                results.add(new StudentResult(
                        rs.getString("roll_number"),
                        rs.getString("name"),
                        rs.getDouble("total_obtained"),
                        rs.getDouble("total_maximum")
                ));
            }
        }

        return results;
    }

}
