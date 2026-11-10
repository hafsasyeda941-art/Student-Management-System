
package com.studentmanagement.model;

public class AttendanceSummary {

    private String rollNumber;
    private String studentName;
    private int totalDays;
    private int presentDays;
    private double percentage;

    public AttendanceSummary(String rollNumber, String studentName,
                             int totalDays, int presentDays) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.totalDays = totalDays;
        this.presentDays = presentDays;
        this.percentage = totalDays == 0
                ? 0.0
                : (presentDays * 100.0) / totalDays;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public double getPercentage() {
        return percentage;
    }
}
