package net.lwenstrom.mock;

import net.lwenstrom.model.Student;

import java.util.Date;


public class StudentTableEntry {
    private Student student;
    private Date requestDateTime;

    public StudentTableEntry(Student student) {
        this.student = student;
        requestDateTime = new Date(System.currentTimeMillis());
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getRequestDateTime(){
        return requestDateTime;
    }
}
