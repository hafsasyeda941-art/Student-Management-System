
package com.studentmanagement.model;

public class StudentResult {

    private String rollNumber;
    private String studentName;
    private double totalObtained;
    private double totalMaximum;
    private double percentage;
    private double gpa;

    public StudentResult(String rollNumber, String studentName,
                         double totalObtained, double totalMaximum) {

        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.totalObtained = totalObtained;
        this.totalMaximum = totalMaximum;

        this.percentage = totalMaximum > 0
                ? (totalObtained / totalMaximum) * 100
                : 0;

        this.gpa = percentage / 10;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getTotalObtained() {
        return totalObtained;
    }

    public double getTotalMaximum() {
        return totalMaximum;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getGpa() {
        return gpa;
    }
}

