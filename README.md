# Student Management System

## Project Overview
The Student Management System is a web-based application developed using Java, Spring Boot and MySQL. It helps administrators manage student information, attendance and academic performance through a centralized dashboard.

## Technologies Used
- Java 21
- Spring Boot 3.5.6
- MySQL
- JDBC
- Thymeleaf
- HTML
- CSS
- Maven
- IntelliJ IDEA

## Features

### 1. Student Management
- Register new students
- View registered students
- Update student details
- Delete student records
- Search students by name or roll number

### 2. Admin Authentication
- Admin login and logout
- Session-based authentication
- Protected management routes
- BCrypt password hashing

### 3. Attendance Management
- Mark students present or absent
- Store attendance records in MySQL
- View attendance history
- Calculate attendance percentages

### 4. Marks Management
- Enter subject-wise marks
- Update existing marks
- Calculate subject-wise percentages
- Calculate overall marks and percentage
- Calculate GPA on a 10-point scale

### 5. Dynamic Dashboard
- Total registered students
- Total departments
- Overall attendance percentage

## Database
Database name: `student_management`

Main tables:
- `students`
- `admins`
- `attendance`
- `marks`

## Installation and Setup

1. Install Java 21, IntelliJ IDEA and MySQL.
2. Clone or download this repository.
3. Create the MySQL database named `student_management`.
4. Create the required database tables.
5. Configure the database connection using your local database credentials.
6. Open the project in IntelliJ IDEA.
7. Reload Maven dependencies.
8. Run `StudentManagementApplication.java`.

Open the application at:

`http://localhost:8080/login`

## Security
- Admin passwords are stored using BCrypt hashing.
- Session-based authentication protects management routes.
- SQL operations use PreparedStatements.

## Author
Student Management System Project

## Future Enhancements
- Student profile photographs
- PDF report generation
- Export student results
- Advanced attendance reports
- Multiple administrator accounts