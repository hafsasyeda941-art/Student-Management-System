
package com.studentmanagement.dao;

import com.studentmanagement.DBConnection;
import com.studentmanagement.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(Student student) throws SQLException {

        String sql = "INSERT INTO students " +
                "(roll_number, name, email, department, year) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, student.getRollNumber());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getDepartment());
            statement.setInt(5, student.getYear());

            statement.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT roll_number, name, email, department, year FROM students";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Student student = new Student(
                        resultSet.getString("roll_number"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("department"),
                        resultSet.getInt("year")
                );

                students.add(student);
            }
        }

        return students;
    }
    // Get student by Roll Number
    public Student getStudentByRollNumber(String rollNumber)
            throws SQLException {

        String sql = "SELECT * FROM students WHERE roll_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, rollNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Student(
                            resultSet.getString("roll_number"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("department"),
                            resultSet.getInt("year")
                    );
                }
            }
        }

        return null;
    }


    // Update student details
    public void updateStudent(Student student)
            throws SQLException {

        String sql = "UPDATE students SET name = ?, email = ?, department = ?, year = ? WHERE roll_number = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getDepartment());
            statement.setInt(4, student.getYear());
            statement.setString(5, student.getRollNumber());

            statement.executeUpdate();
        }
    }

    public void deleteStudent(String rollNumber) throws SQLException {

        String sql = "DELETE FROM students WHERE roll_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, rollNumber);

            statement.executeUpdate();
        }
    }

    public List<Student> searchStudents(String keyword) throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT roll_number, name, email, department, year " +
                "FROM students " +
                "WHERE roll_number LIKE ? OR name LIKE ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchValue = "%" + keyword + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Student student = new Student(
                            resultSet.getString("roll_number"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("department"),
                            resultSet.getInt("year")
                    );

                    students.add(student);
                }
            }
        }

        return students;
    }

    public int getTotalStudents() throws SQLException {

        String sql = "SELECT COUNT(*) FROM students";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public int getTotalDepartments() throws SQLException {

        String sql = "SELECT COUNT(DISTINCT department) " +
                "FROM students " +
                "WHERE department IS NOT NULL AND department <> ''";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }



}
