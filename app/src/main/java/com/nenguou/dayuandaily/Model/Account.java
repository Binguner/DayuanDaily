package com.nenguou.dayuandaily.Model;

public class Account {
    String studentName;
    String studentNumber;

    public Account(String studentName, String studentNumber) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
    }

    public Account() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
