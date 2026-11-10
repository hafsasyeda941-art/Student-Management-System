
package com.studentmanagement.controller;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.model.Student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import com.studentmanagement.dao.AdminDAO;

import com.studentmanagement.dao.AttendanceDAO;

import com.studentmanagement.model.Attendance;

import com.studentmanagement.dao.MarksDAO;
import com.studentmanagement.model.Marks;
import java.util.List;

import com.studentmanagement.model.AttendanceSummary;

import com.studentmanagement.model.StudentResult;


@Controller
public class PageController {

    // Dashboard

    @GetMapping("/")
    public String dashboard(HttpSession session, Model model) {

        if (session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        try {
            StudentDAO studentDAO = new StudentDAO();
            AttendanceDAO attendanceDAO = new AttendanceDAO();

            model.addAttribute(
                    "totalStudents",
                    studentDAO.getTotalStudents()
            );

            model.addAttribute(
                    "totalDepartments",
                    studentDAO.getTotalDepartments()
            );

            model.addAttribute(
                    "attendancePercentage",
                    attendanceDAO.getOverallAttendancePercentage()
            );

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error",
                    "Unable to load dashboard statistics.");
        }

        return "index";
    }


    // Add Student Page
    @GetMapping("/add-student")
    public String addStudent() {
        return "add-student";
    }

    // Register Student
    @PostMapping("/students")
    public String registerStudent(
            @RequestParam String rollNumber,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String department,
            @RequestParam int year,
            Model model) {

        Student student = new Student(
                rollNumber,
                name,
                email,
                department,
                year
        );

        try {
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.addStudent(student);

            model.addAttribute(
                    "message",
                    "Student registered successfully!"
            );

        } catch (SQLException e) {
            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to register student. Please try again."
            );
        }

        return "add-student";
    }

    // View All Students
    @GetMapping("/view-students")
    public String viewStudents(Model model) {

        try {
            StudentDAO studentDAO = new StudentDAO();

            List<Student> students =
                    studentDAO.getAllStudents();

            model.addAttribute("students", students);

        } catch (SQLException e) {
            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to retrieve student records."
            );
        }

        return "view-students";
    }

    // Open Edit Student Page
    @GetMapping("/edit-student/{rollNumber}")
    public String editStudent(
            @org.springframework.web.bind.annotation.PathVariable String rollNumber,
            Model model) {

        try {
            StudentDAO studentDAO = new StudentDAO();

            Student student =
                    studentDAO.getStudentByRollNumber(rollNumber);

            if (student == null) {
                return "redirect:/view-students";
            }

            model.addAttribute("student", student);

            return "edit-student";

        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/view-students";
        }
    }


    // Save Updated Student
    @PostMapping("/update-student")
    public String updateStudent(
            @RequestParam String rollNumber,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String department,
            @RequestParam int year) {

        try {
            Student student = new Student(
                    rollNumber, name, email, department, year
            );

            StudentDAO studentDAO = new StudentDAO();
            studentDAO.updateStudent(student);

            return "redirect:/view-students";

        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/view-students";
        }
    }

    // Delete Student
    @PostMapping("/delete-student")
    public String deleteStudent(
            @RequestParam String rollNumber) {

        try {
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.deleteStudent(rollNumber);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/view-students";
    }

    @GetMapping("/search-students")
    public String searchStudents(
            @RequestParam String keyword,
            Model model) {

        try {
            StudentDAO studentDAO = new StudentDAO();

            List<Student> students =
                    studentDAO.searchStudents(keyword);

            model.addAttribute("students", students);
            model.addAttribute("keyword", keyword);

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to search students.");
            model.addAttribute("students", List.of());
        }

        return "view-students";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        AdminDAO adminDAO = new AdminDAO();

        if (adminDAO.validateAdmin(username, password)) {
            session.setAttribute("admin", username);
            return "redirect:/";
        }

        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @GetMapping("/attendance")
    public String attendancePage(Model model) {

        try {
            StudentDAO studentDAO = new StudentDAO();

            List<Student> students = studentDAO.getAllStudents();

            model.addAttribute("students", students);

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to load students.");
            model.addAttribute("students", List.of());
        }

        return "attendance";
    }

    @PostMapping("/mark-attendance")
    public String markAttendance(
            @RequestParam String rollNumber,
            @RequestParam String attendanceDate,
            @RequestParam String status,
            Model model) {

        try {
            AttendanceDAO attendanceDAO = new AttendanceDAO();

            attendanceDAO.markAttendance(
                    rollNumber,
                    attendanceDate,
                    status
            );

            return "redirect:/attendance?success";

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to mark attendance.");
            return attendancePage(model);
        }
    }

    @GetMapping("/attendance-history")
    public String attendanceHistory(Model model) {

        try {
            AttendanceDAO attendanceDAO = new AttendanceDAO();

            List<Attendance> records =
                    attendanceDAO.getAttendanceHistory();

            model.addAttribute("records", records);

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error",
                    "Unable to load attendance history.");
            model.addAttribute("records", List.of());
        }

        return "attendance-history";
    }

    @GetMapping("/attendance-summary")
    public String attendanceSummary(Model model) {

        try {
            AttendanceDAO dao = new AttendanceDAO();

            model.addAttribute(
                    "summaries",
                    dao.getAttendanceSummary()
            );

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error",
                    "Unable to load attendance summary.");
            model.addAttribute("summaries", List.of());
        }

        return "attendance-summary";
    }

    @GetMapping("/marks")
    public String marksPage(Model model) {

        try {
            StudentDAO studentDAO = new StudentDAO();
            MarksDAO marksDAO = new MarksDAO();

            model.addAttribute("students", studentDAO.getAllStudents());
            model.addAttribute("marksList", marksDAO.getAllMarks());

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to load marks.");
            model.addAttribute("students", List.of());
            model.addAttribute("marksList", List.of());
        }

        return "marks";
    }

    @PostMapping("/save-marks")
    public String saveMarks(
            @RequestParam String rollNumber,
            @RequestParam String subject,
            @RequestParam double marksObtained,
            @RequestParam double maxMarks,
            Model model) {

        try {
            Marks marks = new Marks(
                    rollNumber,
                    subject,
                    marksObtained,
                    maxMarks
            );

            MarksDAO marksDAO = new MarksDAO();
            marksDAO.saveMarks(marks);

            return "redirect:/marks?success";

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();

            try {
                model.addAttribute("students",
                        new StudentDAO().getAllStudents());
                model.addAttribute("marksList",
                        new MarksDAO().getAllMarks());
            } catch (SQLException ex) {
                ex.printStackTrace();
                model.addAttribute("students", List.of());
                model.addAttribute("marksList", List.of());
            }

            model.addAttribute("error", "Unable to save marks. Check the entered values.");
            return "marks";
        }
    }

    @GetMapping("/student-results")
    public String studentResults(Model model) {

        try {
            MarksDAO marksDAO = new MarksDAO();

            model.addAttribute(
                    "results",
                    marksDAO.getStudentResults()
            );

        } catch (SQLException e) {
            e.printStackTrace();

            model.addAttribute("error",
                    "Unable to load student results.");

            model.addAttribute("results", List.of());
        }

        return "student-results";
    }







}

