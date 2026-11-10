
package com.studentmanagement.model;

public class Student {

    // Student attributes
    private String rollNumber;
    private String name;
    private String email;
    private String department;
    private int year;

    // Constructor
    public Student(String rollNumber, String name,
                   String email, String department, int year) {

        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
    }

    // Getters
    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public int getYear() {
        return year;
    }

    // Setters
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
