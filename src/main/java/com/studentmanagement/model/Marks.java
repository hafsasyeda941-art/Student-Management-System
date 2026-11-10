
package com.studentmanagement.model;

public class Marks {

    private String rollNumber;
    private String subject;
    private double marksObtained;
    private double maxMarks;

    public Marks(String rollNumber, String subject,
                 double marksObtained, double maxMarks) {
        this.rollNumber = rollNumber;
        this.subject = subject;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getSubject() {
        return subject;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public double getPercentage() {
        if (maxMarks <= 0) {
            return 0;
        }
        return (marksObtained / maxMarks) * 100;
    }
}

