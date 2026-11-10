
package com.studentmanagement.model;

public class Attendance {

    private String rollNumber;
    private String studentName;
    private String attendanceDate;
    private String status;

    public Attendance(String rollNumber, String studentName,
                      String attendanceDate, String status) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public String getStatus() {
        return status;
    }
}
